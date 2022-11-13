package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.RobotContainer;

public class Trolley extends SequentialCommandGroup{
  
  public Trolley(){
    super(
      new MoveRobotSense(1, 0.5, 0, 0, 0.2, ()->RobotContainer.m_sensor.getIRDistance()<=10),
      
      new MoveRobot(1, 0.02, 0, 0, 0.1),
      new WaitCommand(0.5)
    );
  }
}
