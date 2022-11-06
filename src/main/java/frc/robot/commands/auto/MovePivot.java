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
    //private int m_profType;
    private TrapezoidProfile.Constraints m_constraints1, m_constraints2;
    private TrapezoidProfile.State m_goalX = new TrapezoidProfile.State();
    private TrapezoidProfile.State m_goalY = new TrapezoidProfile.State();
    private TrapezoidProfile.State m_goalZ = new TrapezoidProfile.State();
    private TrapezoidProfile.State m_setpointX = new TrapezoidProfile.State();
    private TrapezoidProfile.State m_setpointY = new TrapezoidProfile.State();
    private TrapezoidProfile.State m_setpointZ = new TrapezoidProfile.State();
    private int m_dir1,m_dir2,m_dir3;

    private final double _startSpeed;

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
    public MovePivot(double radius, double dist, double startSpeed, double endSpeed, double maxSpeed)
    {
        _startSpeed = startSpeed;
    
            m_constraints2 = new TrapezoidProfile.Constraints(0.2, 2.0*Math.PI);
      
            m_constraints1 = new TrapezoidProfile.Constraints(maxSpeed, 0.5);

        m_setpointX = new TrapezoidProfile.State(0, _startSpeed);
        m_setpointY = new TrapezoidProfile.State(0, _startSpeed);
        m_setpointZ = new TrapezoidProfile.State(0, _startSpeed);

        //Negative distance don't seem to work with the library function????
        //Easier to make distance positive and use m_dir to keep track of negative speed.
        double distx = Math.PI*(2*radius), disty = 0, distz = dist;
        m_dir1 = (distx>0)?1:-1;
        distx *= m_dir1;     
        
        m_dir1 = (disty>0)?1:-1;
        disty *= m_dir2;  

        m_dir3 = (distz>0)?1:-1;
        distz *= m_dir3;  
        
        m_goalX = new TrapezoidProfile.State(distx, endSpeed);
        m_goalY = new TrapezoidProfile.State(disty, endSpeed);
        m_goalZ = new TrapezoidProfile.State(distz, endSpeed);

        //addRequirements(m_drive); // Adds the subsystem to the command
     
    }

    
    
    /**
     * Runs before execute
     */
    @Override
    public void initialize()
    {   
        m_setpointX = new TrapezoidProfile.State(0, _startSpeed);
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
        var profileX = new TrapezoidProfile(m_constraints1, m_goalX, m_setpointX);
        var profileY = new TrapezoidProfile(m_constraints1, m_goalY, m_setpointY);
        var profileZ = new TrapezoidProfile(m_constraints2, m_goalZ, m_setpointZ);
        m_setpointX = profileX.calculate(dT);
        m_setpointY = profileY.calculate(dT);
        m_setpointZ = profileZ.calculate(dT);
        m_drive.setRobotSpeedXYW(1.5,1.5,1);
        //m_drive.setMotorOut012(0, 1, 0);
        m_drive.setRobotSpeedType(0, m_setpointX.velocity*m_dir1);
        m_drive.setRobotSpeedType(2, m_setpointZ.velocity*m_dir3);

        if ((profileX.isFinished(dT) && profileZ.isFinished(dT)) || endCondition()) {
            //distance reached or end condition met. End the command
            //This class should be modified so that the profile can end on other conditions like
            //sensor value etc.
            m_drive.setRobotSpeedType(0, m_setpointX.velocity*m_dir1);
            //m_drive.setRobotSpeedType(1, m_setpointY.velocity*m_dir2);
            m_drive.setRobotSpeedType(2, m_setpointZ.velocity*m_dir3);
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