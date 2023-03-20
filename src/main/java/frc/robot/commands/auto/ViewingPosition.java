package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.Globals;

public class ViewingPosition extends ParallelCommandGroup{
  public ViewingPosition(){
    super(
      // Lifts Arm
      new MoveArm(new Translation2d(0.24,0.1), 0.4),
      new MoveCamera(Globals.ViewingAngle)
    );
  }
}
