package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Globals;
import frc.robot.Astar.Layout;

public class Task_A extends SequentialCommandGroup{

    public Task_A(){
        super(
            //## Read WOB ##// 
            new MovetoB(Layout.Convert_mm_Pose2d(Layout.workOrderPos)),
            new ReadWOB(),
            //## Transport Trolleys ##//
            //new SortTrolleys(),
            // new WaitCommand(2),
            //## Sort Items ##//
            new MovetoB(Layout.Convert_mm_Pose2d(Layout.PickUpBinPos)),
            new Rotate2Orientation(Layout.Convert_mm_Pose2d(Layout.PickUpBinPos).getRotation().getDegrees()),
            new Align2Line(),
            // // // new WaitCommand(2),
            new ViewItem(),
            new LoopCmd(new SortItems(), ()->Globals.WOBLoopCondition())//,
            // new InstantCommand(()->Globals.curBin = 1), // Change to second bin
            // new MoveArm(new Translation2d(0.33,0.24), 0.5), // Line detection position
            // new MovetoB(Layout.Convert_mm_Pose2d(Layout.PickUpBin2Pos)),
            // new Rotate2Orientation(Layout.Convert_mm_Pose2d(Layout.PickUpBin2Pos).getRotation().getDegrees()),
            // new Align2Line(),
            // // new WaitCommand(2),
            // new ViewItem(),
            // new LoopCmd(new SortItems(), ()->Globals.WOBLoopCondition()),
            // new MoveArm(new Translation2d(0.33,0.24), 0.5) // Line detection position
        );
        
    }
}