package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Globals;
import frc.robot.RobotContainer;
import frc.robot.Astar.Layout;
import frc.robot.subsystems.Arm;

public class CP1 extends SequentialCommandGroup{
  private final static Arm m_arm = RobotContainer.m_arm;
  public CP1(){
    super(
      // set arm to vertical position
      new MoveArm(new Translation2d(0.33,0.24), 0.5), // Line detection position
      // close gripper
      new Gripper(0, 150),
      new InstantCommand(()-> m_arm.setCameraAngle(280)),
      new WaitCommand(1),
      new MovetoB(new Pose2d(0.70,0.95,new Rotation2d(0))),
      new MoveRobot(2, -Math.PI/2, 0, 0, 0.25),
      new InstantCommand(() -> Globals.useTF = false),
      new InstantCommand(RobotContainer.m_vision::setUseTF),
      new WaitCommand(3),
      // align robot to black line
      new AlignRobot(),
      // move forward till robot is 15cm away
      new MoveRobotSense(1,0.3,0,0,.5,() -> RobotContainer.m_sensor.getIRDistance() <= 15),
      new MoveArm(new Translation2d(0.24,0.1), 0.5),
       // object detetction position
      new InstantCommand(()-> m_arm.setCameraAngle(265)),
      new InstantCommand(() -> Globals.useTF = true),
      new InstantCommand(RobotContainer.m_vision::setUseTF),
      // set item to be picked
      new InstantCommand(() -> Globals.curItem = 1),
      new WaitCommand(4),
      // align robot to object
      new AlignPicker(0.4),
      new InstantCommand(()-> m_arm.setCameraAngle(240)),
      new PickUp(),
      new MoveArm(new Translation2d(0.33,0.24), 0.5)
    );
  }
  
}
