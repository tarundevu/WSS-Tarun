package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotContainer;

public class TestColor extends SequentialCommandGroup{
  // Use for calibrating color alignment
    public TestColor(){
      super(
        new InstantCommand(()-> RobotContainer.m_vision.setColor("Red")), //change color
        new Align2Color()
      );
    }
}
