package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.RobotContainer;

public class ObjectDetection extends SequentialCommandGroup{
  public ObjectDetection(){
    super(
      //new InstantCommand(() -> RobotContainer.m_vision.ItemToPick()),
      new WaitCommand(5),
      new AlignPicker(),
      new PickUp(),
      //new MoveRobot(0, -0.3, 0, 0, 0.4),
      new MovetoB(new Pose2d(-0.94,1.1,new Rotation2d(0))),
      new WaitCommand(2)
    );
  }
}
