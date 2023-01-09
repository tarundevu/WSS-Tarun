package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile;
import frc.robot.Globals;
//RobotContainer import
import frc.robot.RobotContainer;

//Subsystem imports
import frc.robot.subsystems.OmniDrive;
import frc.robot.subsystems.Vision;

public class AlignPicker extends MoveRobot {
    //Grab the subsystem instance from RobotContainer
    private final static OmniDrive m_drive = RobotContainer.m_omnidrive;
    private final static Vision m_vision = RobotContainer.m_vision;

    // private static double convertPxToMM = 0.1/50;
    private double dT = 0.02;
    private double time = 0;
    private double targetXDistance;
    private double targetYDistance;
    private TrapezoidProfile.Constraints m_constraints;

    private TrapezoidProfile.State m_goal_x;
    private TrapezoidProfile.State m_setpoint_x;
    private TrapezoidProfile m_profile_x;

    private TrapezoidProfile.State m_goal_y;
    private TrapezoidProfile.State m_setpoint_y;
    private TrapezoidProfile m_profile_y;
    
    private boolean m_endFlag = false;
    private final double _startSpeed;
    private double camera_offset_pixels = 25;
    public AlignPicker(){
        super(0, 0, 0, 0, 0.4 );
        _startSpeed= 0;  
    }
     /**
     * Runs before execute
     */
    @Override
    public void initialize()
    {   
        super.m_dist = ((Globals.curItemX -400 - camera_offset_pixels) * Globals.convertPxToM);
       
        super.initialize();
    }
}