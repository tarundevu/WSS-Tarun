package frc.robot.subsystems;


//Java imports

//Vendor imports
import com.kauailabs.navx.frc.AHRS;
import com.studica.frc.TitanQuad;
import com.studica.frc.TitanQuadEncoder;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DigitalOutput;
//import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
//WPI imports
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.OmniDriveOdometry;


public class OmniDrive extends SubsystemBase
{
    //Creates all necessary hardware interface here for omni-drive

    //Motors and encoders
    private final TitanQuad[] motors;
    private final TitanQuadEncoder[] encoders;

    //PID stuff
    private PIDController[] pidControllers;
    private double[] pidInputs;
    private double[] pidOutputs;

    private double[] encoderDists;
    private double[] encoderDists_2;
    private double[] encoderSpeeds;
    private double[] wheelSpeeds;
    private double curHeading, targetHeading;
    private double[] motorOuts;
    
    // Odometry class for tracking robot pose
    private final OmniDriveOdometry m_odometry;
    
    //For testing. These should be in another subsystem
    private double pid_dT = Constants.PID_DT;

    // Sensors
    private final DigitalOutput outDebug8;

    private final AHRS gyro;

    // Shuffleboard
    private final ShuffleboardTab tab = Shuffleboard.getTab("OmniDrive");

    private final NetworkTableEntry D_navYaw = tab.add("Nav Yaw", 0).getEntry();
    private final NetworkTableEntry D_curHeading = tab.add("curHeading", 0).getEntry();
    private final NetworkTableEntry D_tgtHeading = tab.add("tgtHeading", 0).getEntry();
    private final NetworkTableEntry D_encoderDisp0 = tab.add("Encoder0", 0).getEntry();
    private final NetworkTableEntry D_encoderDisp1 = tab.add("Encoder1", 0).getEntry();
    private final NetworkTableEntry D_encoderDisp2 = tab.add("Encoder2", 0).getEntry();
    private final NetworkTableEntry D_inputW = tab.add("inputW", 0).getEntry();
    private final NetworkTableEntry D_odometry0 = tab.add("odo x", 0).getEntry();
    private final NetworkTableEntry D_odometry1 = tab.add("odo y", 0).getEntry();
    private final NetworkTableEntry D_odometry2 = tab.add("odo A", 0).getEntry();

    //Subsystem for omnidrive
    public OmniDrive() {

        outDebug8 = new DigitalOutput(8);

        //Omni drive motors
        motors = new TitanQuad[Constants.MOTOR_NUM];
        for (int i=0; i<Constants.MOTOR_NUM; i++) {
            motors[i] = new TitanQuad(Constants.TITAN_ID, i);
            motors[i].setInverted(true);   //Positive is CW. Need to reverse
        }


        encoders = new TitanQuadEncoder[Constants.MOTOR_NUM];
        encoderDists = new double[Constants.MOTOR_NUM];
        encoderDists_2 = new double[Constants.MOTOR_NUM];
        encoderSpeeds = new double[Constants.MOTOR_NUM];
        wheelSpeeds = new double[Constants.MOTOR_NUM];
        motorOuts = new double[Constants.MOTOR_NUM];

        for (int i=0; i<Constants.MOTOR_NUM; i++) {
            encoders[i] = new TitanQuadEncoder(motors[i], i, Constants.KENCODERDISTPERPULSE);
            encoders[i].reset();
            encoderDists[i] = encoders[i].getEncoderDistance();
        }
        
        // x, y and w speed controler
        pidControllers = new PIDController[Constants.PID_NUM];
        //Speed control
        pidControllers[0] = new PIDController(1.2,24.0,0.00, pid_dT);  //x
        pidControllers[1] = new PIDController(1.2,24.0,0.00, pid_dT);  //y 2.0,32.0,0.02
        pidControllers[2] = new PIDController(2.0,0.0,0.1, pid_dT);    //w
        pidControllers[2].enableContinuousInput(-Math.PI, Math.PI);

        //Inputs and Outputs for wheel controller
        pidInputs = new double[Constants.PID_NUM];
        pidOutputs = new double[Constants.PID_NUM];

        // gyro for rotational heading control
        gyro = new AHRS(SPI.Port.kMXP);
        gyro.zeroYaw();
        curHeading = targetHeading = getYawRad();

        m_odometry = new OmniDriveOdometry( new Pose2d(0.0, 0.0, new Rotation2d(0.0)));

    }

    public Pose2d getPose() {
        return m_odometry.getPose();
    }

    public double getYawRad() {
        return -gyro.getYaw()*Math.PI/180;
    }

    /**
     * Call for the current angle from the internal NavX
     * <p>
     * 
     * @return yaw angle in degrees range -180° to 180°
     */
    public double getYaw() {
        //return gyro.getYaw();
        return gyro.getRawGyroZ();
    }

    /**
     * Resets the yaw angle back to zero
     */
    public void resetGyro() {
        gyro.zeroYaw();
    }

    public void resetHeading() {
        curHeading = targetHeading = getYawRad();
    }

    /**
     * Sets the Output power of the 3 motors
     * <p>
     * 
     * @param speed0 range -1 to 1 (0 stop)
     * @param speed1 range -1 to 1 (0 stop)
     * @param speed2 range -1 to 1 (0 stop)
     */
    public void setMotorOut012(double speed0, double speed1, double speed2)
    {
        motors[0].set(speed0);
        motors[1].set(speed1);
        motors[2].set(speed2);
    }

    /**
     * Sets the Output power of the 3 motors according to robot x, y, and z speed
     * This will result in open loop control of the robot speed
     * This is for appreciation only. No real use for this function.
     * <p>
     * 
     * @param x range -1 to 1 (0 stop)
     * @param y range -1 to 1 (0 stop)
     * @param z range -1 to 1 (0 stop)
     */
    public void setRobotSpeedXYW_Open(double x, double y, double z)
    {
        // M0 = [-sin(150) cos(150) R] * [x y w]'   //Left-front wheel
        // M1 = [-sin(270) cos(270) R]              //Back wheel
        // M2 = [-sin(30)  cos(30)  R]              //Right-front wheel

    }

    /***
     * Sets the x, y & w speeds of the robot
     * <p>   
     * @param x - x speed in m/s
     * @param y - y speed in m/s
     * @param w - rotational speed in rad/s
     */
    public void setRobotSpeedXYW(double x, double y, double w) {
        pidInputs[0] = x; 
        pidInputs[1] = y;
        pidInputs[2] = w; 
    }
    /***
     * Sets one of the speeds of the robot
     * <p>  
     * @param type - 0, 1 or 2 for x, y or w speed
     * @param speed - speed to set
     */
    public void setRobotSpeedType(int type, double speed) {
        pidInputs[type] = speed; 
    }

    public void doPID( ){
        outDebug8.set(true);

        //This is for translational speed PID
        //First calculate wheel speed from encoder feedback
        double dcValue = 0.0;
        for (int i=0; i<Constants.MOTOR_NUM; i++) {
            encoderDists[i] = encoders[i].getEncoderDistance();
            wheelSpeeds[i] = encoderSpeeds[i] = (encoderDists[i]-encoderDists_2[i])/pid_dT;
            encoderDists_2[i] = encoderDists[i];
            //encoders[i].getSpeed() in rpm
            //wheelSpeeds[i] = encoderSpeeds[i] = -encoders[i].getSpeed()*Math.PI*0.1/60;
            dcValue += wheelSpeeds[i];
        }

        //Subtract rotational component from encoder speed
        //Rotational PID is handled by gyro separately.
        //Maybe good to combine this dc value with gyro value??????
        dcValue /= 3;
        for (int i=0; i<Constants.MOTOR_NUM; i++) {
            wheelSpeeds[i] -= dcValue;
        }

        //Estimates x and y speed from individual wheel speeds
        //See formula below
        double speedX = (-(wheelSpeeds[0] + wheelSpeeds[2]) + wheelSpeeds[1])/2;
        double speedY = (-wheelSpeeds[0] + wheelSpeeds[2])/(0.866025*2);

        //PID control for x and y speed
        //Speed control 
        pidOutputs[0] = pidControllers[0].calculate(speedX, pidInputs[0]);
        pidOutputs[1] = pidControllers[1].calculate(speedY, pidInputs[1]);
        
        //Translate x and y output to wheel outputs
        // The x and y speed are resolved into individual wheel speed
        // 3 wheel omni drive
        // R is distance of wheel from robot centre
        // M0 = [-sin(150) cos(150) R] * [x y w]'   //Left-front wheel
        // M1 = [-sin(270) cos(270) R]              //Back wheel
        // M2 = [-sin(30)  cos(30)  R]              //Right-front wheel
        motorOuts[0] = (-0.5*pidOutputs[0] - 0.866025*pidOutputs[1]);
        motorOuts[1] = (     pidOutputs[0] + 0               );
        motorOuts[2] = (-0.5*pidOutputs[0] + 0.866025*pidOutputs[1]);
        
        /////////////////////////////////////////////////////////////////////////////////////////
        //This is for rotational speed PID
        /////////////////////////////////////////////////////////////////////////////////////////
        curHeading = getYawRad();
        
        targetHeading += pidInputs[2]*pid_dT;   

        //Limit targetHeading to -Pi to +Pi
        if (targetHeading>Math.PI) targetHeading -= Math.PI*2;
        if (targetHeading<-Math.PI) targetHeading += Math.PI*2;

        pidOutputs[2] = pidControllers[2].calculate(curHeading, targetHeading);

        //Limit output to -1.0 to 1.0 as PID outputs may be greater then 1.0
        double max=1.0;
        for (int i=0; i<Constants.MOTOR_NUM; i++) {
            motorOuts[i] += pidOutputs[2];          // add w component
            max = Math.max(max, Math.abs(motorOuts[i]));
        }

        for (int i=0; i<Constants.MOTOR_NUM; i++) {
            //motors[i].set(motorOuts[i]/max);
            ///////////////////////////////////////////////////////////
            //motors[i].set(0);   //off motor to test encoders manually
        }   
        outDebug8.set(false);
   }
    /**
     * Code that runs once every robot loop
     */
    int initCnt=0;
    @Override
    public void periodic()
    {

        if (initCnt<1) {
            initCnt++;
            gyro.zeroYaw();
            curHeading = targetHeading = getYawRad();
            return;
        }

        if (!Constants.PID_THREAD ) {
            doPID();
        }
        //Use PIDInputs
        m_odometry.update(pidInputs[0]*pid_dT, pidInputs[1]*pid_dT, pidInputs[2]*pid_dT);


        /**
         * Updates for outputs to the shuffleboard
         */

        //D_curHeading.setDouble(curHeading);
        D_curHeading.setDouble(curHeading*180/Math.PI);
        D_tgtHeading.setDouble(targetHeading*180/Math.PI);
        D_navYaw.setDouble(-gyro.getYaw());

        //Titan encoder
        D_encoderDisp0.setDouble(encoderSpeeds[0]);//encoderSpeeds[0]);
        D_encoderDisp1.setDouble(encoderSpeeds[1]);//encoders[1].getEncoderDistance());//encoderSpeeds[1]);
        D_encoderDisp2.setDouble(encoderSpeeds[2]);//encoderSpeeds[2]);
        D_inputW.setDouble(pidInputs[2]);
        double [] value;
        value = new double[3];
        value[0] = m_odometry.getPose().getTranslation().getX();
        value[1] = m_odometry.getPose().getTranslation().getY();
        value[2] = m_odometry.getPose().getRotation().getDegrees();

        D_odometry0.setDouble(value[0]);
        D_odometry1.setDouble(value[1]);
        D_odometry2.setDouble(value[2]);
  
    }
}