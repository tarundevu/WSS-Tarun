package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Globals;
import frc.robot.RobotContainer;

public class StartPosition extends SequentialCommandGroup {
  public StartPosition(){
    super(
      new StartOrientation(),
      new DetectionPosition(),
      new InstantCommand(()->Globals.cvMode = 0),
      new InstantCommand(()->RobotContainer.m_vision.setColor("Black")),
      new WaitCommand(2),
      new AlignRobot(235,265,true),
      new InstantCommand(()-> RobotContainer.m_omnidrive.initialise())
    );
  }
  
}
