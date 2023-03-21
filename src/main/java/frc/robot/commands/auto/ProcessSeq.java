package frc.robot.commands.auto;

import frc.robot.RobotContainer;
import frc.robot.Astar.Layout;
// import the commands
import frc.robot.commands.auto.MoveRobot;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Arm;

public class ProcessSeq extends SequentialCommandGroup {
// Use for CP1
  private final static Arm m_arm = RobotContainer.m_arm;
  public ProcessSeq(){
    super(  
        
        new PickItemfromBin()
        // new ViewItem()

        // new MoveArm(new Translation2d(0.33,0.24), 0.5),
        // new WaitCommand(2),
        // new MoveArm(new Translation2d(0.33,0.05), 0.5),
        // new WaitCommand(2)
    );
  }
}