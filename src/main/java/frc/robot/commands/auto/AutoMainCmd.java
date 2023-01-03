package frc.robot.commands.auto;

import java.util.Map;

import javax.lang.model.util.ElementScanner6;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SelectCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Globals;
import frc.robot.RobotContainer;
import frc.robot.Astar.Layout;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Sensor;
import frc.robot.subsystems.Vision;
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
    private final static Sensor m_sensor = RobotContainer.m_sensor;
    private final static Vision m_vision = RobotContainer.m_vision;
    private final static Arm m_arm = RobotContainer.m_arm;

	public AutoMainCmd() 
    {
        
        
        super(

        // new CP1()
        //new CP2()
        // new CP3()
        new CP4()
        // Open House Code//            
        // new PickItem(2),

        // new InstantCommand(()-> m_arm.setCameraAngle(280)),
        // new MovetoB(new Pose2d(0.96, 1.5, new Rotation2d(0))),
        // new PlaceDown(),
        // new MoveArm(new Translation2d(0.33,0.24), 0.5),
        // new MovetoB(new Pose2d(0.96, 1.1, new Rotation2d(0))),

        // new PickItem(0),

        // new InstantCommand(()-> m_arm.setCameraAngle(280)),
        // new MovetoB(new Pose2d(0.96, 1.5, new Rotation2d(0))),
        // new PlaceDown(),
        // new MoveArm(new Translation2d(0.33,0.24), 0.5),
        // new MovetoB(new Pose2d(0.96, 1.1, new Rotation2d(0))),  
        
        // new PickItem(1),

        // new InstantCommand(()-> m_arm.setCameraAngle(280)),
        // new MovetoB(new Pose2d(0.96, 1.5, new Rotation2d(0))),
        // new PlaceDown(),
        // new MoveArm(new Translation2d(0.33,0.24), 0.5),
        // new MovetoB(new Pose2d(0.96, 1.1, new Rotation2d(0)))  
            //new LoopCmd(new ProcessSeq(), ()-> (++Globals.loopCnt)> 4) /// loop cmd
            
        
            // // ### code to pull trolley back ###//
            // // new MoveRobotSense(1, 0.5, 0, 0, 0.2, ()->RobotContainer.m_sensor.getIRDistance()<=25),
            // // new Gripper(0,150),
            // // new MoveArm(new Translation2d(0.3,0),0.5),
            // // new Gripper(1, 150),
            // // new MoveArm(new Translation2d(0.2,0),0.5),
            // // new MoveRobot(1, -0.20, 0, 0, 0.25),
            // // new Gripper(0,150),
            // // /////////////////
           // Trolley Code // 
            // new MoveRobot(1, 1, 0, 0, 0.4),
            // new MovetoB(new Pose2d(1.4, 2.66, new Rotation2d(0))),  
            // new MoveRobot(2, -Math.PI/2, 0, 0, 0.4),
            // new Trolley(),
            // //### Grab Trolley ###//
            // new Gripper(0,150),
            // new MoveArm(new Translation2d(0.4,0),0.5),
            // new Gripper(1, 150),
            // new MoveArm(new Translation2d(0.3,0),0.5),

            // new MoveRobot(1, -0.5, 0, 0, 0.4),
            // new MovePivot(0.39, Math.PI/2,0, 0, 0.6),
            // new MovetoB(new Pose2d(1.0, 3.8, new Rotation2d(0))),
            // //### Release Trolley ###//        
            // new MoveArm(new Translation2d(0.4,0),0.5),
            // new Gripper(0,150)
            //new MovetoB(new Pose2d(1.9, 1.1, new Rotation2d(0))) 

            // new LoopCmd(new ProcessSeq(), ()->(++Globals.loopCnt)>4 ), /// loop cmd
            
            
          
           

    
            

            

            
            
              );
            
    }
    @Override
    public void initialize(){
        
        super.initialize();
        RobotContainer.m_omnidrive.initialise();
        RobotContainer.m_arm.initialize();
    }
}
