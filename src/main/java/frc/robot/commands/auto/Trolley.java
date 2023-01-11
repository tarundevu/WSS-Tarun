package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Globals;
import frc.robot.RobotContainer;

public class Trolley extends SequentialCommandGroup{
  
  public Trolley(){
    super(
      new InstantCommand(() -> Globals.useTF = false),
      new InstantCommand(RobotContainer.m_vision::setUseTF),
      new AlignRobot(100,110,"trolley"),
      new MoveRobot(1, 0.12, 0, 0, 0.1)
      
    );
  }
}
