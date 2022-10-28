package frc.robot.commands.auto;

import java.util.Map;

import javax.lang.model.util.ElementScanner6;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SelectCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Globals;
import frc.robot.RobotContainer;
import frc.robot.Astar.Layout;
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
    double temp;
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
    

	public AutoMainCmd() 
    {
        
        
        super(
            // Move to Tray //
            new MoveArm(new Translation2d(0.2,0.1),0.5),// moves arm up
            
            new MoveRobot(1, 1, 0, 0, 0.4),
            new MovetoB(new Pose2d(1.9, 1.1, new Rotation2d(0)))
            // new MoveRobot(1, 2, 0, 0, 0.4)
            //new Gripper(1, 150),
            // new MoveRobot(1, 0.65, 0, 0, 0.4), // moves forward
            // new MoveRobotSense(0, -1.5, 0, 0, 0.25, ()->RobotContainer.m_sensor.getCobraTotal()>=4094), //moves left
            // new MoveRobot(1, -0.05, 0, 0, 0.25),
            
            // new DetectObstacle(), //moves forward 
            // new PickUp(),
           
            // new LoopCmd(new ProcessSeq(), ()->(++Globals.loopCnt)>4 ),
            // // //Tray to Base //
            // new MoveRobot(1, -0.5, 0, 0, 0.4),
            // new MoveRobot(0, 0.75, 0, 0, 0.4)

           //
           //new DetectObject()
        //    new WaitCommand(1),
        //    new PickUp(),
        //    new WaitCommand(1),
        //    new PickDown()
        //new CoordinateMotion(new Translation2d(-0.7, 0.7))
           
           //new MoveArm(new Translation2d(0.3, RobotContainer.m_sensor.getYcoord((RobotContainer.m_sensor.getIRDistance2()/100) + 0.075 - 0.2 - 0.1)), 0.4)
            // new DetectObject()
            //new MoveRobotSense(1, 0.5, 0, 0, 0.25, ()->RobotContainer.m_sensor.getIRDistance2()<=10)
            // new PickUp(),
            // new PickDown()
           

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

            
            
              );
            
    }
    @Override
    public void initialize(){
        
        super.initialize();
        RobotContainer.m_omnidrive.initialise();
        //RobotContainer.m_arm.initialize();
    }
}
