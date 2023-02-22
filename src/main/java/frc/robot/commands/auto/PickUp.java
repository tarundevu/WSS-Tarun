package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.RobotContainer;
// import the commands
import edu.wpi.first.wpilibj.geometry.Translation2d;

//WPI imports


/**
 * DriveMotor class
 * <p>
 * This class creates the inline auto command to pick up objects
 */
public class PickUp extends SequentialCommandGroup
{
    
    public PickUp()
    {
      
      super(
        new Gripper(0, 70).alongWith(new ArmPick(0)),
        
        new ArmPick(2).alongWith(new Gripper(1, 70)),
        
        new ArmPick(1),
        new Gripper(0), 
        new MoveGripper(new Translation2d(0.4,0.3),0.5)
      );
      
    }
}