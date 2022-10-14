package frc.robot.commands.auto;

import frc.robot.RobotContainer;
// import the commands
import frc.robot.commands.auto.MoveRobot;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class Tray2Yellow extends SequentialCommandGroup {

  public Tray2Yellow(){
  super(  
    new MoveRobot(0, -2, 0, 0, 0.4), // moves left
    new MoveRobot(1, 0.5, 0, 0, 0.4), // moves forward
    new MoveRobotSense(0, -0.5, 0, 0, 0.25, ()->RobotContainer.m_sensor.getCobraTotal()>=4094), //moves left
    new MoveRobot(1, -0.05, 0, 0, 0.25),
    new DetectObstacle(), // moves forward
    new Pick()
  );
  }
}
