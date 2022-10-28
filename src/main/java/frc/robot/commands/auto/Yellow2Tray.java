package frc.robot.commands.auto;

import frc.robot.RobotContainer;
// import the commands
import frc.robot.commands.auto.MoveRobot;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class Yellow2Tray extends SequentialCommandGroup {

  public Yellow2Tray(){
  super(  
      new MoveRobot(1, -0.65, 0, 0, 0.4),
      new MoveRobot(0,1.5,0,0.25,0.4),
      new MoveRobotSense(0, 0.7, 0.25, 0, 0.25, ()->RobotContainer.m_sensor.getCobraTotal()>=4094), //moves right
      new MoveRobot(1, -0.05, 0, 0, 0.25),
      new DetectObstacle(), //moves forward
      new PickUp()
  );
  }
}