package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile;
import frc.robot.Globals;
//RobotContainer import
import frc.robot.RobotContainer;

//Subsystem imports
import frc.robot.subsystems.OmniDrive;
import frc.robot.subsystems.Vision;

public class AlignPicker extends CommandBase {
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
    public AlignPicker(double maxSpeed){
        // targetXDistance = ((m_vision.getDettol(0) -160) * convertPxToMM);
        _startSpeed = 0;        
        m_constraints = new TrapezoidProfile.Constraints(maxSpeed, 0.3);
        // m_goal = new TrapezoidProfile.State(targetXDistance, 0);
        
    }
     /**
     * Runs before execute
     */
    @Override
    public void initialize()
    {   
        targetXDistance = ((getItemX(Globals.curItem) -400) * Globals.convertPxToM);
       
        m_goal_x = new TrapezoidProfile.State(targetXDistance, 0);
        m_setpoint_x = new TrapezoidProfile.State(0, _startSpeed);
        m_profile_x = new TrapezoidProfile(m_constraints, m_goal_x, m_setpoint_x);

        Globals.useTF = true;
        m_vision.setUseTF();

        m_endFlag = false;
    }
    public double getItemX(int item) {
        /*
         * 1 - Dettol
         * 2 - Jagabee
         * 3 - Coke
         */
        //gets item type to pick and returns item coordinate
        double[] itemCo = new double[3];

        itemCo[0] = m_vision.getDettol(0);
        itemCo[1] = m_vision.getJagabee(0);
        itemCo[2] = m_vision.getCoke(0);
        
        return itemCo[item];
    }
    /**
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
        

        m_profile_x = new TrapezoidProfile(m_constraints, m_goal_x, m_setpoint_x);
        m_setpoint_x = m_profile_x.calculate(dT);


        m_drive.setRobotSpeedType(0, m_setpoint_x.velocity);
        // m_drive.setRobotSpeedType(1, m_setpoint_y.velocity);

        if ( m_profile_x.isFinished(dT) || endCondition() ) {
            //distance reached or end condition met. End the command
            //This class should be modified so that the profile can end on other conditions like
            //sensor value etc.
            m_drive.setRobotSpeedType(0, m_goal_x.velocity);
            // m_drive.setRobotSpeedType(1, m_goal_y.velocity);
            Globals.useTF = false;
            m_vision.setUseTF();
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