package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class ColorPosition extends SequentialCommandGroup{
  public ColorPosition(){
    super(
      // Lifts arm
      new MoveArm(new Translation2d(0.33,0.3), 0.5),
      new ParallelCommandGroup(new MoveArm(new Translation2d(0.33,0.24), 0.5),
      // move camera to horizontal position
                          new MoveCamera(265))
      
  
    );
  }
}