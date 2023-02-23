package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

public class DetectionPosition extends ParallelCommandGroup{
  public DetectionPosition(){
    super(
      // Lifts Arm
      
      new MoveArm(new Translation2d(0.33,0.24), 0.5),
      // move camera to horizontal position
      new MoveCamera(300)
    );
  }
  
}
