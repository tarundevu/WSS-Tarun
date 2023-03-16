package frc.robot.commands.auto;

import java.util.Map;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SelectCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Globals;
import frc.robot.RobotContainer;
import frc.robot.Astar.Layout;

public class ResetPosition extends SequentialCommandGroup{
  private enum CommandSelector {
    ONE, TWO
  }
  static public CommandSelector selectPosition() {
  
    if (Globals.curBin == 0)
        return CommandSelector.ONE;
    else 
        return CommandSelector.TWO;
    
  }
  /**
   * Resets the current position of the robot with the pick up bin coordinates
   */
  public ResetPosition(){
    super(
      new SelectCommand(
            Map.ofEntries(
                Map.entry(CommandSelector.ONE, new InstantCommand(()->RobotContainer.m_omnidrive.UpdatePosition(Layout.PickUpBinPos))),
                Map.entry(CommandSelector.TWO, new InstantCommand(()->RobotContainer.m_omnidrive.UpdatePosition(Layout.PickUpBin2Pos)))
               
                ), 
            ResetPosition::selectPosition
            )
    );
  }
  
}
