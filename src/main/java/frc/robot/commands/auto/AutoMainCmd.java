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

	public AutoMainCmd() 
    {
        
        
        super(
        
        // new MovetoB(Layout.Convert_mm_Pose2d(Layout.PickUpBinPos)),
        // new Align2Line(),
        
        // new InstantCommand(()->m_omnidrive.UpdatePosition(Layout.Convert_mm_Pose2d(Layout.PickUpBinPos))),
        // new WaitCommand(3)
        // new Task_A()
        // new GotoColor(Layout.Convert_mm_Pose2d(Layout.GreenPos))
        
        // new Rotate2Orientation(Layout.Convert_mm_Pose2d(Layout.PickUpBinPos).getRotation().getDegrees())
        // // new MoveArm(new Translation2d(0.33,0.24), 0.5) // Line detection position
        // new MoveRobot(1, -0.1, 0, 0, 0.3)
        //new GotoTrolley(0.15, 4.35)
        //new RotatetoDir(RobotContainer.m_vision.Rotate2Obj(0.15,4.35))//working

            new ViewItem(),
            new LoopCommands(new ProcessSeq())
            
        //new CP3()
        // new CP2()
        // new CP3()
        // new CP4()
        // new CP6()
        
            //new LoopCmd(new TestMotion(), ()->(++Globals.LoopCnt)>5 ) /// loop cmd
                // new TestMotion(),
                // new TestMotion(),
                // new TestMotion(),
                // new TestMotion(),
                // new TestMotion()
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