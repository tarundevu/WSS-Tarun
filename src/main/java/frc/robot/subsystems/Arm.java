package frc.robot.subsystems;

import java.util.Map;

import com.studica.frc.Servo;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.auto.MoveServo;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.networktables.NetworkTableEntry;


// Good to create a subsystem for robot arm
public class Arm extends SubsystemBase{
    private final Servo servo1;
    private final Servo servo2;
    private final Servo gripper;
    private final Servo servoCam;
    private double Servovalue;
    private double Servovalue2;

    private final double l1 = 0.24; 
    private final double l2 = 0.335; 
    private double offset0 = 0;  
    private double offset1 = 0;
    private double A,B, m_x, m_y;
    private Translation2d m_pos;
    

    private final ShuffleboardTab tab = Shuffleboard.getTab("Arm");
    private final NetworkTableEntry D_armvalue = tab.add("Servo1 angle", 0).getEntry();
    private final NetworkTableEntry D_armvalue2 = tab.add("Servo2 angle", 0).getEntry();
    private final NetworkTableEntry D_armAngleA = tab.add("Arm angleA", 0).getEntry();
    private final NetworkTableEntry D_armAngleB = tab.add("Arm angleB", 0).getEntry();
    private final NetworkTableEntry D_X = tab.add("X", 0).getEntry();
    private final NetworkTableEntry D_Y = tab.add("Y", 0).getEntry();
    private final NetworkTableEntry D_offset0 = tab.addPersistent("offset0", 0).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", -500, "max", +500)).getEntry();
    private final NetworkTableEntry D_offset1 = tab.addPersistent("offset1", 0).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", -500, "max", +500)).getEntry();
    private final NetworkTableEntry D_sliderX = tab.add("setX", 0.04).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0.05, "max", 0.4)) .getEntry();
    private final NetworkTableEntry D_sliderY = tab.add("setY", 0).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0.0, "max", 0.4)) .getEntry();
    private final NetworkTableEntry D_sliderServo = tab.add("setServo", 0).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 300)) .getEntry();
    private final NetworkTableEntry D_Gripper = tab.add("Gripper angle", 0).getEntry();
    

    public Arm(){
      servo1 = new Servo(0);
      servo2 = new Servo(1);
      gripper = new Servo(2);
      servoCam = new Servo(3);
;    }
    
    public void initialize(){
      // _x= 0.2;
      // _y = 0;
      setArmPos(0.3, 0.1);
      setGripper(0);
      new WaitCommand(0.5);
      

    }
    

    public double getServoAngle(){
      return servo1.getAngle();
      
    }
    public double getServoAngle2(){
      return servo2.getAngle();
      
    }
    public double getGripper(){
      return gripper.getAngle();
    }
    public double getServoAngle3(){
      return servoCam.getAngle();
    }
    public double getSliderX() {
      return D_sliderX.getDouble(0.04);
    }

    public double getSliderY() {
      return D_sliderY.getDouble(0.0);
    }

    public double getSliderServo()
    {
      return D_sliderServo.getDouble(0);
    }

    /**
     * Sets the servo angle
     * <p>
     * 
     * @param degrees degree to set the servo to, range 0° - 300°
     */
    public void setServoAngle(final double degrees){
      Servovalue = degrees;
      servo1.setAngle(Servovalue);
    }
    public void setServoAngle2(final double degrees){
      Servovalue2 = degrees;
      servo2.setAngle(Servovalue2);
    }
    public void setGripper(final double degrees){
      gripper.setAngle(degrees);
    }
    public void setServoAngle3(final double degrees){
      servoCam.setAngle(degrees);
    }
    public void setArmPos(double x, double y){
    
      m_x = x;
      m_y = y;

      if ((x<0.2)){
        x = 0.2;
              
      }
    

      double a = l2;
      double c = l1;
      double b = Math.sqrt(x*x+y*y);
      double alpha = Math.acos( (b*b + c*c - a*a)/(2*b*c) );
      double beta = Math.acos( (a*a + c*c - b*b)/(2*a*c) );

        // A is servo0 angle wrt horizon
        // When A is zero, arm-c is horizontal.
        // beta is servo1 angle wrt arm-c (BA)
        // When beta is zero, arm-c is closed  to arm-c
      B = Math.PI - beta;    //Use B to designate beta. Different from diagram.
      A = alpha + Math.atan2(y,x);

        //servo0 and servo1 might be mounted clockwise or anti clockwise.
        //offset0 and offset1 are used to adjust the zero the arm position.
        //This makes it easier to mount and tune the arm.

      B*=2;
      A*=4;
      servo1.setAngle((Math.toDegrees(A) + offset0));
      servo2.setAngle((Math.toDegrees(B) + offset1));
      
    }
    public Translation2d getArmPos(){
      Translation2d m_pos = new Translation2d(m_x, m_y);
      return m_pos;
    }
    
    public void DisplayValue(double get_X, double get_Y){
       D_X.setDouble(get_X);
       D_Y.setDouble(get_Y);
       

    }
   
    public void DisplayGripperAngle(double angle){
      D_Gripper.setDouble(angle);
    }
    

    
    /**
     * code that runs once every robot loop
     */
    @Override
    public void periodic(){
      offset0 = D_offset0.getDouble(0.0);
      offset1 = D_offset1.getDouble(0.0);
      D_armvalue.setDouble(getServoAngle());
      D_armvalue2.setDouble(getServoAngle2());
      // v1 = D_armvalue.getDouble(0);
      // v2 = D_armvalue2.getDouble(0);
      D_armAngleA.setDouble(Math.toDegrees(A));
      D_armAngleB.setDouble(Math.toDegrees(B));
      
    }

    // public void Debug(double position) {
    //   setpoint1.setDouble(position);
    //   //_dx.setDouble(dist);
    //   //tgt_dist.setDouble(tgt_dist);
    // }

    
}
