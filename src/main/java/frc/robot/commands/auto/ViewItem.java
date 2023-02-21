package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Globals;

/**
 * DriveMotor class
 * <p>
 * This class creates the inline auto command to drive the motor
 */
public class ViewItem extends SequentialCommandGroup{   
	public ViewItem() 
    {
 
        super(
        //new MoveArm(new Translation2d(0.33,0.24), 0.5), // Line detection position
        new MoveArm(new Translation2d(0.24,0.1), 0.5),
        new MoveCamera(280),
        new InstantCommand(()-> Globals.cvMode=1),  
        new WaitCommand(3),
        new InstantCommand(()-> Globals.cvMode=-1)
        // new WaitCommand(2)
      
        );
    }
   
}