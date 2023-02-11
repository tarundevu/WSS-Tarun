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
import frc.robot.Astar.Layout;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.OmniDrive;
import frc.robot.subsystems.Vision;
import frc.robot.utils.OmniDriveOdometry;

public class GotoTrolley extends SequentialCommandGroup {
  private final static OmniDrive m_omnidrive = RobotContainer.m_omnidrive;
  
 
  /**
   * This command moves the robot in front of the trolley and rotates to face it
   * @param pose - Coordinates of trolley in Pose2d(Use Layout.Convert_mm_Pose2d(int[] from Layout))
   * 
   */
  public GotoTrolley(Pose2d pose) {
    super(
      
      new MovetoB(new Pose2d(m_omnidrive.getCoord(pose.getTranslation(),"trolley")[0], m_omnidrive.getCoord(pose.getTranslation(),"trolley")[1], new Rotation2d(0))),
      new Rotate2Orientation(pose.getRotation().getDegrees()),
      new Align2Trolley(),
      new WaitCommand(1)
      
    );
  }
}