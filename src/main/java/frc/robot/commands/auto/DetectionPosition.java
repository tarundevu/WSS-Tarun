package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Globals;

public class DetectionPosition extends SequentialCommandGroup{
  // This command moves the arm to the line/trolley detection position
  public DetectionPosition(){
    super(
      // Lifts Arm
      new MoveArm(new Translation2d(0.33,0.3), 0.5),
      //Parallel command group runs commands in parallel
      new ParallelCommandGroup(new MoveArm(new Translation2d(0.33,0.24), 0.5),
                               // move camera to horizontal position
                               new MoveCamera(Globals.NormalCameraAngle)
      )
      
    );
  }
  
}
