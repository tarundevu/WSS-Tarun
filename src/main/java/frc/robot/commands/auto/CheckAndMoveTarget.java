package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotContainer;

public class CheckAndMoveTarget extends SequentialCommandGroup{
    public CheckAndMoveTarget(String targetName, double dist){
        super(
            new MovetoPoint(targetName, dist),
            new Rotate2Orientation(targetName, dist)
            
        );
    }
}