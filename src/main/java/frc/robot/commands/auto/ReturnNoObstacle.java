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
// NOT USED IN COMPETITION
public class ReturnNoObstacle extends SequentialCommandGroup
{
    
    public ReturnNoObstacle()
    {
        super(
            
            
              
              //new MoveRobot(1, 0.5, 0, 0, 0.4),
              new MoveRobot(1, -0.5, 0, 0, 0.4) 
                
            
            );
    }
}
