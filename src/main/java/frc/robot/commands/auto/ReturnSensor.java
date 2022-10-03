package frc.robot.commands.auto;

import java.util.Map;

import edu.wpi.first.wpilibj2.command.SelectCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
// import the commands
import frc.robot.commands.auto.MoveRobot;
import frc.robot.RobotContainer;

/**
 * DriveMotor class
 * <p>
 * This class creates the inline auto command to drive the motor
 */
public class ReturnSensor extends SequentialCommandGroup
{
    
    public ReturnSensor()
    {
        super(
            
                    new MoveRobot(2, -Math.PI/2, 0, 0, Math.PI),
                    new MoveRobot(1, 0.5, 0, 0, 0.4),
                    new MoveRobot(2, Math.PI, 0, 0, Math.PI),
                    new MoveRobot(1, 0.5, 0, 0, 0.4),
                    new MoveRobot(2, -Math.PI/2, 0, 0, Math.PI)
                
            
            );
    }
}
