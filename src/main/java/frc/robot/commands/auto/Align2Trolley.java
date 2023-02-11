package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Globals;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Vision;

public class Align2Trolley extends SequentialCommandGroup{
  private final static Arm m_arm = RobotContainer.m_arm;
  private final static Vision m_vision = RobotContainer.m_vision;
  
  public Align2Trolley(){
    super(
      new MoveArm(new Translation2d(0.33,0.24), 0.5), // Line detection position
      new MoveCamera(290),
      new InstantCommand(()-> Globals.cvMode = 0),
      new AlignRobot("trolley"),
      new InstantCommand(()-> Globals.cvMode=-1),
      new WaitCommand(2),
      new MoveRobot(1, 0.03, 0, 0, 0.1)
      
    );
  }
}