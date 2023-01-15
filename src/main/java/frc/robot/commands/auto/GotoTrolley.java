package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import java.util.Map;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SelectCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Globals;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Vision;

public class GotoTrolley extends SequentialCommandGroup{
  private static double m_x,m_y;
  private enum CommandSelector {
    Top, Left, Right, Bottom, TL, TR, BL
  }

  static public CommandSelector Move() {
  
    if (m_y > 4.92 && m_x > 0.21 && m_x < 2.04)
        return CommandSelector.Left;
    else if (m_y < 0.21 && m_x > 0.21 && m_x < 2.04)
        return CommandSelector.Right;
    else if (m_x < 0.75 && m_y > 0.21 && m_y < 4.92)
        return CommandSelector.Bottom;
    else if (m_x > 2.04 && m_y > 4.92)
        return CommandSelector.TL;
    else if (m_x > 2.04 && m_y < 0.21)
        return CommandSelector.TR;
    else if (m_x < 0.21 && m_y > 4.92)
        return CommandSelector.BL;
    else 
        return CommandSelector.Top;
  }

  static public CommandSelector Rotate() {
  
    if (m_y > 4.92 && m_x > 0.21 && m_x < 2.04)
        return CommandSelector.Left;
    else if (m_y < 0.21 && m_x > 0.21 && m_x < 2.04)
        return CommandSelector.Right;
    else if (m_x < 0.75 && m_y > 0.21 && m_y < 4.92)
        return CommandSelector.Bottom;
    else 
        return null;
  }
  public GotoTrolley(double x, double y) {
    super(
      new SelectCommand(
        Map.ofEntries(
            Map.entry(CommandSelector.Top, new MovetoB(new Pose2d(x-0.5, y, new Rotation2d(0)))),
            Map.entry(CommandSelector.Left, new MovetoB(new Pose2d(x, y-0.5, new Rotation2d(0)))),
            Map.entry(CommandSelector.Right, new MovetoB(new Pose2d(x, y+0.5, new Rotation2d(0)))),
            Map.entry(CommandSelector.Bottom, new MovetoB(new Pose2d(x+0.5, y, new Rotation2d(0)))),
            Map.entry(CommandSelector.TL, new MovetoB(new Pose2d(x-0.35, y-0.35, new Rotation2d(0)))),
            Map.entry(CommandSelector.TR, new MovetoB(new Pose2d(x-0.35, y+0.35, new Rotation2d(0)))),
            Map.entry(CommandSelector.BL, new MovetoB(new Pose2d(x+0.35, y-0.35, new Rotation2d(0))))
            ), 
        GotoTrolley::Move
      ),
      new SelectCommand(
        Map.ofEntries(
            Map.entry(CommandSelector.Left, new MoveRobot(2, Math.PI/2, 0, 0, 0.3)),
            Map.entry(CommandSelector.Right, new MoveRobot(2, -Math.PI/2, 0, 0, 0.3)),
            Map.entry(CommandSelector.Bottom, new MoveRobot(2, 3*(Math.PI/4), 0, 0, 0.3)),
            Map.entry(CommandSelector.TL, new MoveRobot(2, Math.PI/4, 0, 0, 0.3)),
            Map.entry(CommandSelector.TR, new MoveRobot(2, -Math.PI/4, 0, 0, 0.3)),
            Map.entry(CommandSelector.BL, new MovetoB(new Pose2d(x+0.35, y-0.35, new Rotation2d(0))))
            ), 
        GotoTrolley::Rotate
      ),
      new Align2Trolley(),
      new TrolleyHolder(1)
    );
    m_x = x;
    m_y = y;
  }
  
}
