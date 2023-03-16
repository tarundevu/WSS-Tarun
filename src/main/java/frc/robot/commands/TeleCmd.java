package frc.robot.commands;

import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Arm;
import frc.robot.commands.gamepad.OI;
import frc.robot.subsystems.OmniDrive;
import frc.robot.subsystems.Sensor;
import frc.robot.Globals;
import frc.robot.subsystems.Vision;

public class TeleCmd extends CommandBase
{
    /**
     * Bring in Subsystem and Gamepad code
     */
    private final OmniDrive m_omnidrive;
    private final Sensor m_sensor;
    private final Arm m_arm;
    private final OI m_oi;
    private final static Vision m_vision = RobotContainer.m_vision;
    /**
     * Constructor
     */
    public TeleCmd(OmniDrive omnidrive, OI oi, Arm arm)
    {
        m_omnidrive = RobotContainer.m_omnidrive;
        m_sensor = RobotContainer.m_sensor;
        m_oi = RobotContainer.m_oi;
        m_arm = RobotContainer.m_arm;
        //addRequirements(m_omnidrive); //add the drive subsystem as a requirement 
		//addRequirements(m_menu); 
    }

    /**
     * Code here will run once when the command is called for the first time
     */
    @Override
    public void initialize()
    {
        //Globals.cvMode=2;
        //m_vision.setcvMode();
    }

    /**
     * Code here will run continously every robot loop until the command is stopped
     */
    @Override
    public void execute()
    {
        /**
         * Get Joystick data
         */
        //Right stick for X-Y control
        //Left stick for W (rotational) control
        double x = m_oi.getRightDriveX();
        double y = -m_oi.getRightDriveY();//Down is positive. Need to negate
        double w = -m_oi.getLeftDriveX(); //X-positive is CW. Need to negate
        boolean A = m_oi.getDriveAButton();
        boolean B = m_oi.getDriveBButton();
        boolean pick = m_oi.getDriveXButton();
        boolean place = m_oi.getDriveYButton();

        //Get other buttons?
        double s = m_oi.getLeftDriveY();
        //Add code here to control servo motor etc.
        double s0, s1, s2;
        double speed_multiplier = 2;
        s0 = speed_multiplier * ((-0.498*x) + (-0.867*y)+(1*w));
        s1 = speed_multiplier * ((1*x) + (0*y)+(1*w));
        s2 = speed_multiplier * ((-0.5*x) + (0.865*y)+(1*w));
        // m_omnidrive.setMotorOut012(x,y,w);
        //m_arm.setArmPos(0.328, 0.24);
        Globals.cvMode=-1;
        m_arm.setCameraAngle(m_arm.getSliderServo());
        // m_arm.setCameraAngle(290);
        // m_arm.setGripper(150);
        // m_arm.setCameraAngle(280);
        //m_arm.set
        // if ((pick == true)){
        //     m_arm.setArmPos(0.328, 0.25);
        //     m_arm.setArmPos(0.3, -0.07);
        // }
        // if ((place == true) && (pick == false)){
        //     m_arm.setArmPos(0.328, 0.25);
        // }
        if (A==true){
            m_arm.setGripper(290);
        //    m_arm.setTrolleyAngle(150);
        //    Globals.cvMode=2;
        //    m_vision.setcvMode();
        }
        if (B==true){
            m_arm.setGripper(0);
            // m_arm.setTrolleyAngle(0);
        }
        // m_arm.setGripper(m_arm.getSliderServo());
        m_arm.setArmPos(m_arm.getSliderX(), m_arm.getSliderY());
        //Translation2d pos = new Translation2d(m_arm.getSliderX(), m_arm.getSliderY());
        m_omnidrive.setRobotSpeedXYW(x*0.5, y*0.5, w*Math.PI/4);

    }

    /**
     * When the comamnd is stopped or interrupted this code is run
     * <p>
     * Good place to stop motors in case of an error
     */
    @Override
    public void end(boolean interrupted)
    {
        m_omnidrive.setMotorOut012(0, 0, 0);
    }

    /**
     * Check to see if command is finished
     */
    @Override
    public boolean isFinished()
    {
        return false;
    }
}