package frc.robot.commands.auto;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Globals;
import frc.robot.RobotContainer;

public class CheckAndMoveTarget extends SequentialCommandGroup{
    public CheckAndMoveTarget(String targetName, double dist){
        super(
            new MovetoPoint(targetName, dist),
            new InstantCommand(()-> System.out.println("After MovetoPoint")),
            new Rotate2Orientation(targetName, dist),
            new InstantCommand(()-> System.out.println("After Rotate2Orientation"))
        );
    }
    public CheckAndMoveTarget(Supplier<String> targetName, double dist){
        super(
            new MovetoPoint(targetName, dist),
            new InstantCommand(()-> System.out.println("After MovetoPoint")),
            new Rotate2Orientation(targetName, dist),
            new InstantCommand(()-> System.out.println("After Rotate2Orientation"))
        );
    }
}