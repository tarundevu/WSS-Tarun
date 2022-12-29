package frc.robot.commands.auto;

import frc.robot.RobotContainer;
// import the commands
import frc.robot.commands.auto.MoveRobot;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class Tray2Yellow extends SequentialCommandGroup {

  public Tray2Yellow(){
  super(  
    // moves beside the yellow box
    new MovetoB(new Pose2d(0.65, 3.8, new Rotation2d(0))),
    // //### Release Trolley ###//        
    new ReleaseTrolley(),
    // positions arm to grab the item
    new MoveArm(new Translation2d(0.3, -0.02), 0.4),
    // closes gripper
    new Gripper(1, 150),
    new MoveCamera(280, 100),
    // lifts arm up for alignment with camera
    new MoveArm(new Translation2d(0.328, 0.25), 0.4),// puts arm up
    // moves back for clearance
    new MoveRobot(1, -0.6, 0, 0, 0.25),
    // moves in front of the yellow box
    new MoveRobot(0, 0.5, 0, 0, 0.25),
    //GIves time for the gripper to stop shaking
    new WaitCommand(1),
    // uses cobra sensor for alignment
    // new MoveRobotSense(0, 1, 0, 0, 0.2, ()->RobotContainer.m_sensor.getCobraTotal()>=4094),
    // aligns the robot using the camera
    new AlignRobot(),
    // moves back for clearance
    new MoveRobot(1, -0.05, 0, 0, 0.25),
    // moves forward for clearance
    new MoveRobotSense(1,0.4,0,0,0.15,()->RobotContainer.m_sensor.getIRDistance()<=10), // moves forward
    // places item down
    new PlaceDown(),
    // moves back for clearance
    new MoveRobot(1, -0.1, 0, 0, 0.4)
    //new MovetoB(new Pose2d(0.65, 3.8, new Rotation2d(0)))
  );
  }
}
