package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Arm;

public class MoveCamera extends CommandBase{
  private final static Arm m_arm = RobotContainer.m_arm;
    private double dT = 0.02;
    private boolean m_endFlag = false;
    private TrapezoidProfile.Constraints m_constraints;
    private TrapezoidProfile.State m_goal;
    private TrapezoidProfile.State m_setpoint;
    private TrapezoidProfile m_profile;
    private double tgt_pos;
    private double start_pos;
    private double maxSpeed = 70;

    /** This class is used to control the angle of the camera
     * 
     * @param pos - angle of camera
     * @param maxSpeed - Maximum speed
     */
    public MoveCamera(int pos)
    {   
        m_constraints = new TrapezoidProfile.Constraints(maxSpeed, maxSpeed);
      
        tgt_pos = pos;
        
    }

    /**
     * Runs before execute
     */
    @Override
    public void initialize()
    {   

        start_pos = m_arm.getCameraAngle();
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

        m_profile = new TrapezoidProfile(m_constraints, m_goal, m_setpoint);

        m_setpoint = m_profile.calculate(dT);

        m_arm.setCameraAngle(m_setpoint.position);
        if ((m_profile.isFinished(dT))) {
            //distance reached End the command

            m_arm.setCameraAngle(tgt_pos);

            m_endFlag = true;
        }
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