package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Globals;

public class WOBPosition extends ParallelCommandGroup{
  public WOBPosition(){
    super(
      new MoveArm(new Translation2d(0.26,0), 0.5), // WOB position
      new MoveCamera(Globals.WOBAngle)
    );
  }
}
