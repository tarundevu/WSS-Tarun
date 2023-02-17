package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotContainer;

public class CheckAndMoveTarget extends SequentialCommandGroup{
    public CheckAndMoveTarget(String targetName){
        super(
            new Rotate2Orientation(RobotContainer.m_points.getPoint(targetName)), 
            new MovetoPoint(targetName)
        );
    }
}