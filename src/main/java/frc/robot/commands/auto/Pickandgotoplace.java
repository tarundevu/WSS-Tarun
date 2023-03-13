package frc.robot.commands.auto;

import java.util.Map;

import edu.wpi.first.wpilibj2.command.SelectCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Astar.Layout;

public class Pickandgotoplace extends SequentialCommandGroup{
  public Pickandgotoplace(){
    super(
    //   new PickItemfromBin(),
    //   new SelectCommand(
    //     Map.ofEntries(
    //         Map.entry(CommandSelector.ONE, new GotoTrolley(Layout.Convert_mm_Pose2d(Layout.T3Pos))),
    //         Map.entry(CommandSelector.TWO, new GotoTrolley(Layout.Convert_mm_Pose2d(Layout.T2Pos))),
    //         Map.entry(CommandSelector.THREE, new GotoTrolley(Layout.Convert_mm_Pose2d(Layout.T1Pos)))
    //         ), 
    //     SortItems::selectTarget
    //     )
    );
  }
}
