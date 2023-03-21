package frc.robot.commands.auto;

import java.util.Map;

import javax.lang.model.util.ElementScanner6;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ProxyScheduleCommand;
import edu.wpi.first.wpilibj2.command.SelectCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Globals;
import frc.robot.RobotContainer;
import frc.robot.Astar.Layout;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.OmniDrive;
import frc.robot.subsystems.Sensor;
import frc.robot.subsystems.Vision;
import frc.robot.commands.auto.MoveRobotSense;
import frc.robot.commands.auto.LoopCmd;




/**
 * DriveMotor class
 * <p>
 * This class creates the inline auto command to drive the motor
 */
public class AutoMainCmd extends SequentialCommandGroup
{   
    int count = 0;
    double temp;
    private final static Sensor m_sensor = RobotContainer.m_sensor;
    private final static Vision m_vision = RobotContainer.m_vision;
    private final static OmniDrive m_omnidrive = RobotContainer.m_omnidrive;
    private final static Arm m_arm = RobotContainer.m_arm;
    private ProxyScheduleCommand m_proxySchedule;

	public AutoMainCmd() 
    {
        
        
        super(
            // new Task_B()
            new Task_A_pick1st()
           
        // ###################################################################################### // 
        //          FOR TESTING, IF ROBOT MOVEMENT HAS ISSUES           //
            //new LoopCmd(new TestMotion(), ()->(++Globals.LoopCnt)>5 ) /// loop cmd
            // new MoveRobot(0,-1.5,0,0,0.4)
                // new TestMotionX(),
                // new TestMotionX(),
                // new TestMotionX(),
                // new TestMotionX(),
                // new TestMotionX()
                // new MoveRobot(0, 0.0875, 0, 0, 0.4),
                // // new WaitCommand(2),
                // new MovetoB(new Pose2d(0.96, 1.6, new Rotation2d(0))),
                // new MovetoB(new Pose2d(0.96, 1.1, new Rotation2d(0))),
                // new MoveRobot(0, 0.0875, 0, 0, 0.4),
                // // new WaitCommand(2),
                // new MovetoB(new Pose2d(0.96, 1.6, new Rotation2d(0))),
                // new MovetoB(new Pose2d(0.96, 1.1, new Rotation2d(0))),
                // new MoveRobot(0, 0.0875, 0, 0, 0.4),
                // // new WaitCommand(2),
                // new MovetoB(new Pose2d(0.96, 1.6, new Rotation2d(0))),
                // new MovetoB(new Pose2d(0.96, 1.1, new Rotation2d(0))),
                // new MoveRobot(0, 0.0875, 0, 0, 0.4),
                // // new WaitCommand(2),
                // new MovetoB(new Pose2d(0.96, 1.6, new Rotation2d(0))),
                // new MovetoB(new Pose2d(0.96, 1.1, new Rotation2d(0))),
                // new MoveRobot(0, 0.0875, 0, 0, 0.4),
                // new WaitCommand(2),
                // new MovetoB(new Pose2d(0.96, 1.6, new Rotation2d(0))),
                // new MovetoB(new Pose2d(0.96, 1.1, new Rotation2d(0)))
              );
            
    }
    
}