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
      new MoveArm(new Translation2d(0.35,0),0.5),
      new Gripper(1,60),
      new WaitCommand(1),
      new MoveArm(new Translation2d(0.35,0),0.5)
      );
      
    }
}