package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import java.util.Map;


import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SelectCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Globals;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.OmniDrive;
import frc.robot.subsystems.Vision;
import frc.robot.utils.OmniDriveOdometry;

public class GotoColor extends SequentialCommandGroup {
  private final static OmniDrive m_omnidrive = RobotContainer.m_omnidrive;
  private static double m_x, m_y;
  private static double angle;

  private enum CommandSelector {
    Top, Left, Right, Bottom, TL, TR, BL
  }

  static public CommandSelector Move() {

    if (m_y > 4.29 && m_x > 0.21 && m_x < 2.04)
      return CommandSelector.Left;
    else if (m_y < 0.21 && m_x > 0.21 && m_x < 2.04)
      return CommandSelector.Right;
    else if (m_x < 0.75 && m_y > 0.21 && m_y < 4.29)
      return CommandSelector.Bottom;
    else if (m_x > 2.04 && m_y > 4.29)
      return CommandSelector.TL;
    else if (m_x > 2.04 && m_y < 0.21)
      return CommandSelector.TR;
    else if (m_x < 0.21 && m_y > 4.29)
      return CommandSelector.BL;
    else
      return CommandSelector.Top;
  }

  public GotoColor(double x, double y) {
    super(new SelectCommand(
        Map.ofEntries(Map.entry(CommandSelector.Top, new MovetoB(new Pose2d(x - 0.39, y, new Rotation2d(0)))),
            Map.entry(CommandSelector.Left, new MovetoB(new Pose2d(x, y - 0.39, new Rotation2d(0)))),
            Map.entry(CommandSelector.Right, new MovetoB(new Pose2d(x, y + 0.39, new Rotation2d(0)))),
            Map.entry(CommandSelector.Bottom, new MovetoB(new Pose2d(x + 0.39, y, new Rotation2d(0)))),
            Map.entry(CommandSelector.TL, new MovetoB(new Pose2d(x - 0.35, y - 0.35, new Rotation2d(0)))),
            Map.entry(CommandSelector.TR, new MovetoB(new Pose2d(x - 0.35, y + 0.35, new Rotation2d(0)))),
            Map.entry(CommandSelector.BL, new MovetoB(new Pose2d(x + 0.35, y - 0.35, new Rotation2d(0))))),
        GotoColor::Move
      ),
      new RotatetoOrientation(m_omnidrive.Rotate2Obj(x, y))
      
      
    );
  
  }
}
