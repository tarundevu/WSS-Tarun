package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class TestPicking extends SequentialCommandGroup{
  public TestPicking(){
    super(
      new ViewItem(),
      new OpenHseLoopCommand(new OpenHse())
    );
  }
  
}
