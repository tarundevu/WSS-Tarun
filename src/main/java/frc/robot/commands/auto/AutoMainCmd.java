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




/**
 * DriveMotor class
 * <p>
 * This class creates the inline auto command to drive the motor
 */
public class AutoMainCmd extends SequentialCommandGroup
{   
    int count = 0;
    double temp;
    private final static Sensor m_sensor = RobotContainer.m_sensor;
    private final static Vision m_vision = RobotContainer.m_vision;
    private final static Arm m_arm = RobotContainer.m_arm;

	public AutoMainCmd() 
    {
        
        
        super(
        
        // new MovetoB(new Pose2d(0.96, 1.1, new Rotation2d(0))),
        // new InstantCommand(()-> m_vision.getWOBItems()),
        // new ViewItem(),
        new LoopCmd(new SortItems(), ()->Globals.WOBLoopCondition()),
        // new MoveRobot(1,-0.1, 0, 0, 0.25)
        new InstantCommand(()-> m_arm.setCameraAngle(280)),
        new WaitCommand(2),
        new InstantCommand(()-> Globals.cvMode=2),
        new InstantCommand(m_vision::setcvMode)
        //new AlignRobot()
            // new ViewItem(),
            // new LoopCommands(new WOB())
        //new CP3()
        // new CP2()
        // new CP3()
        // new CP4()
        // new CP6()

            // new LoopCmd(new ProcessSeq(), ()->(++Globals.loopCnt)>4 ), /// loop cmd
                
              );
            
    }
    @Override
    public void initialize(){
        
        super.initialize();
        RobotContainer.m_omnidrive.initialise();
        RobotContainer.m_arm.initialize();
    }
}
