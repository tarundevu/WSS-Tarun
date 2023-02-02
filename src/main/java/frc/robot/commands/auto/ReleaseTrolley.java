package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
// this class is used for the previous gripper (NOT IN USE)
public class ReleaseTrolley extends SequentialCommandGroup {
  public ReleaseTrolley(){
    super(
      new MoveArm(new Translation2d(0.32,0),0.5),
      new Gripper(0,150)
    );
  }
}
