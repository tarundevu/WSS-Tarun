package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotContainer;
import frc.robot.Astar.Layout;

public class SortTrolleys extends SequentialCommandGroup{

    public SortTrolleys(){
        super(
            new GotoTrolley(Layout.Convert_mm_Pose2d(Layout.T1Pos)),
            new TrolleyHolder(1),
            
            new InstantCommand(()-> RobotContainer.m_vision.setColor("Blue")),
            new GotoColor(Layout.Convert_mm_Pose2d(Layout.BluePos)),
            new TrolleyHolder(0),
            new MoveRobot(1, -0.05, 0, 0, 0.1),
            new InstantCommand(()-> RobotContainer.m_points.updateObsPoint("BlueTarget", Layout.Convert_mm_Pose2d(Layout.BluePos))),
            new InstantCommand(() -> RobotContainer.m_points.AddObsGrid()),
            
            new GotoTrolley(Layout.Convert_mm_Pose2d(Layout.T2Pos)),
            new TrolleyHolder(1),
            
            new InstantCommand(()-> RobotContainer.m_vision.setColor("Green")),
            new GotoColor(Layout.Convert_mm_Pose2d(Layout.GreenPos)),
            new TrolleyHolder(0),
            new MoveRobot(1, -0.05, 0, 0, 0.1),
            new InstantCommand(()-> RobotContainer.m_points.updateObsPoint("GreenTarget", Layout.Convert_mm_Pose2d(Layout.GreenPos))),
            new InstantCommand(() -> RobotContainer.m_points.AddObsGrid()),

            new GotoTrolley(Layout.Convert_mm_Pose2d(Layout.T3Pos)),
            new TrolleyHolder(1),
            
            new InstantCommand(()-> RobotContainer.m_vision.setColor("Red")),
            new GotoColor(Layout.Convert_mm_Pose2d(Layout.RedPos)),
            new TrolleyHolder(0),
            new MoveRobot(1, -0.05, 0, 0, 0.1),
            new InstantCommand(()-> RobotContainer.m_points.updateObsPoint("RedTarget", Layout.Convert_mm_Pose2d(Layout.RedPos))),
            new InstantCommand(() -> RobotContainer.m_points.AddObsGrid())
        );
    }
    
}