package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Globals;
import frc.robot.RobotContainer;
import frc.robot.Astar.Layout;

public class Task_A_trolley1st extends SequentialCommandGroup{
    /*
     * Sequence for Competition Task A
     */
    public Task_A_trolley1st(){
        super(
            // Start Orientation Correction
            // new StartOrientation(),
            new InstantCommand(()->RobotContainer.m_points.SetTrolleysAsObstacles()),
            //## Calibrate Robot Position ##//
            new InitialCalibration(),
            //## Read WOB ##// 
            new MovetoB(Layout.workOrderPos),
            new ReadWOB(),
            //## Transport Trolleys ##//
            new SortTrolleys(),
            new WaitCommand(2),
            //## Sort Items ##//
            //## pick up bin 1 ##//
            new MovetoB(Layout.PickUpBinPos),
            new Rotate2Orientation(Layout.PickUpBinPos.getRotation().getDegrees()),
            new Align2Line(),
            new ViewItem(),
            new LoopCmd(new SortItems(), ()->Globals.WOBLoopCondition()),
            // new InstantCommand(()->Globals.curBin = 1), // Change to second bin
            new MoveArm(new Translation2d(0.33,0.24), 0.5) // Line detection position
   
        );
        
    }
}