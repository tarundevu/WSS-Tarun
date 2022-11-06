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
    private TrapezoidProfile.State m_goal1;
    private TrapezoidProfile.State m_setpoint1;
    private TrapezoidProfile m_profile1;
    // private final double tgt_pos1;
    // private final double tgt_pos2;
    // private double start_pos1, start_pos2;
    private Translation2d tgt_pos;
    private Translation2d cur_pos;
    private Translation2d start_pos;
    private double tgt_dist, m_dx, m_dy;
    //private double x,y;
    /**
     * @param x - X coordinate
     * @param y - Y coordinate
     * @param maxSpeed - Maximum speed
     */
    public MoveArm(Translation2d pos, double maxSpeed)
    {   
        // x = pos.getX();
        // y = pos.getY();
        // m_constraints = new TrapezoidProfile.Constraints(maxSpeed, maxSpeed);
        
        // double[] tgt_angles = m_arm.getAngle(x, y);
        // tgt_pos1 = tgt_angles[0]; // A
        // tgt_pos2 = tgt_angles[1]; // B
        
        m_constraints = new TrapezoidProfile.Constraints(maxSpeed, maxSpeed);
        tgt_pos = pos;
    }
    
    /**
     * Runs before execute
     */
    @Override
    public void initialize()
    {   
        
 
        // start_pos1 = m_arm.getServoAngle();
        // start_pos2 = m_arm.getServoAngle2();
        
        // m_goal1 = new TrapezoidProfile.State(tgt_pos1, 0);
        // m_goal2 = new TrapezoidProfile.State(tgt_pos2, 0);

        // m_setpoint1 = new TrapezoidProfile.State(start_pos1, 0);
        // m_setpoint2 = new TrapezoidProfile.State(start_pos2, 0);

        start_pos = m_arm.getArmPos();
        tgt_dist = start_pos.getDistance(tgt_pos);
        m_dx = tgt_pos.getX() - start_pos.getX();
        m_dy = tgt_pos.getY() - start_pos.getY();

        m_goal1 = new TrapezoidProfile.State(tgt_dist, 0);
        m_setpoint1 = new TrapezoidProfile.State(0, 0);
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
         
        // m_profile1 = new TrapezoidProfile(m_constraints, m_goal1, m_setpoint1);
        // m_profile2 = new TrapezoidProfile(m_constraints, m_goal2, m_setpoint2);

        // m_setpoint1 = m_profile1.calculate(dT);
        // m_setpoint2 = m_profile2.calculate(dT);

        // //m_arm.setArmPos(m_setpoint1.position, m_setpoint2.position);
        // m_arm.setServoAngle(m_setpoint1.position);
        // m_arm.setServoAngle2(m_setpoint2.position);
        
        
        m_profile1 = new TrapezoidProfile(m_constraints, m_goal1, m_setpoint1);

        m_setpoint1 = m_profile1.calculate(dT);
        cur_pos = start_pos.plus(new Translation2d((m_setpoint1.position*m_dx/tgt_dist),(m_setpoint1.position*m_dy/tgt_dist)));

        m_arm.setArmPos(cur_pos.getX(), cur_pos.getY());
        m_arm.DisplayValue(cur_pos.getX(), cur_pos.getY());
        if (m_profile1.isFinished(dT) || endCondition()) {
            //distance reached End the command
            
            m_arm.setArmPos(tgt_pos.getX(), tgt_pos.getY());
            m_endFlag = true;
        }
    }

    /**
     * Called when the command is told to end or is interrupted
     */
    @Override
    public void end(boolean interrupted)
    {
        // m_arm.setArmPos(cur_pos.getX(), cur_pos.getY());
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
