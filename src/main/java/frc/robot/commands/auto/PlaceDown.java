package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Globals;
import frc.robot.RobotContainer;
// import the commands
import edu.wpi.first.wpilibj.geometry.Translation2d;

//WPI imports


//RobotContainer import



//Subsystem imports

//import frc.robot.subsystems.Arm;



/**
 * DriveMotor class
 * <p>
 * This class creates the inline auto command to place objects down
 */
public class PlaceDown extends SequentialCommandGroup
{
    
    public PlaceDown()
    {
      
      super(
      // Moves arm down
      // new MoveArm(new Translation2d(0.25,0.12),0.5),
      new ArmPick(3),
      // opens gripper
      new Gripper(1,80),
      // wait 1 sec
      new WaitCommand(1),
      // Moves arm up
      
      new MoveArm(new Translation2d(0.25,0.25), 0.5).alongWith(new Gripper(0,80))
      );
      
    }
}