package frc.robot.commands.auto;

import java.util.Map;

import javax.lang.model.util.ElementScanner6;

import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SelectCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Sensor;
import frc.robot.commands.auto.MoveRobotSense;
import frc.robot.commands.auto.LoopCmd;
import frc.robot.commands.auto.DetectObstacle;


/**
 * DriveMotor class
 * <p>
 * This class creates the inline auto command to drive the motor
 */
public class AutoMainCmd extends SequentialCommandGroup
{   

    
	public AutoMainCmd() 
    {
        
        super(
            // new MoveRobotSense(1, 1, 0, 0, 0.5, ()->RobotContainer.m_sensor.getIRDistance()<=20)


            //selectCmd123_B() // Didn't work

            //Select one of two commands
            //new ConditionalCommand( new MoveLeft(), new MoveRight(),  MoveTest::selectCmdA ),
            
            //Select one of many commands
            //Selection command in selectCmd123
            // new SelectCommand(
            //     Map.ofEntries(
            //         Map.entry(CommandSelector.ONE, new MoveRobot(2,-(Math.PI/2), 0, 0, Math.PI)),
            //         Map.entry(CommandSelector.TWO, new MoveRobot(2, (Math.PI/2), 0, 0, Math.PI)),
            //         Map.entry(CommandSelector.THREE, new MoveRobot(2, Math.PI/2, 0, 0, Math.PI)) ),
            //     AutoMainCmd::selectCmd123
            // ),
            // new MoveRobot(2, Math.PI, 0, 0, Math.PI),
            //new MoveRobot(1, -0.5, 0, 0, 0.4),
            //new LoopCmd(new DetectObstacle(), ()->RobotContainer.m_sensor.getSwitch()==false),
            // new MoveRobot(1, -0.5, 0, 0, 0.4)
            // new MoveServo(0, 20),
            // new MoveServo(90, 20)
            new MoveArm(new Translation2d(0.2,0), 25)
            // new MoveArm(0.2, 0.2,25),
            // new MoveArm(0.2, 0.1,25),
            // new MoveArm(0.3, 0.1, 25),
            // new MoveArm(0.3, 0.2, 25)
              );
            
    }
    @Override
    public void initialize(){
        RobotContainer.m_arm.initialize();
        super.initialize();
    }
}
