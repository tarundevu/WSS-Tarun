package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Globals;
import frc.robot.RobotContainer;

public class TestLine extends SequentialCommandGroup{
  // Use for calibrating line alignment
  public TestLine(){
    super(
      // Lifts arm up and close gripper
      new DetectionPosition().alongWith(new Gripper(0,80)),
      // sets cv mode to line detection
      new InstantCommand(()-> RobotContainer.m_vision.setColor("Black")),
      new InstantCommand(()-> Globals.cvMode = 0),
      new WaitCommand(0.5),
      // aligns to line
      new AlignRobot(194,175,true),
      // resets cv mode to idle mode
      new InstantCommand(()-> Globals.cvMode=-1),
      // wait 2 secs
      new WaitCommand(2)
    );
  }
}
