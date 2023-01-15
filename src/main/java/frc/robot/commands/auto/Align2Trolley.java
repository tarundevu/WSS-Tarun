package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Globals;
import frc.robot.RobotContainer;

public class Align2Trolley extends SequentialCommandGroup{
  
  public Align2Trolley(){
    super(
      new InstantCommand(()-> Globals.cvMode = 0),
      new AlignRobot("trolley"),
      new MoveRobot(1, 0.12, 0, 0, 0.1)
      
    );
  }
}
