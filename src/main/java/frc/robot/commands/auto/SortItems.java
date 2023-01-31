package frc.robot.commands.auto;

import java.util.Map;
import frc.robot.Globals;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SelectCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotContainer;
import frc.robot.Astar.Layout;
import frc.robot.subsystems.Arm;

public class SortItems extends SequentialCommandGroup{
  private final static Arm m_arm = RobotContainer.m_arm;
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
  public SortItems() 
    {
        super(   
        new PickItemfromBin(),
        new InstantCommand(()-> m_arm.setCameraAngle(280)),
        new SelectCommand(
                Map.ofEntries(
                    Map.entry(CommandSelector.ONE, new MovetoB(new Pose2d(0.96, 1.6, new Rotation2d(0)))),
                    Map.entry(CommandSelector.TWO, new MovetoB(new Pose2d(0.96, 2.0, new Rotation2d(0)))),
                    Map.entry(CommandSelector.THREE, new MovetoB(new Pose2d(0.96, 2.9, new Rotation2d(0))))
                    ), 
                SortItems::selectTarget
            ),
        new PlaceDown(),
        new MoveArm(new Translation2d(0.33,0.24), 0.5),
        new SelectCommand(
            Map.ofEntries(
                Map.entry(CommandSelector.ONE,new MovetoB(Layout.Convert_mm_Pose2d(Layout.PickUpBinPos))),
                Map.entry(CommandSelector.TWO, new MovetoB(Layout.Convert_mm_Pose2d(Layout.PickUpBin2Pos)))
                ), 
            SortItems::selectBin
        ),
        new Align2Line(),
        new ViewItem()
        );
    }
    
}