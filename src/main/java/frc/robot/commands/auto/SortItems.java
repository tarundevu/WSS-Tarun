package frc.robot.commands.auto;

import java.util.Map;
import frc.robot.Globals;
import edu.wpi.first.wpilibj2.command.SelectCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Astar.Layout;

// The Routine for picking and transporting items to trolley/target area
public class SortItems extends SequentialCommandGroup{
  private enum CommandSelector {
    ONE, TWO, THREE
  }

  static public CommandSelector selectTarget() {
    if (Globals.curTarget == 0)
        return CommandSelector.ONE;
    else if (Globals.curTarget == 1)
        return CommandSelector.TWO;
    else if (Globals.curTarget == 2)
        return CommandSelector.THREE;
    else 
        return null;
    
  }
  static public CommandSelector selectBin() {
    
    if (Globals.curBin == 0)
        return CommandSelector.ONE;
    else 
        return CommandSelector.TWO;
    
  }
  static public CommandSelector selectRotation() {
    
    if (Globals.curBin == 0)
        return CommandSelector.ONE;
    else 
        return CommandSelector.TWO;
    
  }
  public SortItems() 
    {
        super(   
        new PickItemfromBin(),
        new MoveCamera(286),
        new SelectCommand(
            Map.ofEntries(
                Map.entry(CommandSelector.ONE, new GotoTrolley(Layout.Convert_mm_Pose2d(Layout.T1Pos))),
                Map.entry(CommandSelector.TWO, new GotoTrolley(Layout.Convert_mm_Pose2d(Layout.T2Pos))),
                Map.entry(CommandSelector.THREE, new GotoTrolley(Layout.Convert_mm_Pose2d(Layout.T3Pos)))
                ), 
            SortItems::selectTarget
            ),
        // new SelectCommand(
        //     Map.ofEntries(
        //         Map.entry(CommandSelector.ONE, new GotoTrolley(Globals.TrolleyList[0])),
        //         Map.entry(CommandSelector.TWO, new GotoTrolley(Globals.TrolleyList[1])),
        //         Map.entry(CommandSelector.THREE, new GotoTrolley(Globals.TrolleyList[2]))
        //         ), 
        //     SortItems::selectTarget
        //     ),
        new PlaceDown(),
        new MoveRobot(1, -0.05, 0, 0, 0.1),
        new SelectCommand(
            Map.ofEntries(
                Map.entry(CommandSelector.ONE,new MovetoB(Layout.Convert_mm_Pose2d(Layout.PickUpBinPos))),
                Map.entry(CommandSelector.TWO, new MovetoB(Layout.Convert_mm_Pose2d(Layout.PickUpBin2Pos)))
                ), 
            SortItems::selectBin
        ),
        new SelectCommand(
            Map.ofEntries(
                Map.entry(CommandSelector.ONE,new Rotate2Orientation(Layout.Convert_mm_Pose2d(Layout.PickUpBinPos).getRotation().getDegrees())),
                Map.entry(CommandSelector.TWO, new Rotate2Orientation(Layout.Convert_mm_Pose2d(Layout.PickUpBin2Pos).getRotation().getDegrees()))
                ), 
            SortItems::selectRotation
        ),
        
        new Align2Line(),
        new ViewItem()
        );
    }
    
}