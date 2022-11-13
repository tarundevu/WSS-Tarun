package frc.robot.commands.auto;

import java.util.Map;
import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.SelectCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
// import the commands
import frc.robot.commands.auto.MoveRobot;
import frc.robot.Robot;
import frc.robot.RobotContainer;

/**
 * DriveMotor class
 * <p>
 * This class creates the inline auto command to drive the motor
 */
public class DetectObject extends SequentialCommandGroup
{
    //private static double y_value = (RobotContainer.m_sensor.getIRDistance2()/100)+0.075-0.2-0.1;
    private static double dist;
    private enum CommandSelector {
        ONE, TWO, THREE
    }

    static public CommandSelector selectCmd123() {
        if (RobotContainer.m_sensor.getIRDistance()>10)
            return CommandSelector.ONE;
        else if (RobotContainer.m_sensor.getIRDistance()<10){
            return CommandSelector.TWO;
        }
        else 
            return null;
        
    }

    public DetectObject()
    {
        super(
            
                new SelectCommand(
                Map.ofEntries(
                 
                    //Map.entry(CommandSelector.ONE, new Gripper(0, 150)),
                    Map.entry(CommandSelector.ONE, new MoveRobot(1,dist, 0, 0, 0.25))),
                    //Map.entry(CommandSelector.TWO, new MoveArmSense(new Translation2d(0.3, -0.03), 0.4, ()->RobotContainer.m_sensor.getIRDistance2()<=30)),
                     
                DetectObject::selectCmd123
            )

            );
            
    }

}
