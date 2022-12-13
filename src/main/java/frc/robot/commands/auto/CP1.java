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

public class CP1 extends SequentialCommandGroup{

  public CP1(){
    super(
      // set arm to vertical position
      new MoveArm(new Translation2d(0.34,0.25), 50),
      // close gripper
      new Gripper(1, 150),
      new MovetoB(new Pose2d(Layout.PickUpBinPos[0][0],Layout.PickUpBinPos[0][1],new Rotation2d(Layout.PickUpBinPos[0][2]))),
      new InstantCommand(() -> Globals.useTF = false),
      new InstantCommand(RobotContainer.m_vision::setUseTF),
      new WaitCommand(3),
      // align robot to black line
      new AlignRobot(),
      // move forward till robot is 15cm away
      new MoveRobotSense(1,1,0,0,.5,() -> RobotContainer.m_sensor.getIRDistance() <= 15),
      new InstantCommand(() -> Globals.useTF = true),
      new InstantCommand(RobotContainer.m_vision::setUseTF),
      // set item to be picked
      new InstantCommand(() -> Globals.curItem = 1),
      new WaitCommand(5),
      // align robot to object
      new AlignPicker(50),
      new PickUp()
    );
  }
  
}
