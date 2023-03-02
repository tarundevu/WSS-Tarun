package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Globals;
import frc.robot.RobotContainer;

public class Align2Color extends SequentialCommandGroup {
  public Align2Color(){
    super(
      // Lifts arm
      new MoveArm(new Translation2d(0.33,0.3), 0.5),
      new MoveArm(new Translation2d(0.33,0.24), 0.5),
      // move camera to horizontal position
      new MoveCamera(265),
      // Align to trolle
      // sets cvMode to trolley alignment
      new InstantCommand(()-> Globals.cvMode = 0),
      // RMBR IMPORTANT!! setColormode before calling Align2Color
      new WaitCommand(2),
      new AlignRobot(195,200, false),
      // resets cvMode to idle
      new InstantCommand(()-> Globals.cvMode=-1),
      // new WaitCommand(2),
      new MoveRobot(1, 0.25, 0, 0, 0.1)
    );
  }
}
