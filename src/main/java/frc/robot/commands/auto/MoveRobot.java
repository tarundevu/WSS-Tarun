package frc.robot.commands.auto;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile;
//WPI imports
import edu.wpi.first.wpilibj2.command.CommandBase;

//RobotContainer import
import frc.robot.RobotContainer;


//Subsystem imports
import frc.robot.subsystems.OmniDrive;
import frc.robot.subsystems.Arm;


/**
 * SimpleDrive class
 * <p>
 * This class drives a motor 
 */
public class MoveRobot extends CommandBase
{
    //Grab the subsystem instance from RobotContainer
    private final static OmniDrive m_drive = RobotContainer.m_omnidrive;
    private double dT = 0.02;
    private boolean m_endFlag = false;
    protected int m_profType;
    protected TrapezoidProfile.Constraints m_constraints;
    private TrapezoidProfile.State m_goal = new TrapezoidProfile.State();
    private TrapezoidProfile.State m_setpoint = new TrapezoidProfile.State();
    private int m_dir;

    protected double m_startSpeed, m_endSpeed, m_maxSpeed;
    protected double m_dist;
    private Supplier<Rotation2d> m_getRot=null;

    public MoveRobot(Supplier<Rotation2d> getRot, double startSpeed, double endSpeed, double maxSpeed)
    {
        this(2, 0, startSpeed, endSpeed, maxSpeed);
        m_getRot = getRot;
    }
    /**
     * This command moves the robot a certain distance following a trapezoidal speed profile.
     * <p>
     * 
     * @param type - 0, 1 or 2 for x, y, or w speed
     * @param dist - distance to move
     * @param startSpeed -  starting speed of robot
     * @param endSpeed - ending speed og robot
     * @param maxSpeed - max speed of robot
     */
    //This move the robot a certain distance following a trapezoidal speed profile.
    public MoveRobot(int type, double dist, double startSpeed, double endSpeed, double maxSpeed)
    {
        m_startSpeed = startSpeed;
        m_endSpeed = endSpeed;
        m_maxSpeed = maxSpeed;
        m_profType = type;
        m_dist = dist;
        if (type==2){
            m_constraints = new TrapezoidProfile.Constraints(maxSpeed, Math.PI/2);
        }
        else{
            m_constraints = new TrapezoidProfile.Constraints(maxSpeed, 0.5);
        }
        //m_setpoint = new TrapezoidProfile.State(0, m_startSpeed);
        

        //addRequirements(m_drive); // Adds the subsystem to the command
     
    }
    /**
     * Runs before execute
     */
    @Override
    public void initialize()
    {   
        if(m_getRot!=null){
            m_dist = m_getRot.get().getRadians();
        }
        m_setpoint = new TrapezoidProfile.State(0, m_startSpeed);
        m_endFlag = false;
        //Negative distance don't seem to work with the library function????
        //Easier to make distance positive and use m_dir to keep track of negative speed.
        // m_dir = (m_dist>0)?1:-1;
        // m_dist *= m_dir;          
        
        m_goal = new TrapezoidProfile.State(m_dist, m_endSpeed);
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
        //Create a new profile to calculate the next setpoint(speed) for the profile
        var profile = new TrapezoidProfile(m_constraints, m_goal, m_setpoint);
        m_setpoint = profile.calculate(dT);
        m_drive.setRobotSpeedType(m_profType, m_setpoint.velocity);

        if (profile.isFinished(dT) || endCondition()) {
            //distance reached or end condition met. End the command
            //This class should be modified so that the profile can end on other conditions like
            //sensor value etc.
            m_drive.setRobotSpeedType(m_profType, m_goal.velocity);
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