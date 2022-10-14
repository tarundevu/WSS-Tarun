package frc.robot.commands.auto;

import java.util.Map;

import javax.lang.model.util.ElementScanner6;

import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SelectCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Globals;
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
    int count = 0;
    private enum CommandSelector {
        ONE, TWO, THREE
    }

    static public CommandSelector selectCmd123() {
        if (RobotContainer.m_sensor.getIRDistance()<=20)
            return CommandSelector.ONE;
        else if (RobotContainer.m_sensor.getIRDistance()>20){
            return CommandSelector.TWO;
        }
        else 
            return null;
        
    }
    
    public int incCnt() {
        return ++count;
    }
	public AutoMainCmd() 
    {
        
        
        super(
            // Move to Tray //
            new MoveArm(new Translation2d(0.2,0.2),0.5), // moves arm up
            new MoveRobot(1, 0.65, 0, 0, 0.4), // moves forward
            new MoveRobotSense(0, -1.5, 0, 0, 0.25, ()->RobotContainer.m_sensor.getCobraTotal()>=4094), //moves left
            new MoveRobot(1, -0.05, 0, 0, 0.25),
            
            new DetectObstacle(), //moves forward 
            
            new Pick(),
            // new LoopCmd(new Pick(), ()->RobotContainer.m_sensor.getCount()>=2 )
            new LoopCmd(new ProcessSeq(), ()->(++Globals.loopCnt)>4 ),
            // //Tray to Base //
            new MoveRobot(1, -0.5, 0, 0, 0.4),
            new MoveRobot(0, 0.75, 0, 0, 0.4)
            

        //##################################//
            //Round 1 //
            // Tray to Box 1 //
            // new MoveRobot(0, -2, 0, 0, 0.4), // moves left
            // new MoveRobot(1, 0.5, 0, 0, 0.4), // moves forward
            // new MoveRobotSense(0, -0.5, 0, 0, 0.25, ()->RobotContainer.m_sensor.getCobraTotal()>=4094), //moves left
            // new MoveRobotSense(1, 0.5, 0, 0, 0.25, ()->RobotContainer.m_sensor.getIRDistance()<=10), // moves forward
            // new Pick(),

            // // Box 1 to Tray //
            // new MoveRobot(1, -0.65, 0, 0, 0.4),
            // new MoveRobot(0,1.5,0,0,0.4),
            // new MoveRobotSense(0, 0.7, 0, 0, 0.25, ()->RobotContainer.m_sensor.getCobraTotal()>=4094), //moves right
            // new MoveRobotSense(1, 0.5, 0, 0, 0.25, ()->RobotContainer.m_sensor.getIRDistance()<=10), //moves forward
            // new Pick(),

            // // Tray to Box 2 //
            // new MoveRobot(1, -0.5, 0, 0, 0.4),
            // new MoveRobot(2, Math.PI, 0, 0, Math.PI),
            // new MoveRobot(0, 1.5, 0, 0, 0.4),
            // new MoveRobotSense(0, 0.7, 0, 0, 0.25, ()->RobotContainer.m_sensor.getCobraTotal()>=4094),
            // new MoveRobotSense(1, 0.5, 0, 0, 0.25, ()->RobotContainer.m_sensor.getIRDistance()<=15),
            // new Pick(),

            // // Box 2 to Tray //
            // new MoveRobot(0, -1.5, 0, 0, 0.4),
            // new MoveRobot(2,Math.PI,0,0,Math.PI),
            // new MoveRobot(1,0.35,0,0,0.4),
            // new MoveRobotSense(0, 1, 0, 0, 0.25, ()->RobotContainer.m_sensor.getCobraTotal()>=4094), //moves right
            // new MoveRobotSense(1, 0.5, 0, 0, 0.25, ()->RobotContainer.m_sensor.getIRDistance()<=10), //moves forward
            // new Pick(),

            // //round 2 //
            // // Tray to Box 1 //
            // new MoveRobot(0, -2, 0, 0, 0.4), // moves left
            // new MoveRobot(1, 0.5, 0, 0, 0.4), // moves forward
            // new MoveRobotSense(0, -0.5, 0, 0, 0.25, ()->RobotContainer.m_sensor.getCobraTotal()>=4094), //moves left
            // new MoveRobotSense(1, 0.5, 0, 0, 0.25, ()->RobotContainer.m_sensor.getIRDistance()<=10), // moves forward
            // new Pick(),

            // // Box 1 to Tray //
            // new MoveRobot(1, -0.65, 0, 0, 0.4),
            // new MoveRobot(0,1.5,0,0,0.4),
            // new MoveRobotSense(0, 0.7, 0, 0, 0.25, ()->RobotContainer.m_sensor.getCobraTotal()>=4094), //moves right
            // new MoveRobotSense(1, 0.5, 0, 0, 0.25, ()->RobotContainer.m_sensor.getIRDistance()<=10), //moves forward
            // new Pick(),

            // // Tray to Box 2 //
            // new MoveRobot(1, -0.3, 0, 0, 0.4),
            // new MoveRobot(2, Math.PI, 0, 0, Math.PI),
            // new MoveRobot(0, 1.5, 0, 0, 0.4),
            // new MoveRobotSense(0, 0.7, 0, 0, 0.25, ()->RobotContainer.m_sensor.getCobraTotal()>=4094),
            // new MoveRobotSense(1, 0.5, 0, 0, 0.25, ()->RobotContainer.m_sensor.getIRDistance()<=10),
            // new Pick(),

            // // Box 2 to Tray //
            // new MoveRobot(0, -1.5, 0, 0, 0.4),
            // new MoveRobot(2,Math.PI,0,0,Math.PI),
            // new MoveRobot(1,0.35,0,0,0.4),
            // new MoveRobotSense(0, 1, 0, 0, 0.25, ()->RobotContainer.m_sensor.getCobraTotal()>=4094), //moves right
            // new MoveRobotSense(1, 0.5, 0, 0, 0.25, ()->RobotContainer.m_sensor.getIRDistance()<=10), //moves forward
            // new Pick(),
            // //END //
            
            




            //new LoopCmd(new Pick(), ()->RobotContainer.m_sensor.getCount()>=2 )
            //new MoveRobot(1, 0.7, 0, 0, 0.4)
            // new MoveRobot(0, -2.15, 0, 0, 0.4),
            // new MoveRobot(1, 0.6, 0, 0, 0.4),
            // new MoveRobot(1, -1.2, 0, 0, 0.4),
            // new MoveRobot(0, 2.9, 0, 0, 0.4)
            //new DetectObstacle()
            //selectCmd123_B() // Didn't work

            //Select one of two commands
            //new ConditionalCommand( new MoveLeft(), new MoveRight(),  MoveTest::selectCmdA ),
            
            //Select one of many commands
            // Selection command in selectCmd123
            // new SelectCommand(
            //     Map.ofEntries(
            //         Map.entry(CommandSelector.ONE, new MoveRobot(2,-(Math.PI/2), 0, 0, Math.PI)),
            //         Map.entry(CommandSelector.TWO, new MoveRobot(2, (Math.PI/2), 0, 0, Math.PI)),
            //         Map.entry(CommandSelector.THREE, new MoveRobot(2, Math.PI/2, 0, 0, Math.PI)) ),
            //     AutoMainCmd::selectCmd123
            // )
            // new MoveRobot(2, Math.PI, 0, 0, Math.PI),
            //new MoveRobot(1, -0.5, 0, 0, 0.4),

            //new MoveRobot(1, 0.5, 0, 0, 0.4)
            // new MoveRobot(2, (Math.PI), 0, 0, Math.PI),
            // //new MoveRobot(1, -0.5, 0, 0, 0.4),
            // new MoveRobot(1, 0.5, 0, 0, 0.4),
            // new MoveRobot(2, -(Math.PI), 0, 0, Math.PI)
            //new MoveRobot(0, -0.5, 0, 0, 0.4)
            // new MoveServo(0, 20),
            // new MoveServo(90, 20)
           
            // new MoveArm(new Translation2d(0.2,0),0.5),
            // new MoveArm(new Translation2d(0.2,0.2),0.5),
            // new MoveArm(new Translation2d(0.4,0.2), 0.5),
            // new MoveArm(new Translation2d(0.4,0), 0.5),
            // new MoveArm(new Translation2d(0.2,0),0.5)
            
              );
            
    }
    @Override
    public void initialize(){
        //RobotContainer.m_arm.initialize();
        super.initialize();
    }
}
