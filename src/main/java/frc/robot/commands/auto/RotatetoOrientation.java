package frc.robot.commands.auto;

import java.util.Map;
import frc.robot.Globals;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SelectCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Arm;
import frc.robot.utils.OmniDriveOdometry;

public class RotatetoOrientation extends SequentialCommandGroup {
  private final static OmniDriveOdometry m_odometry = RobotContainer.m_od;
  private static double angle = 0;
  private static double dir = 0;

  private enum CommandSelector {
    ONE, TWO
  }

  static public CommandSelector selectAngle() {
    if (Globals.curDir < dir)
      return CommandSelector.ONE;
    else if (Globals.curDir > dir)
      return CommandSelector.TWO;
    else
      return null;

  }

  /**
   * This command rotates the robot to an orientation with respect to the arena
   * @param angle - orientation to face
   */
  public RotatetoOrientation(double angle) 
    {
        super(   
        new SelectCommand(
                Map.ofEntries(
                    Map.entry(CommandSelector.ONE, new MoveRobotSense(2, 2*Math.PI, 0, 0, 0.3,()->m_odometry.getPose().getRotation().getDegrees()>angle)),
                    Map.entry(CommandSelector.TWO, new MoveRobotSense(2, -2*Math.PI, 0, 0, 0.3,()->m_odometry.getPose().getRotation().getDegrees()<angle))
                    ), 
                SortItems::selectTarget
        )
        );
        dir = angle;
    }
    
}
