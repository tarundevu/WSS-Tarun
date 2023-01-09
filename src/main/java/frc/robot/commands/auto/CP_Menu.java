package frc.robot.commands.auto;

import java.util.Map;
import java.util.function.Supplier;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SelectCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
// import the commands
import frc.robot.commands.auto.MoveRobot;
import frc.robot.RobotContainer;

/**
 * Menu
 * <p>
 * This class creates a menu for selecting the Core programming tasks
 */
public class CP_Menu extends SequentialCommandGroup{
   
    private enum CommandSelector {
        ONE, TWO, THREE, FOUR, SIX, IDLE
    }

    static public CommandSelector selectCmd123() {
        if (RobotContainer.m_menu.getCP1()==true)
            return CommandSelector.ONE;
        else if (RobotContainer.m_menu.getCP2()==true){
            return CommandSelector.TWO;
        }
        else if (RobotContainer.m_menu.getCP3()==true){
            return CommandSelector.THREE;
        }
        else if (RobotContainer.m_menu.getCP4()==true){
            return CommandSelector.FOUR;
        }
        else if (RobotContainer.m_menu.getCP6()==true){
            return CommandSelector.SIX;
        }
        else if ((RobotContainer.m_menu.getCP2()||RobotContainer.m_menu.getCP3()||RobotContainer.m_menu.getCP4()||RobotContainer.m_menu.getCP6())==false){
            return CommandSelector.IDLE;
        }
        else 
            return null;
        
    }

    public CP_Menu()
    {
        super(
            
                new SelectCommand(
                Map.ofEntries(
                    
                    Map.entry(CommandSelector.ONE, new CP1()),
                    
                    Map.entry(CommandSelector.TWO, new CP2()),

                    Map.entry(CommandSelector.THREE, new CP3()),

                    Map.entry(CommandSelector.FOUR, new CP4()),

                    Map.entry(CommandSelector.SIX, new CP6()),
                    
                    Map.entry(CommandSelector.IDLE, new MoveArm(new Translation2d(0.33,0.24), 0.5))
                    
                    ),
                     
                CP_Menu::selectCmd123
            )

            );
            
    }
}