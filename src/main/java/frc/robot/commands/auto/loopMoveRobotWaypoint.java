package frc.robot.commands.auto;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Globals;
import frc.robot.RobotContainer;

public class loopMoveRobotWaypoint extends SequentialCommandGroup {


    public loopMoveRobotWaypoint(){
        super(
            // Sets the python script to perspective transformation with tensorflow model
            
            new MoveRobotWayPoint(),
            new InstantCommand(() -> Globals.cvMode = 4), 
            new WaitCommand(8),
            // CP5 might be affected 
            new InstantCommand(() -> RobotContainer.m_points.updateAllPoints()),
            new InstantCommand(() -> Globals.cvMode = -1)
            
        );
    }
}