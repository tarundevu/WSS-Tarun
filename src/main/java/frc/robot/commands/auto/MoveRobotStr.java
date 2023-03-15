package frc.robot.commands.auto;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.geometry.Pose2d;
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
public class MoveRobotStr extends CommandBase
{
    //Grab the subsystem instance from RobotContainer
    private final static OmniDrive m_drive = RobotContainer.m_omnidrive;
    private double dT = 0.02;
    private double time=0;
    private boolean m_endFlag = false;
    private TrapezoidProfile.Constraints m_constraints;
    private TrapezoidProfile.State m_goal;
    private TrapezoidProfile.State m_setpoint;
    private TrapezoidProfile m_profile;

    protected double m_startSpeed, m_endSpeed, m_dist, m_dir;
    private Supplier<Pose2d> m_posB;

    /**
     * This command moves the robot a certain distance following a trapezoidal speed profile.
     * <p>
     * 
     * @param type - 0, 1 or 2 for x, y, or w speed
     * @param dist - distance to move (m/s or rad/s)
     * @param startSpeed -  starting speed of robot
     * @param endSpeed - ending speed of robot
     * @param maxSpeed - max speed of robot
     */
    //This move the robot a certain distance following a trapezoidal speed profile.
    public MoveRobotStr(Supplier<Pose2d> posB)
    {
        m_startSpeed = 0;
        m_endSpeed = 0;
        m_constraints = new TrapezoidProfile.Constraints(0.4, 0.4);
        m_posB = posB;
         //addRequirements(m_drive); // Adds the subsystem to the command
     
    }

    /**
     * Runs before execute
     */
    @Override
    public void initialize()
    {   
        Pose2d curPose = RobotContainer.m_omnidrive.getPose();
        double dx, dy;
        dx = m_posB.get().getTranslation().getX() - curPose.getTranslation().getX();
        dy = m_posB.get().getTranslation().getY() - curPose.getTranslation().getY();
        m_dist = Math.sqrt(dx*dx + dy*dy);
        double angle = Math.atan2(dy, dx);

        m_dir = angle - curPose.getRotation().getRadians();

        m_setpoint = new TrapezoidProfile.State(0, m_startSpeed);
        m_goal = new TrapezoidProfile.State(m_dist, m_endSpeed);
        m_profile = new TrapezoidProfile(m_constraints, m_goal, m_setpoint);
        m_endFlag = false;
    }
    /**
     * Condition to end speed profile
     * Used by derived class to terminate the profile early
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
        time += dT;

        m_profile = new TrapezoidProfile(m_constraints, m_goal, m_setpoint);
        m_setpoint = m_profile.calculate(dT);

        double xspeed = m_setpoint.velocity * Math.cos(m_dir);
        double yspeed = m_setpoint.velocity * Math.sin(m_dir);
        m_drive.setRobotSpeedXYW(xspeed, yspeed, 0);

        if ( m_profile.isFinished(dT) || endCondition() ) {
            //distance reached or end condition met. End the command
            //This class should be modified so that the profile can end on other conditions like
            //sensor value etc.
            m_drive.setRobotSpeedXYW(0, 0, 0);
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