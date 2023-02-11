package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Astar.Layout;

public class SortTrolley extends SequentialCommandGroup{

    public SortTrolley(){
        super(
            new GotoTrolley(Layout.Convert_mm_Pose2d(Layout.T1Pos)),
            new TrolleyHolder(1),

            new GotoColor(Layout.Convert_mm_Pose2d(Layout.RedPos)),
            new TrolleyHolder(0),

            new GotoTrolley(Layout.Convert_mm_Pose2d(Layout.T2Pos)),
            new TrolleyHolder(1),

            new GotoColor(Layout.Convert_mm_Pose2d(Layout.GreenPos)),
            new TrolleyHolder(0),

            new GotoTrolley(Layout.Convert_mm_Pose2d(Layout.T3Pos)),
            new TrolleyHolder(1),

            new GotoColor(Layout.Convert_mm_Pose2d(Layout.BluePos)),
            new TrolleyHolder(0)
        );
    }

}