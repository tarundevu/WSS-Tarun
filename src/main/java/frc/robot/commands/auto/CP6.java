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
  private static double trolley_x = 1.45,
                        trolley_y = 4.35;
  private static double target_x = 0.15,
                        target_y = 2.9;   
  // pick up trolley and move it to target area
  public CP6(){
    super(
      new InstantCommand(()-> m_arm.setCameraAngle(280)),
      // for trolley on front //
      // new MovetoB(new Pose2d(trolley_x-0.4     ,trolley_y,new Rotation2d(0))),
      // for trolley on right side //
      // new MovetoB(new Pose2d(trolley_x,trolley_y-0.4,new Rotation2d(0))),
      // new MoveRobot(2, Math.PI/2, 0, 0, 0.3),
      // // for trolley on back //
      // // new MovetoB(new Pose2d(trolley_x,trolley_y+0.4,new Rotation2d(0))),
      // // new MoveRobot(2, Math.PI, 0, 0, 0.3),

      // //new Trolley(),
      // //new TrolleyHolder(1),
      // // target at back
      // new MovetoB(new Pose2d(target_x+0.39,target_y,new Rotation2d(0))),// in front of target area
      // new MoveRobot(2, Math.PI/2, 0, 0, 0.3),
      // new MoveRobot(2, Math.PI, 0, 0, 0.3),
      // new MoveRobot(2, -Math.PI/2, 0, 0, 0.3)

      // latest code //
      new GotoTrolley(Layout.Convert_mm_Pose2d(Layout.T1Pos)),
      new TrolleyHolder(1),
      new GotoColor(Layout.Convert_mm_Pose2d(Layout.RedPos))
    );
  }
}
