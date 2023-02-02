package frc.robot.commands.auto;

import java.util.Map;

import javax.lang.model.util.ElementScanner6;

import edu.wpi.first.wpilibj2.command.SelectCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Globals;
import frc.robot.RobotContainer;
import frc.robot.subsystems.OmniDrive;

public class RotatetoOrientation2 extends SequentialCommandGroup {
  public final static OmniDrive m_omnidrive = RobotContainer.m_omnidrive;
  private static double dir;

  private enum CommandSelector {
    POS, NEG
  }
  //Checks whether angle is negative and changes direction
  public static CommandSelector selectCmd12() {
    if (Globals.curDir < dir)
      return CommandSelector.POS;
    else if (Globals.curDir > dir) {
      return CommandSelector.NEG;
    } else
      return null;

  }
  /**
   * This command rotates the robot to face a certain orientation
   * @param angle - angle to rotate to (Robot starts at -90) (NOTE: Use 179.9 for 180)
   */
  public RotatetoOrientation2(double angle) {

    super(

            new MoveRobot(2,angle,0,0,0.3)

        );
            angle -= m_omnidrive.getDir();
            if (angle>180)
                angle -= 360;
            else if (angle<-180)
                angle += 360;
            else 
                angle *= 1;
            angle *= (Math.PI/180);
    }
}