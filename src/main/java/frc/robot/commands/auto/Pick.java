package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
// import the commands
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile;
//WPI imports
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.networktables.NetworkTableEntry;

//RobotContainer import
import frc.robot.RobotContainer;


//Subsystem imports
//import frc.robot.subsystems.OmniDrive;
import frc.robot.subsystems.Arm;



/**
 * DriveMotor class
 * <p>
 * This class creates the inline auto command to drive the motor
 */
public class Pick extends SequentialCommandGroup
{
    
    public Pick()
    {
      
      super(
      new MoveArm(new Translation2d(0.2,0.1),0.5),
      new MoveArm(new Translation2d(0.35,0.1),0.5),
      new MoveArm(new Translation2d(0.35,0), 0.5),
      new MoveArm(new Translation2d(0.35,0.1), 0.5),
      new MoveArm(new Translation2d(0.2,0.1),0.5)
      );
      
    }
}