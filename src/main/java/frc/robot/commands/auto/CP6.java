package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Arm;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;

public class CP6 extends SequentialCommandGroup{
  private final static Arm m_arm = RobotContainer.m_arm;
  public CP6(){
    super(
      new InstantCommand(()-> m_arm.setCameraAngle(280)),
      new MoveRobot(1, 1, 0, 0, 0.5),
      new MovetoB(new Pose2d(1.5,2.66,new Rotation2d(0))),
      new MoveRobot(2, -Math.PI/2, 0, 0, 0.25),
      new Trolley(),
      new TrolleyHolder(1),
      new MovetoB(new Pose2d(0.6,3.5,new Rotation2d(0)))// in front of target area
    );
  }
}
