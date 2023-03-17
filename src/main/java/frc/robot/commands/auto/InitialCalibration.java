package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Globals;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.Astar.Layout;
// import frc.robot.Astar.Layout;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.OmniDrive;
import frc.robot.subsystems.Sensor;
import frc.robot.subsystems.Vision;
// This command is used to move the robot to align to the black line for initial calibration of robot pose
public class InitialCalibration extends SequentialCommandGroup{
  private final static Vision m_vision = RobotContainer.m_vision;
  public InitialCalibration(){
    super(
      // moves to the 1st pick up bin
      new MovetoB(Layout::getCalibrationPos),
      new Rotate2Orientation(Layout::getCalibrationPos),
      // Lifts arm up and close gripper
      new DetectionPosition().alongWith(new Gripper(0,80)),
      // sets cv mode to line detection
      new InstantCommand(()-> m_vision.setColor("Black")),
      new InstantCommand(()-> Globals.cvMode = 0),
      new WaitCommand(0.5),
      // aligns to line
      new AlignRobot(195,175,true),
      // resets cv mode to idle mode
      new InstantCommand(()-> Globals.cvMode=-1),
      // wait 2 secs
      new WaitCommand(2),
      // resets robot's position
      new ResetPosition()
    );
  }
}
