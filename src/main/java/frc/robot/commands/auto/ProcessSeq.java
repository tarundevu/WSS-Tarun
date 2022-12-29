package frc.robot.commands.auto;

import frc.robot.RobotContainer;
// import the commands
import frc.robot.commands.auto.MoveRobot;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class ProcessSeq extends SequentialCommandGroup {

  public ProcessSeq(){
  super(  
      // new Tray2Yellow(),
      // new Yellow2Tray(),
      // new Tray2Blue(),
      // new Blue2Tray()
      new ObjectDetection()
  );
  }
}