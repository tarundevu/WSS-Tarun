package frc.robot.subsystems;

import java.util.Map;

import com.studica.frc.Servo;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.commands.auto.MoveServo;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.networktables.NetworkTableEntry;


// Good to create a subsystem for robot arm
public class Arm extends SubsystemBase{
    private final Servo servo1;
    private final Servo servo2;
    private double Servovalue;
    private double Servovalue2;

    private final double l1 = 0.24; 
    private final double l2 = 0.32; 
    private double offset0 = 0;  
    private double offset1 = 0;
    private double A,B;
    

    private final ShuffleboardTab tab = Shuffleboard.getTab("Arm");
    private final NetworkTableEntry D_armvalue = tab.add("Arm value", 0).getEntry();
    private final NetworkTableEntry D_armvalue2 = tab.add("Arm value2", 0).getEntry();
    private final NetworkTableEntry D_armAngleA = tab.add("Arm angleA", 0).getEntry();
    private final NetworkTableEntry D_armAngleB = tab.add("Arm angleB", 0).getEntry();
    private final NetworkTableEntry D_offset0 = tab.addPersistent("offset0", 0).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", -500, "max", +500)).getEntry();
    private final NetworkTableEntry D_offset1 = tab.addPersistent("offset1", 0).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", -500, "max", +500)).getEntry();
    private final NetworkTableEntry D_sliderX = tab.add("setX", 0.04).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0.05, "max", 0.4)) .getEntry();
    private final NetworkTableEntry D_sliderY = tab.add("setY", 0).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0.0, "max", 0.4)) .getEntry();
    private final NetworkTableEntry D_sliderServo = tab.add("setServo", 0).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 300)) .getEntry();
    

    public Arm(){
      servo1 = new Servo(0);
      servo2 = new Servo(1);
      
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

    public double getServoAngle(){
      return servo1.getAngle();
      
    }
    public double getServoAngle2(){
      return servo2.getAngle();
      
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
    public void setArmPos(double _x, double _y){
    
      double x = _x;
      double y = _y;

      if ((x<0.05)&&(y<0.1)){
        x = 0.05;
              
      }
      // if ((x>0.3)&&(y>0.1)){

      // }

      double a = l2;
      double c = l1;
      double b = Math.sqrt(x*x+y*y);
      double alpha = Math.acos( (b*b + c*c - a*a)/(2*b*c) );
      double beta = Math.acos( (a*a + c*c - b*b)/(2*a*c) );

        // A is servo0 angle wrt horizon
        // When A is zero, arm-c is horizontal.
        // beta is servo1 angle wrt arm-c (BA)
        // When beta is zero, arm-c is closed  to arm-c
      B = beta;    //Use B to designate beta. Different from diagram.
      A = alpha + Math.atan2(y,x);

        //servo0 and servo1 might be mounted clockwise or anti clockwise.
        //offset0 and offset1 are used to adjust the zero the arm position.
        //This makes it easier to mount and tune the arm.

       //B = 300 - B;
      // A = 300 -A;
      //A = A*4;
      //B=B*2;
      

      B*=-2;
      A*=4;
      servo1.setAngle((Math.toDegrees(A) + offset0));
      servo2.setAngle(((Math.toDegrees(B)) + offset1));
      
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

    
}
