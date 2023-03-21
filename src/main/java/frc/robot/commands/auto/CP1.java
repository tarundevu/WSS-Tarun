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
import frc.robot.subsystems.Vision;

public class CP1 extends SequentialCommandGroup{
  private final static Arm m_arm = RobotContainer.m_arm;
  private final static Vision m_vision = RobotContainer.m_vision;
  // Pick item from bin
  public CP1(){
    super(
      // set arm to vertical position
      new DetectionPosition(), // Line detection position
      new MoveRobotSense(1, 0.3, 0, 0,0.25, ()-> RobotContainer.m_sensor.getIRDistance()<=15),
      new ViewItem(),
      new OpenHseLoopCommand(new ProcessSeq())
     
    );
  }
  
}
