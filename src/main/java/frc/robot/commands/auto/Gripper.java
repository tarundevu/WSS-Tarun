package frc.robot.commands.auto;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile;
//WPI imports
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Globals;
//RobotContainer import
import frc.robot.RobotContainer;

//Subsystem imports
//import frc.robot.subsystems.OmniDrive;
import frc.robot.subsystems.Arm;

public class Gripper extends CommandBase{
    private final static Arm m_arm = RobotContainer.m_arm;
    private double dT = 0.02;
    private boolean m_endFlag = false;
    private TrapezoidProfile.Constraints m_constraints;
    private TrapezoidProfile.State m_goal;
    private TrapezoidProfile.State m_setpoint;
    private TrapezoidProfile m_profile;
    private double maxSpeed = 60;
    private int isOpen;
    private double targetAngle;
    private int[][] itemGripperSizes = {
        {210,100}, // CokeUp
        {210,0}, // Coke
        {210,0}, // Dettol
        {220,0} // Jagabee
    };
 
    /** This class is used to control an end effector
     * 
     * @param pos - 1 for open or 0 for close
     * @param maxSpeed - Maximum speed
     */
    public Gripper(int pos, double maxSpeed)
    {   
        isOpen = pos;
        m_constraints = new TrapezoidProfile.Constraints(maxSpeed, maxSpeed);
    }
    /** This class is used to control an end effector
     * 
     * @param pos - 1 for open or 0 for close
     */
    public Gripper(int pos)
    {   
        isOpen = pos;
        m_constraints = new TrapezoidProfile.Constraints(maxSpeed, maxSpeed);
    }
    /**
     * Runs before execute
     */
    @Override
    public void initialize()
    {   
        targetAngle = itemGripperSizes[Globals.curItemType][isOpen];
        double start_pos = m_arm.getGripper();
        
        m_goal = new TrapezoidProfile.State(targetAngle, 0);
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
        
        m_arm.setGripper(m_setpoint.position);
        if (m_profile.isFinished(dT)) {
            //distance reached End the command
            m_arm.setGripper(targetAngle);
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