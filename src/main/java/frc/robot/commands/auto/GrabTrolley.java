package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
/*
 * This command is to be used with the previous gripper(NOT IN USE)
 */
public class GrabTrolley extends SequentialCommandGroup{
  public GrabTrolley(){
    super(
        new Gripper(0,150),
        new MoveArm(new Translation2d(0.42,-0.01),0.5),
        new Gripper(1, 150),
        new MoveArm(new Translation2d(0.32,0),0.5)

    );
  }
}
