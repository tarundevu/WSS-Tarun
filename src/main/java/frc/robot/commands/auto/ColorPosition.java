package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

public class ColorPosition extends ParallelCommandGroup{
  public ColorPosition(){
    super(
      // Lifts arm
      new DetectionPosition(),
      // move camera to horizontal position
      new MoveCamera(265)
  
    );
  }
}
