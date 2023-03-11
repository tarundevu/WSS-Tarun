package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Globals;

public class Task_B extends SequentialCommandGroup{
  public Task_B(){
    super(
      // Puts camera in viewing position
      new MoveArm(new Translation2d(0.3,0.4), 2),
      new MoveCamera(277),
      
      // Sets the python script to perspective transformation with tensorflow model
      new InstantCommand(() -> Globals.cvMode = 4),

      // Move out of starting position
      new MoveRobot(0, -0.05, 0, 0, 5),
      new MoveRobot(1, 0.25, 0, 0, 5),

      // Mapping movement sequence
      new loopMoveRobotWaypoint(),
      new LoopCmd(new loopMoveRobotWaypoint(), () -> Globals.endConditionTaskBMapping())
      // new CheckAndMoveTarget("T0")
      
      
      // new MovetoPoint("T2")
      // new MovetoPoint("T3"),
      

    );
  }
}
