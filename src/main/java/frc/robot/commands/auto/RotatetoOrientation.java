package frc.robot.commands.auto;

import java.util.Map;
import edu.wpi.first.wpilibj2.command.SelectCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Globals;
import frc.robot.RobotContainer;
import frc.robot.subsystems.OmniDrive;

public class RotatetoOrientation extends SequentialCommandGroup {
  public final static OmniDrive m_omnidrive = RobotContainer.m_omnidrive;
  private static double dir;

  private enum CommandSelector {
    POS, NEG
  }
  //Checks whether angle is negative
  public static CommandSelector selectCmd12() {
    if (Globals.curDir < dir)
      return CommandSelector.POS;
    else if (Globals.curDir > dir) {
      return CommandSelector.NEG;
    } else
      return null;

  }

  public RotatetoOrientation(double angle) {

    super(new SelectCommand(
        Map.ofEntries(
          Map.entry(CommandSelector.POS, new MoveRobotSense(2,2*Math.PI,0,0,0.3,()->m_omnidrive.getDir()>angle)), 
          Map.entry(CommandSelector.NEG, new MoveRobotSense(2,-2*Math.PI,0,0,0.3,()->m_omnidrive.getDir()<angle))),

        RotatetoOrientation::selectCmd12
           
        )
        );
        dir = angle;
    }
}