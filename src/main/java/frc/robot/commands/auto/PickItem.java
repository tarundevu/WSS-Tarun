package frc.robot.commands.auto;

import java.util.Map;

import javax.lang.model.util.ElementScanner6;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SelectCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Globals;
import frc.robot.RobotContainer;
import frc.robot.Astar.Layout;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Sensor;
import frc.robot.subsystems.Vision;
import frc.robot.commands.auto.MoveRobotSense;
import frc.robot.commands.auto.LoopCmd;
import frc.robot.commands.auto.DetectObstacle;



/**
 * DriveMotor class
 * <p>
 * This class creates the inline auto command to drive the motor
 */
public class PickItem extends SequentialCommandGroup
{   
    int m_item = 0;
    double temp;
    private final static Sensor m_sensor = RobotContainer.m_sensor;
    private final static Vision m_vision = RobotContainer.m_vision;
    private final static Arm m_arm = RobotContainer.m_arm;

	public PickItem(int item) 
    {
 
        
        super(
            
        new MoveArm(new Translation2d(0.33,0.24), 0.5), // Line detection position
        // new Gripper(0, 100),
        // new WaitCommand(1),
        // new Gripper(1, 100)
        //new InstantCommand(() -> Globals.useTF = false),
        //new InstantCommand(m_vision::setUseTF),
        //new ArmToMidBin(0.5),
        // object detetction position
        new MoveArm(new Translation2d(0.24,0.1), 0.5),
        new InstantCommand(()-> m_arm.setServoAngle3(265)),
        //new WaitCommand(3),
        new InstantCommand(() -> Globals.useTF = true),
        new InstantCommand(m_vision::setUseTF),
        new InstantCommand(() -> Globals.curItem = item),
        new WaitCommand(4),
        new AlignPicker(0.4),
        new InstantCommand(()-> m_arm.setServoAngle3(240)),
        new PickUp(),

        new MoveArm(new Translation2d(0.33,0.24), 0.5)

           
            
        );
              m_item = item;
    }
    @Override
    public void initialize(){
        
        super.initialize();
        RobotContainer.m_omnidrive.initialise();
        RobotContainer.m_arm.initialize();
    }
}
