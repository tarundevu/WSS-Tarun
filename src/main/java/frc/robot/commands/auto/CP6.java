package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotContainer;
import frc.robot.Astar.Layout;
import frc.robot.subsystems.Arm;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;

public class CP6 extends SequentialCommandGroup{
  private final static Arm m_arm = RobotContainer.m_arm;
  // pick up trolley and move it to target area
  public CP6(){
    super(
      new MoveCamera(286),
      new GotoTrolley(Layout.T1Pos),
      new TrolleyHolder(1),
      new GotoColor(Layout.RedPos),
      new TrolleyHolder(0)
    );
  }
}