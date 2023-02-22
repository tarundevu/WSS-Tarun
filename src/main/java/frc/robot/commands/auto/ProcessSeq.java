package frc.robot.commands.auto;

import frc.robot.RobotContainer;
import frc.robot.Astar.Layout;
// import the commands
import frc.robot.commands.auto.MoveRobot;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Arm;

public class ProcessSeq extends SequentialCommandGroup {

  private final static Arm m_arm = RobotContainer.m_arm;
  public ProcessSeq(){
    super(  
        
        new PickItemfromBin(),
        
        // new MovetoB(new Pose2d(0.96, 1.5, new Rotation2d(0))),
        new GotoTrolley(Layout.Convert_mm_Pose2d(Layout.GreenPos)),
        new PlaceDown(),
        // new MoveArm(new Translation2d(0.33,0.3), 0.5), // Line detection position
        // new MoveArm(new Translation2d(0.33,0.24), 0.5), // Line detection position
        // new MovetoB(new Pose2d(0.96, 1.1, new Rotation2d(0))),
        new MovetoB(Layout.Convert_mm_Pose2d(Layout.PickUpBinPos)),
        

        new Rotate2Orientation(Layout.Convert_mm_Pose2d(Layout.PickUpBinPos).getRotation().getDegrees()),
        new Align2Line(),
        new ViewItem()

        // new MoveArm(new Translation2d(0.33,0.24), 0.5),
        // new WaitCommand(2),
        // new MoveArm(new Translation2d(0.33,0.05), 0.5),
        // new WaitCommand(2)
    );
  }
}