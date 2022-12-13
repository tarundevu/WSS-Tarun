package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class CP2 extends SequentialCommandGroup{

  public CP2(){
    super(
      new MovetoB(new Pose2d()),
      new Trolley()
    );
  }
  
}
