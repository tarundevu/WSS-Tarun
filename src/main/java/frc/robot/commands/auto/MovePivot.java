package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile;
//WPI imports
import edu.wpi.first.wpilibj2.command.CommandBase;

//RobotContainer import
import frc.robot.RobotContainer;


//Subsystem imports
import frc.robot.subsystems.OmniDrive;

/**
 * SimpleDrive class
 * <p>
 * This class drives a motor 
 */
public class MovePivot extends CommandBase
{
    //Grab the subsystem instance from RobotContainer
    private final static OmniDrive m_drive = RobotContainer.m_omnidrive;
    private double dT = 0.02;
    private boolean m_endFlag = false;
    private double Radius;
    //private int m_profType;
    private TrapezoidProfile.Constraints m_constraints;
    private TrapezoidProfile.State m_goalZ = new TrapezoidProfile.State();  
    private TrapezoidProfile.State m_setpointZ = new TrapezoidProfile.State();
    private int m_dir;

    private final double _startSpeed;

    /**
     * This command moves the robot around a circle of given radius.
     * <p>
     * 
     * @param radius - radius of circle
     * @param dist - angle of rotation
     * @param startSpeed -  starting speed of robot
     * @param endSpeed - ending speed of robot
     * @param maxSpeed - max speed of robot
     */
    //This move the robot a certain distance following a trapezoidal speed profile.
    public MovePivot(double radius, double dist, double startSpeed, double endSpeed, double maxSpeed)
    {
        _startSpeed = startSpeed;
    
        m_constraints = new TrapezoidProfile.Constraints(maxSpeed, 2.0*Math.PI);
        Radius = radius;
    
        m_setpointZ = new TrapezoidProfile.State(0, _startSpeed);

        //Negative distance don't seem to work with the library function????
        //Easier to make distance positive and use m_dir to keep track of negative speed.
        
        m_dir = (dist>0)?1:-1;
        dist *= m_dir;     
        
        m_goalZ = new TrapezoidProfile.State(dist, endSpeed);

        //addRequirements(m_drive); // Adds the subsystem to the command
     
    }

    
    
    /**
     * Runs before execute
     */
    @Override
    public void initialize()
    {   
        m_setpointZ = new TrapezoidProfile.State(0, _startSpeed);
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
        //Create a new profile to calculate the next setpoint(speed) for the profile
        var profileZ = new TrapezoidProfile(m_constraints, m_goalZ, m_setpointZ);
        m_setpointZ = profileZ.calculate(dT);
        
        m_drive.setRobotSpeedType(0, (m_setpointZ.velocity*m_dir)*Radius);
        m_drive.setRobotSpeedType(2, (m_setpointZ.velocity*m_dir));

        if ((profileZ.isFinished(dT) || endCondition())) {
            //distance reached or end condition met. End the command
            //This class should be modified so that the profile can end on other conditions like
            //sensor value etc.
            m_drive.setRobotSpeedType(0, (m_setpointZ.velocity*m_dir)*Radius);
            m_drive.setRobotSpeedType(2,   (m_setpointZ.velocity*m_dir));
        
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