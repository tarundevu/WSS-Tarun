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
        // Moves arm to viewing position
        new ViewingPosition(),
        // sets cvMode to object detection mode
        new InstantCommand(()-> Globals.cvMode=1),  
        new WaitCommand(3),
        // resets cvMode to idle mode
        new InstantCommand(()-> Globals.cvMode=-1)
        // new WaitCommand(2)
      
        );
    }
   
}