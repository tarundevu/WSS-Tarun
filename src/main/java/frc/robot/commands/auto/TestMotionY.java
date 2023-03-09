package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ScheduleCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Globals;

public class TestMotionY extends SequentialCommandGroup {
    
    public TestMotionY(){
        super(
            new MoveRobot(1,0.5,0,0,0.4),
            new WaitCommand(1),
            new MoveRobot(1,-0.5,0,0,0.4),
            new WaitCommand(1)
        );
    }
    
    // @Override
    // public void end(){
        
    // }
    
}