package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Globals;
import frc.robot.Robot;
import frc.robot.RobotContainer;
// import frc.robot.Astar.Layout;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.OmniDrive;
import frc.robot.subsystems.Sensor;
import frc.robot.subsystems.Vision;
// This command is used to align to the black line and move towards the bin
public class Align2Line extends SequentialCommandGroup{
  private final static Arm m_arm = RobotContainer.m_arm;
  private final static Sensor m_sensor = RobotContainer.m_sensor;
  private final static Vision m_vision = RobotContainer.m_vision;
  private final static OmniDrive m_omnidrive = RobotContainer.m_omnidrive;
  // aligns to line
  public Align2Line(){
    super(
      // Lifts arm up and close gripper
      new DetectionPosition().alongWith(new Gripper(0,80)),
      // sets cv mode to line detection
      new InstantCommand(()-> RobotContainer.m_vision.setColor("Black")),
      new InstantCommand(()-> Globals.cvMode = 0),
      new WaitCommand(1), // Org 0.5
      // aligns to line
      new AlignRobot(194,175,true),
      // resets cv mode to idle mode
      new InstantCommand(()-> Globals.cvMode=-1),
      // wait 2 secs
      new WaitCommand(2),
      // resets robot's position
      new ResetPosition(),
      // moves forward until robot is 15 cm away
      new MoveRobotSense(1, 0.3, 0, 0,0.25, ()-> m_sensor.getIRDistance()<=15)
    );
  }
  
}