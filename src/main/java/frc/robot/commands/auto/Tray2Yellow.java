package frc.robot.commands.auto;

import frc.robot.RobotContainer;
// import the commands
import frc.robot.commands.auto.MoveRobot;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class Tray2Yellow extends SequentialCommandGroup {

  public Tray2Yellow(){
  super(  
    new MovetoB(new Pose2d(1.00, 3.8, new Rotation2d(0))),
    new MoveRobot(2, Math.PI/2, 0, 0, Math.PI),
    new MoveRobotSense(0, 1, 0, 0, 0.2, ()->RobotContainer.m_sensor.getCobraTotal()>=4094),
    new MoveRobot(1, -0.05, 0, 0, 0.25),
    new MoveRobotSense(1,0.4,0,0,0.15,()->RobotContainer.m_sensor.getIRDistance()<=10), // moves forward
    new PickDown()
  );
  }
}
