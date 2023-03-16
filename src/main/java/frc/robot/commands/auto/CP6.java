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
      new DetectionPosition(),
      // new GotoTrolley(Layout.T1Pos),
      new MovetoB(new Pose2d(RobotContainer.m_omnidrive.getCoord(Layout.T1Pos.getTranslation(),"trolley"), new Rotation2d(0))),
      new Rotate2Orientation(Layout.T1Pos.getRotation().getDegrees()),
      new TrolleyHolder(1),
      new InstantCommand(()-> RobotContainer.m_vision.setColor("Red")),
      // new GotoColor(Layout.RedPos),
      new MovetoB(new Pose2d(RobotContainer.m_omnidrive.getCoord(Layout.RedPos.getTranslation(),"color"), new Rotation2d(0))),
      new Rotate2Orientation(Layout.RedPos.getRotation().getDegrees()),
      new TrolleyHolder(0)
    );
  }
}