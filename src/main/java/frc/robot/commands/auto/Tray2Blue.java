package frc.robot.commands.auto;

import frc.robot.RobotContainer;
// import the commands
import frc.robot.commands.auto.MoveRobot;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class Tray2Blue extends SequentialCommandGroup {

  public Tray2Blue(){
  super(  
      new MoveRobot(1, -0.4, 0, 0, 0.4),
            new MoveRobot(2, Math.PI, 0, 0, 0.4),
            new MoveRobot(0, 1.5, 0, 0.25, 0.4),
            new MoveRobotSense(0, 0.7, 0.25, 0, 0.25, ()->RobotContainer.m_sensor.getCobraTotal()>=4094),
            new MoveRobot(1, -0.05, 0, 0, 0.25),
            new DetectObstacle(),
            new PickDown()
  );
  }
}