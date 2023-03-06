package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Globals;
import frc.robot.RobotContainer;
//This command aligns the robot to the target area
public class Align2Color extends SequentialCommandGroup {
  /**
   * This command aligns the robot to the target area <p>
   * RMBR! set the color mode to the respective color before calling this command
   */
  public Align2Color(){
    super(
      new ColorPosition(),
      // sets cvMode to trolley alignment
      new InstantCommand(()-> Globals.cvMode = 0),

      // RMBR IMPORTANT!!!!! setColormode before calling Align2Color
      new WaitCommand(2),
      new AlignRobot(195,180, false),
      // resets cvMode to idle
      new InstantCommand(()-> Globals.cvMode=-1),
      // new WaitCommand(2),
      new MoveRobot(1, 0.32, 0, 0, 0.1)
    );
  }
}
