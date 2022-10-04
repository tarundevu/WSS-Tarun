package frc.robot.subsystems;

import java.util.Map;

import com.studica.frc.Servo;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
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

    private final double a1 = 0.25; 
    private final double a2 = 0.25; 
    private double offset0 = 0;   //For making software adjustment to servo 
    private double offset1 = 0;
    

    private final ShuffleboardTab tab = Shuffleboard.getTab("Arm");
    private final NetworkTableEntry D_armvalue = tab.add("Arm value", 0).getEntry();
    private final NetworkTableEntry D_armvalue2 = tab.add("Arm value2", 0).getEntry();
    private final NetworkTableEntry D_offset0 = tab.addPersistent("offset0", 0).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", -10, "max", +10)).getEntry();
    private final NetworkTableEntry D_offset1 = tab.addPersistent("offset1", 0).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", -10, "max", +10)).getEntry();

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

    public void setArmPos(double _x, double _y){
    
      double x = _x;
      double y = _y;

      double a = a2;
      double c = a1;
      double b = Math.sqrt(x*x+y*y);
      double alpha = Math.acos( (b*b + c*c - a*a)/(2*b*c) );
      double beta = Math.acos( (a*a + c*c - b*b)/(2*a*c) );

        // A is servo0 angle wrt horizon
        // When A is zero, arm-c is horizontal.
        // beta is servo1 angle wrt arm-c (BA)
        // When beta is zero, arm-c is closed  to arm-c
      double B = beta;    //Use B to designate beta. Different from diagram.
      double A = alpha + Math.atan2(y,x);

        //servo0 and servo1 might be mounted clockwise or anti clockwise.
        //offset0 and offset1 are used to adjust the zero the arm position.
        //This makes it easier to mount and tune the arm.
      servo1.setAngle(Math.toDegrees(A) + offset0);
      servo2.setAngle(Math.toDegrees(B) + offset1);
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
    }

    
}
