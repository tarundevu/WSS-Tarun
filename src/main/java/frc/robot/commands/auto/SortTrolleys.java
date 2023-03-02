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
    private enum CommandSelector {
        ONE, TWO, THREE
      }
    
      static public CommandSelector selectSetColor() {
        if (Globals.TrolleyList[Globals.curTrolley] == Layout.Convert_mm_Pose2d(Layout.T1Pos))
            return CommandSelector.ONE;
        else if (Globals.TrolleyList[Globals.curTrolley] == Layout.Convert_mm_Pose2d(Layout.T2Pos))
            return CommandSelector.TWO;
        else if (Globals.TrolleyList[Globals.curTrolley] == Layout.Convert_mm_Pose2d(Layout.T3Pos))
            return CommandSelector.THREE;
        else 
            return null;
        
      }
      static public CommandSelector selectAddObstacle() {
        
        if (Globals.TrolleyList[Globals.curTrolley] == Layout.Convert_mm_Pose2d(Layout.T1Pos))
            return CommandSelector.ONE;
        else if (Globals.TrolleyList[Globals.curTrolley] == Layout.Convert_mm_Pose2d(Layout.T2Pos))
            return CommandSelector.TWO;
        else if (Globals.TrolleyList[Globals.curTrolley] == Layout.Convert_mm_Pose2d(Layout.T3Pos))
            return CommandSelector.THREE;
        else 
            return null;
        
      }
    public SortTrolleys(){
        super(
            new InstantCommand(()-> RobotContainer.m_omnidrive.FindNearestTarget()),// for testing
            // go to 1st trolley
            new InstantCommand(()-> Globals.curTrolley = 0),
            new GotoTrolley(Globals.TrolleyList[0]),
            new TrolleyHolder(1),
            // move to 1st color
            new SelectCommand(
            Map.ofEntries(
                Map.entry(CommandSelector.ONE, new InstantCommand(()-> RobotContainer.m_vision.setColor("Red"))),
                Map.entry(CommandSelector.TWO, new InstantCommand(()-> RobotContainer.m_vision.setColor("Green"))),
                Map.entry(CommandSelector.THREE, new InstantCommand(()-> RobotContainer.m_vision.setColor("Blue")))
                ), 
            SortTrolleys::selectSetColor
            ),
            new GotoColor(Globals.TargetList[0]),
            new TrolleyHolder(0),
            new MoveRobot(1, -0.05, 0, 0, 0.1),
            new SelectCommand(
            Map.ofEntries(
                Map.entry(CommandSelector.ONE, new SequentialCommandGroup(new InstantCommand(()-> RobotContainer.m_points.updateObsPoint("RedTarget", Layout.Convert_mm_Pose2d(Layout.RedPos))),
                                                                          new InstantCommand(() -> RobotContainer.m_points.AddObsGrid()))),
                Map.entry(CommandSelector.TWO, new SequentialCommandGroup(new InstantCommand(()-> RobotContainer.m_points.updateObsPoint("GreenTarget", Layout.Convert_mm_Pose2d(Layout.GreenPos))),
                                                                          new InstantCommand(() -> RobotContainer.m_points.AddObsGrid()))),
                Map.entry(CommandSelector.THREE, new SequentialCommandGroup(new InstantCommand(()-> RobotContainer.m_points.updateObsPoint("BlueTarget", Layout.Convert_mm_Pose2d(Layout.BluePos))),
                                                                            new InstantCommand(() -> RobotContainer.m_points.AddObsGrid())))
                ), 
            SortTrolleys::selectAddObstacle
            ),
            
            new InstantCommand(()-> Globals.curTrolley = 1),
            
            new GotoTrolley(Globals.TrolleyList[1]),
            new TrolleyHolder(1),
            
            new SelectCommand(
            Map.ofEntries(
                Map.entry(CommandSelector.ONE, new InstantCommand(()-> RobotContainer.m_vision.setColor("Red"))),
                Map.entry(CommandSelector.TWO, new InstantCommand(()-> RobotContainer.m_vision.setColor("Green"))),
                Map.entry(CommandSelector.THREE, new InstantCommand(()-> RobotContainer.m_vision.setColor("Blue")))
                ), 
            SortTrolleys::selectSetColor
            ),
            new GotoColor(Globals.TargetList[1]),
            new TrolleyHolder(0),
            new MoveRobot(1, -0.05, 0, 0, 0.1),
            new SelectCommand(
            Map.ofEntries(
                Map.entry(CommandSelector.ONE, new SequentialCommandGroup(new InstantCommand(()-> RobotContainer.m_points.updateObsPoint("RedTarget", Layout.Convert_mm_Pose2d(Layout.RedPos))),
                                                                          new InstantCommand(() -> RobotContainer.m_points.AddObsGrid()))),
                Map.entry(CommandSelector.TWO, new SequentialCommandGroup(new InstantCommand(()-> RobotContainer.m_points.updateObsPoint("GreenTarget", Layout.Convert_mm_Pose2d(Layout.GreenPos))),
                                                                          new InstantCommand(() -> RobotContainer.m_points.AddObsGrid()))),
                Map.entry(CommandSelector.THREE, new SequentialCommandGroup(new InstantCommand(()-> RobotContainer.m_points.updateObsPoint("BlueTarget", Layout.Convert_mm_Pose2d(Layout.BluePos))),
                                                                            new InstantCommand(() -> RobotContainer.m_points.AddObsGrid())))
                  ), 
            SortTrolleys::selectAddObstacle
            ),
            new InstantCommand(()-> Globals.curTrolley = 2),

            new GotoTrolley(Globals.TrolleyList[2]),
            new TrolleyHolder(1),
            
            new SelectCommand(
            Map.ofEntries(
                Map.entry(CommandSelector.ONE, new InstantCommand(()-> RobotContainer.m_vision.setColor("Red"))),
                Map.entry(CommandSelector.TWO, new InstantCommand(()-> RobotContainer.m_vision.setColor("Green"))),
                Map.entry(CommandSelector.THREE, new InstantCommand(()-> RobotContainer.m_vision.setColor("Blue")))
                ), 
            SortTrolleys::selectSetColor
            ),
            new GotoColor(Globals.TargetList[2]),
            new TrolleyHolder(0),
            new MoveRobot(1, -0.05, 0, 0, 0.1),
            new SelectCommand(
            Map.ofEntries(
                Map.entry(CommandSelector.ONE, new SequentialCommandGroup(new InstantCommand(()-> RobotContainer.m_points.updateObsPoint("RedTarget", Layout.Convert_mm_Pose2d(Layout.RedPos))),
                                                                          new InstantCommand(() -> RobotContainer.m_points.AddObsGrid()))),
                Map.entry(CommandSelector.TWO, new SequentialCommandGroup(new InstantCommand(()-> RobotContainer.m_points.updateObsPoint("GreenTarget", Layout.Convert_mm_Pose2d(Layout.GreenPos))),
                                                                          new InstantCommand(() -> RobotContainer.m_points.AddObsGrid()))),
                Map.entry(CommandSelector.THREE, new SequentialCommandGroup(new InstantCommand(()-> RobotContainer.m_points.updateObsPoint("BlueTarget", Layout.Convert_mm_Pose2d(Layout.BluePos))),
                                                                            new InstantCommand(() -> RobotContainer.m_points.AddObsGrid())))
                ), 
            SortTrolleys::selectAddObstacle
            )
        );
    }
    
}