package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
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
      new MoveArm(new Translation2d(0.3,0.1),0.5),
      new Gripper(0, 150),
      //new MoveArm(new Translation2d(0.3,0.1),0.5),
      new WaitCommand(1),
      // new MoveArmSense(new Translation2d(0.3, -0.03), 0.4, ()->RobotContainer.m_sensor.getIRDistance2()<=10),
      new MoveArm(new Translation2d(0.3,-0.07),0.5),
      new Gripper(1,150),
      new MoveArm(new Translation2d(0.2,0.1), 0.5)
      
      //new Gripper(1, 100)
      
      );
      
    }
}