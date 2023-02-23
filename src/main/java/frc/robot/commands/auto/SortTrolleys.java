package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Astar.Layout;

public class SortTrolleys extends SequentialCommandGroup{

    public SortTrolleys(){
        super(
            new GotoTrolley(Layout.Convert_mm_Pose2d(Layout.T1Pos)),
            new TrolleyHolder(1),
            
            new GotoColor(Layout.Convert_mm_Pose2d(Layout.BluePos)),
            new TrolleyHolder(0),
            new MoveRobot(1, -0.05, 0, 0, 0.1),
            
            new GotoTrolley(Layout.Convert_mm_Pose2d(Layout.T2Pos)),
            new TrolleyHolder(1),
            
            new GotoColor(Layout.Convert_mm_Pose2d(Layout.RedPos)),
            new TrolleyHolder(0),
            new MoveRobot(1, -0.05, 0, 0, 0.1),
            
            new GotoTrolley(Layout.Convert_mm_Pose2d(Layout.T3Pos)),
            new TrolleyHolder(1),
            
            new GotoColor(Layout.Convert_mm_Pose2d(Layout.GreenPos)),
            new TrolleyHolder(0),
            new MoveRobot(1, -0.05, 0, 0, 0.1)
        );
    }
    
}