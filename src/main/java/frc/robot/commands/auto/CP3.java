package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class CP3 extends SequentialCommandGroup{
  public CP3(){
    super(
      new Gripper(0, 150),
      new MovetoB(null),
      new Trolley(),
      new PlaceDown()
    );
  }
}
