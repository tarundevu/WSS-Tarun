package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class TestMotion extends SequentialCommandGroup {
    public TestMotion(){
        super(
            //new MoveRobot(0,-2,0,0,0.4),
            new MoveRobot(2,Math.PI,0,0,Math.PI/3),
            //new WaitCommand(1),
            //new MoveRobot(0,2,0,0,0.4),
            //new MoveRobot(2,-Math.PI,0,0,Math.PI/3),
            new WaitCommand(1)
        );
    }
    
}