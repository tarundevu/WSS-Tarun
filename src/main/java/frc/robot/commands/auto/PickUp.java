package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.RobotContainer;
// import the commands
import edu.wpi.first.wpilibj.geometry.Translation2d;

//WPI imports


/**
 * DriveMotor class
 * <p>
 * This class creates the inline auto command to pick up objects
 */
public class PickUp extends SequentialCommandGroup
{
    
    public PickUp()
    {
      
      super(
        new Gripper(1, 60),
        new ArmPickX(0.2),
        //new MoveGripper(new Translation2d(m_arm.getArmPos().getX() - Globals.arm_offset_y,0.02), maxSpeed),
        new Gripper(0, 60),
        new MoveGripper(new Translation2d(0.4,0.3),0.5)
      
      //new Gripper(1, 100)
      
      );
      
    }
}