package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class TestMotionRot extends SequentialCommandGroup {
    public TestMotionRot(){
        super(
            new MoveRobot(2,Math.PI,0,0,Math.PI/3),
            new WaitCommand(1)
        );
    }
    
}