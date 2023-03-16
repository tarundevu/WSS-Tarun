package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotContainer;

public class testing extends SequentialCommandGroup{
  public testing(Pose2d pose) {
    super(
      
      new MovetoB(RobotContainer.m_Grid.findGotoPos(pose.getTranslation(), 0.5))
      
    );
  }
}
