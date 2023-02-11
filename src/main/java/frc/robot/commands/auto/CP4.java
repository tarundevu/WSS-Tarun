package frc.robot.commands.auto;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
// import frc.robot.subsystems.Arm;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Arm;

public class CP4 extends SequentialCommandGroup{
  private final static Arm m_arm = RobotContainer.m_arm;
  // Move to target area
  public CP4(){
    super(
      new MoveCamera(290),
      new MoveArm(new Translation2d(0.33,0.24), 0.5), // Line detection position
      new MovetoB(new Pose2d(1.0,3.5,new Rotation2d(0))) //target posisiton
    );
  }
}