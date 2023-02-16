package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Globals;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Vision;

public class ReadWOB extends SequentialCommandGroup{
  private final static Arm m_arm = RobotContainer.m_arm;
  private final static Vision m_vision = RobotContainer.m_vision;
  public ReadWOB(){
    super(
      new InstantCommand(()-> Globals.cvMode = 2),
      new MoveArm(new Translation2d(0.3,0.05), 0.5), 
      new MoveCamera(167),
      new WaitCommand(2),
      new InstantCommand(()-> m_vision.getWOBItems()),
      new InstantCommand(()-> Globals.cvMode=-1),
      new WaitCommand(2),
      new MoveCamera(300),
      new MoveArm(new Translation2d(0.33,0.24), 0.5) // Line detection position
    );
  }
}