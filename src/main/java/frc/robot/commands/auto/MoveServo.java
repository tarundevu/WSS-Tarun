package frc.robot.commands.auto;

//import javax.swing.text.Position;

import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile;
//WPI imports
import edu.wpi.first.wpilibj2.command.CommandBase;

//RobotContainer import
import frc.robot.RobotContainer;


//Subsystem imports
//import frc.robot.subsystems.OmniDrive;
import frc.robot.subsystems.Arm;


/**
 * SimpleDrive class
 * <p>
 * This class drives a motor 
 */
// NOT USED IN COMPETITION
public class MoveServo extends CommandBase
{
    //Grab the subsystem instance from RobotContainer
    private final static Arm m_arm = RobotContainer.m_arm;
    private double dT = 0.02;
    private boolean m_endFlag = false;
    //private int m_profType;
    private TrapezoidProfile.Constraints m_constraints;
    private TrapezoidProfile.State m_goal = new TrapezoidProfile.State();
    private TrapezoidProfile.State m_setpoint = new TrapezoidProfile.State();
    private TrapezoidProfile m_profile;
    //private int m_dir;
    private double start_pos;
    //private double time;
    private final double tgt_pos;

    //private final double _startSpeed;

    /**
     * This command moves the robot a certain distance following a trapezoidal speed profile.
     * <p>
     * 
     * @param position - position of the
     * @param maxSpeed - distance to move
     * 
     */
    //This move the robot a certain distance following a trapezoidal speed profile.
    public MoveServo(double position, double maxSpeed)
    {
        
        //double start_pos = m_arm.getServoAngle0();
        tgt_pos = position;
        
        m_constraints = new TrapezoidProfile.Constraints(maxSpeed, maxSpeed);
        //m_setpoint = new TrapezoidProfile.State(0, _startSpeed);
        
        //Negative distance don't seem to work with the library function????
        //Easier to make distance positive and use m_dir to keep track of negative speed.
               
        
        //m_goal = new TrapezoidProfile.State(dist, endSpeed);

        //addRequirements(m_drive); // Adds the subsystem to the command
     
    }

    
    
    /**
     * Runs before execute
     */
    @Override
    public void initialize()
    {   
        start_pos = m_arm.getServoAngle();

        m_goal = new TrapezoidProfile.State(tgt_pos, 0);
        m_setpoint = new TrapezoidProfile.State(start_pos, 0);
        m_endFlag = false;
        
    }
    /**
     * Condition to end speed profile
     */
    public boolean endCondition()
    {
        return false;
    }
    /**
     * Called continously until command is ended
     */
    @Override
    public void execute()
    {
        //time += dT;
        //Create a new profile to calculate the next setpoint(speed) for the profile
        m_profile = new TrapezoidProfile(m_constraints, m_goal, m_setpoint);
        m_setpoint = m_profile.calculate(dT);
        m_arm.setServoAngle(m_setpoint.position);

        if ((m_setpoint.position>=m_goal.position) || endCondition()) {
            //distance reached or end condition met. End the command
            //This class should be modified so that the profile can end on other conditions like
            //sensor value etc.
            m_arm.setServoAngle(m_setpoint.position);
            m_endFlag = true;
        }
        
    }

    

    /**
     * Called when the command is told to end or is interrupted
     */
    @Override
    public void end(boolean interrupted)
    {

    }

    /**
     * Creates an isFinished condition if needed
     */
    @Override
    public boolean isFinished()
    {
        return m_endFlag;
    }

}