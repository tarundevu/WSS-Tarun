package frc.robot.commands.auto;

import java.util.Map;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SelectCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Globals;
import frc.robot.RobotContainer;
import frc.robot.Astar.Layout;
// The routine to transport the trolleys to the target areas
public class SortTrolleys extends SequentialCommandGroup{
   
    public SortTrolleys(){
        super(
           
            // new InstantCommand(() ->Globals.debug[0]=-1),

            // SEQUENCE 1 If trolley first // 
            // new GotoTrolley(Layout.Convert_mm_Pose2d(Layout.T1Pos)),
            // new Align2Trolley(),
            // new TrolleyHolder(1),
            
            // new InstantCommand(()-> RobotContainer.m_vision.setColor("Blue")),
            // new GotoColor(Layout.Convert_mm_Pose2d(Layout.BluePos)),
            // new TrolleyHolder(0),
            // new MoveRobot(1, -0.05, 0, 0, 0.1),
            // // new InstantCommand(() ->Globals.debug[0]=0),
            // new InstantCommand(()-> RobotContainer.m_points.updateObsPoint("BlueTarget", Layout.Convert_mm_Pose2d(Layout.BluePos))),
            // // new InstantCommand(() ->Globals.debug[0]=1),
            // new InstantCommand(() -> RobotContainer.m_points.AddObsGrid()),
            // // new InstantCommand(() ->Globals.debug[0]=2),

            // new GotoTrolley(Layout.Convert_mm_Pose2d(Layout.T2Pos)),
            // new Align2Trolley(),
            // // new InstantCommand(() ->Globals.debug[0]=3),
            // new TrolleyHolder(1),
            
            // new InstantCommand(()-> RobotContainer.m_vision.setColor("Green")),
            // new GotoColor(Layout.Convert_mm_Pose2d(Layout.GreenPos)),
            // new TrolleyHolder(0),
            // new MoveRobot(1, -0.05, 0, 0, 0.1),
            // new InstantCommand(()-> RobotContainer.m_points.updateObsPoint("GreenTarget", Layout.Convert_mm_Pose2d(Layout.GreenPos))),
            // new InstantCommand(() -> RobotContainer.m_points.AddObsGrid()),

            // new GotoTrolley(Layout.Convert_mm_Pose2d(Layout.T3Pos)),
            // new Align2Trolley(),
            // new TrolleyHolder(1),
            
            // new InstantCommand(()-> RobotContainer.m_vision.setColor("Red")),
            // new GotoColor(Layout.Convert_mm_Pose2d(Layout.RedPos)),
            // new TrolleyHolder(0),
            // new MoveRobot(1, -0.05, 0, 0, 0.1),
            // new InstantCommand(()-> RobotContainer.m_points.updateObsPoint("RedTarget", Layout.Convert_mm_Pose2d(Layout.RedPos))),
            // new InstantCommand(() -> RobotContainer.m_points.AddObsGrid())

            // SEQUENCE 2 If Pick first // 
            new GotoTrolley(Layout.Convert_mm_Pose2d(Layout.T3Pos)),
            new Align2Trolley(),
            new TrolleyHolder(1),
            new InstantCommand(()-> RobotContainer.m_points.removeObs("T3")), 
            
            new InstantCommand(()-> RobotContainer.m_vision.setColor("Red")),
            new GotoColor(Layout.Convert_mm_Pose2d(Layout.RedPos)),
            new TrolleyHolder(0),
            new MoveRobot(1, -0.05, 0, 0, 0.1),
            new InstantCommand(()-> RobotContainer.m_points.updateObsPoint("RedTarget", Layout.Convert_mm_Pose2d(Layout.RedPos))),
            new InstantCommand(() -> RobotContainer.m_points.AddObsGrid()),

            new GotoTrolley(Layout.Convert_mm_Pose2d(Layout.T2Pos)),
            new Align2Trolley(),
            new TrolleyHolder(1),
            new InstantCommand(()-> RobotContainer.m_points.removeObs("T2")),
            
            new InstantCommand(()-> RobotContainer.m_vision.setColor("Green")),
            new GotoColor(Layout.Convert_mm_Pose2d(Layout.GreenPos)),
            new TrolleyHolder(0),
            new MoveRobot(1, -0.05, 0, 0, 0.1),
            new InstantCommand(()-> RobotContainer.m_points.updateObsPoint("GreenTarget", Layout.Convert_mm_Pose2d(Layout.GreenPos))),
            new InstantCommand(() -> RobotContainer.m_points.AddObsGrid()),

            new GotoTrolley(Layout.Convert_mm_Pose2d(Layout.T1Pos)),
            new Align2Trolley(),
            new TrolleyHolder(1),
            new InstantCommand(()-> RobotContainer.m_points.removeObs("T1")),
            
            new InstantCommand(()-> RobotContainer.m_vision.setColor("Blue")),
            new GotoColor(Layout.Convert_mm_Pose2d(Layout.BluePos)),
            new TrolleyHolder(0),
            new MoveRobot(1, -0.05, 0, 0, 0.1),
            new InstantCommand(()-> RobotContainer.m_points.updateObsPoint("BlueTarget", Layout.Convert_mm_Pose2d(Layout.BluePos))),
            new InstantCommand(() -> RobotContainer.m_points.AddObsGrid())
        );
    }
    
}