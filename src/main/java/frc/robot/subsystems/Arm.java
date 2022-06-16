package frc.robot.subsystems;

import com.studica.frc.Servo;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.networktables.NetworkTableEntry;

// Good to create a subsystem for robot arm
public class Arm extends SubsystemBase{
    private final Servo servo1;
    private final Servo servo2;
    private double Servovalue;

    private final ShuffleboardTab tab = Shuffleboard.getTab("Arm");
    private final NetworkTableEntry D_armvalue = tab.add("Arm value", 0).getEntry();

    public Arm(){
      servo1 = new Servo(8);
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
      servo1.setAngle(degrees);
    }
    
    /**
     * code that runs once every robot loop
     */
    @Override
    public void periodic(){
      D_armvalue.setDouble(Servovalue);

    }
}
