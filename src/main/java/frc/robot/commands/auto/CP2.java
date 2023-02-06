package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Arm;

public class CP2 extends SequentialCommandGroup{
  private final static Arm m_arm = RobotContainer.m_arm;
  // move and push trolley
  public CP2(){
    super(
      new InstantCommand(()-> m_arm.setCameraAngle(300)),
      new MovetoB(new Pose2d(0.5,2.4,new Rotation2d(0))) // Trolley location
    );
  }
  
}
