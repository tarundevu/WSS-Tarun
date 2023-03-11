package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class TestMotionY extends SequentialCommandGroup {
    
    public TestMotionY(){
        super(
            new MoveRobot(1,0.5,0,0,0.4),
            new WaitCommand(1),
            new MoveRobot(1,-0.5,0,0,0.4),
            new WaitCommand(1)
        );
    }

    
}