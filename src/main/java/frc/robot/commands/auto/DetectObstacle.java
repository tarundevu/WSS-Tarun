package frc.robot.commands.auto;

import java.util.Map;
import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SelectCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
// import the commands
import frc.robot.commands.auto.MoveRobot;
import frc.robot.commands.auto.ReturnSensor;
import frc.robot.commands.auto.ReturnNoObstacle;
import frc.robot.RobotContainer;

/**
 * DriveMotor class
 * <p>
 * This class creates the inline auto command to drive the motor
 */
public class DetectObstacle extends SequentialCommandGroup
{
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

    public DetectObstacle()
    {
        super(
            
                new SelectCommand(
                Map.ofEntries(
                    //Map.entry(CommandSelector.ONE, new ReturnSensor()),
                    //Map.entry(CommandSelector.ONE, new MoveRobot(1,(RobotContainer.m_sensor.getIRDistance()-15)/100,0,0,0.25)),
                    Map.entry(CommandSelector.ONE, new MoveRobotSense(1,0.4,0,0,0.15,()->RobotContainer.m_sensor.getIRDistance()<=10)),
                    Map.entry(CommandSelector.TWO, new MoveRobot(1,-((10-RobotContainer.m_sensor.getIRDistance())/100),0,0,0.15))),
                     
                DetectObstacle::selectCmd123
            )

            );
            
    }
}
