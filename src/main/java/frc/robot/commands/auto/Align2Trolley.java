package frc.robot.commands.auto;


import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Globals;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Vision;

public class Align2Trolley extends SequentialCommandGroup{
  private final static Vision m_vision = RobotContainer.m_vision;
  public Align2Trolley(){
    super(
      new InstantCommand(()-> Globals.cvMode = 0),
      new InstantCommand(m_vision::setcvMode),
      new AlignRobot("trolley"),
      new InstantCommand(()-> Globals.cvMode=-1),
      new InstantCommand(m_vision::setcvMode),
      new MoveRobot(1, 0.13, 0, 0, 0.1)
      
    );
  }
}
