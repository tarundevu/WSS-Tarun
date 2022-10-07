package frc.robot.commands.auto;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile;
//WPI imports
import edu.wpi.first.wpilibj2.command.CommandBase;

//RobotContainer import
import frc.robot.RobotContainer;


//Subsystem imports
//import frc.robot.subsystems.OmniDrive;
import frc.robot.subsystems.Arm;

public class MoveArm extends CommandBase{
    private final static Arm m_arm = RobotContainer.m_arm;
    private double dT = 0.02;
    private boolean m_endFlag = false;
    private TrapezoidProfile.Constraints m_constraints;
    private TrapezoidProfile.State m_goal1, m_goal2;
    private TrapezoidProfile.State m_setpoint1, m_setpoint2;
    private TrapezoidProfile m_profile1, m_profile2;
    private double tgt_pos1;
    private double tgt_pos2;
    private double x;
    private double y;
    //private double x,y;
    /**
     * @param x - X coordinate
     * @param y - Y coordinate
     * @param maxSpeed - Maximum speed
     */
    public MoveArm(Translation2d pos, double maxSpeed)
    {
        m_constraints = new TrapezoidProfile.Constraints(maxSpeed, maxSpeed);
        x = pos.getX();
        y = pos.getY();
  
    }
    
    /**
     * Runs before execute
     */
    @Override
    public void initialize()
    {   
        double[] tgt_angles = m_arm.getAngle(x, y);
        tgt_pos1 = tgt_angles[0]; // A
        tgt_pos2 = tgt_angles[1]; // B

        double start_pos1 = m_arm.getServoAngle();
        double start_pos2 = m_arm.getServoAngle2();
        
        m_goal1 = new TrapezoidProfile.State(tgt_pos1, 0);
        m_goal2 = new TrapezoidProfile.State(tgt_pos2, 0);

        m_setpoint1 = new TrapezoidProfile.State(start_pos1, 0);
        m_setpoint2 = new TrapezoidProfile.State(start_pos2, 0);

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
        
        
        
        m_profile1 = new TrapezoidProfile(m_constraints, m_goal1, m_setpoint1);
        m_profile2 = new TrapezoidProfile(m_constraints, m_goal2, m_setpoint2);

        m_setpoint1 = m_profile1.calculate(dT);
        m_setpoint2 = m_profile2.calculate(dT);

        //m_arm.setArmPos(m_setpoint1.position, m_setpoint2.position);
        m_arm.setServoAngle(m_setpoint1.position);
        m_arm.setServoAngle2(m_setpoint2.position);

        if ((m_profile1.isFinished(dT)) && (m_profile2.isFinished(dT))) {
            //distance reached End the command
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
