package frc.robot.commands.auto;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.RobotContainer;

public class loopMoveRobotWaypoint extends SequentialCommandGroup {


    public loopMoveRobotWaypoint(){
        super(
            new MoveRobotWayPoint(),
            new WaitCommand(4),
            new InstantCommand(() -> RobotContainer.m_vision.updateAllPoints())
        );
    }
}