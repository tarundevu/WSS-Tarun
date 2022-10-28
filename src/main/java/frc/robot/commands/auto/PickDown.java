package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
// import the commands
import edu.wpi.first.wpilibj.geometry.Translation2d;

//WPI imports


//RobotContainer import



//Subsystem imports

//import frc.robot.subsystems.Arm;



/**
 * DriveMotor class
 * <p>
 * This class creates the inline auto command to drive the motor
 */
public class PickDown extends SequentialCommandGroup
{
    
    public PickDown()
    {
      
      super(
      //new MoveArm(new Translation2d(0.25,0.1),0.5),
      //new Gripper(1, 100),
      new MoveArm(new Translation2d(0.3,0.1),0.5),
      new MoveArm(new Translation2d(0.3,0), 0.5),
      
      new Gripper(1,100),
      new MoveArm(new Translation2d(0.3,0.1), 0.5)
      //new MoveArm(new Translation2d(0.25,0.1),0.5)
      //new Gripper(1, 100)
      
      );
      
    }
}