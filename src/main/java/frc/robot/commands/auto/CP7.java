package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Globals;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Arm;

public class CP7 extends SequentialCommandGroup {
    private final static Arm m_arm = RobotContainer.m_arm;
    public CP7(){

        super(
            // Puts camera in viewing position
            new MoveArm(new Translation2d(0.3,0.4), 2),
            new MoveCamera(286),
            // Put to finding trolley and transform point 
            new InstantCommand(() -> Globals.cvMode = 4),
            // Move out of the way
            new MoveRobot(0, -0.05, 0, 0, 5),
            new MoveRobot(1, 0.25, 0, 0, 5),
            // Loop MoveRobot Commands until target area is found
            new loopMoveRobotWaypoint(),
            new LoopCmd(new loopMoveRobotWaypoint(), () -> Globals.endConditionCP7()),
            new MovetoPoint("Trolley", 0.5)
            );
    }
}