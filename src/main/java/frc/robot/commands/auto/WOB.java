package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Vision;

public class WOB extends SequentialCommandGroup{
  private final static Arm m_arm = RobotContainer.m_arm;
  private final static Vision m_vision = RobotContainer.m_vision;
  public WOB() 
    {
 
        
        super(   
        new ViewItem(),
        new PickItem(),
        new InstantCommand(()-> m_arm.setCameraAngle(280)),
        new MovetoB(new Pose2d(0.96, 1.5, new Rotation2d(0))),
        new PlaceDown(),
        new MoveArm(new Translation2d(0.33,0.24), 0.5),
        new MovetoB(new Pose2d(0.96, 1.1, new Rotation2d(0)))
        );
    }
    
}
