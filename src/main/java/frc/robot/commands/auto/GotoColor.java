package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import java.util.Map;

import edu.wpi.first.wpilibj.RobotController;
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
  /**
   * This command moves the robot in front of the target and rotates to face it
   * @param pose - Coordinates of target area in Pose2d(Use Layout.Convert_mm_Pose2d(int[] from Layout)) <p>
   * Aligns to color <p>
   * RMBR IMPORTANT!!!!! call new InstantCommand(()-> RobotContainer.m_vision.setColor("NAME OF COLOR")) before this command
   */
  public GotoColor(Pose2d pose) {
      super(
      // Gets offsetted coordinates from getCoord() which takes in the color coordinates as parameter
      new MovetoB(()->RobotContainer.m_Grid.findGotoPos(pose.getTranslation(), 0.6)),
      // // Rotates the robot to the orientation of the target area
      // new Rotate2Orientation(()RobotContainer.m_Grid.findGotoPos(pose.getTranslation(), 0.6).getRotation().getDegrees()),
      // Aligns to the color (RMBR IMPORTANT!!!!! setColormode before calling Align2Color)
      new Align2Color(),
      new WaitCommand(1)
      
    );
  }
  
  public GotoColor(String target) {
    super(
    // Gets offsetted coordinates from getCoord() which takes in the color coordinates as parameter
    new CheckAndMoveTarget(target, 0.7),
    // Aligns to the color (RMBR IMPORTANT!!!!! setColormode before calling Align2Color)
    new Align2Color(),
    new WaitCommand(1)
    
  );
}
}