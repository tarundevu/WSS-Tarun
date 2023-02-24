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
        // Moves arm forward
        new ArmPick(0),
        // Moves arm to above the object and opens gripper
        new ArmPick(2).alongWith(new Gripper(1, 70)),
        // Moves arm down 
        new ArmPick(1),
        // Closes gripper
        new Gripper(0), 
        // Lifts arm up
        new MoveGripper(new Translation2d(0.4,0.3),0.25)
      );
      
    }
}