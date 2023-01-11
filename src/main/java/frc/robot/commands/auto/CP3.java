package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Arm;

public class CP3 extends SequentialCommandGroup{
  private final static Arm m_arm = RobotContainer.m_arm;
  public CP3(){
    super(
      new Gripper(1, 90),
      new WaitCommand(5),
      new Gripper(0, 90),
      new PlaceDown()
    );
  }
}
