package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class TestMotion extends SequentialCommandGroup {
    public TestMotion(){
        super(
            new MoveRobot(0,0.5,0,0,0.4),
            new WaitCommand(1),
            new MoveRobot(0,-0.5,0,0,0.4),
            new WaitCommand(1)
        );
    }
    
}