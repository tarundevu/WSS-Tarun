package frc.robot.commands.auto;

import javax.swing.TransferHandler.TransferSupport;

import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Globals;
//RobotContainer import
import frc.robot.RobotContainer;
//Subsystem imports
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Sensor;
import frc.robot.subsystems.Vision;

public class ArmPickX extends CommandBase {
    private final static Vision m_vision = RobotContainer.m_vision;
    private final static Arm m_arm = RobotContainer.m_arm;
    private final static Sensor m_sensor = RobotContainer.m_sensor;

    private double dT = 0.02;
    private boolean m_endFlag = false;
    private TrapezoidProfile.Constraints m_constraints;
    private TrapezoidProfile.State m_goal = new TrapezoidProfile.State();
    private TrapezoidProfile.State m_setpoint = new TrapezoidProfile.State();
    private TrapezoidProfile m_profile;
    private double xgoal, ygoal;
    private Translation2d tgt_pos, cur_pos, start_pos;
    
    private double _maxSpeed, tgt_dist, m_dx, m_dy;
    private double pickUpHeight = 0.00;
    public ArmPickX(double maxSpeed){
        _maxSpeed = maxSpeed;
        
        m_constraints = new TrapezoidProfile.Constraints(_maxSpeed, 1);
    }
     /**
     * Runs before execute
     */
    @Override
    public void initialize() {
        // xgoal = 0.335 - (getItemY(Globals.curItem) - 120) * Globals.convertPxToMM + 0.012;
        xgoal = m_arm.getArmPos().getX() + Globals.camera_offset - (getItemY(Globals.curItem) - m_vision.getResolution(1)/2) * Globals.convertPxToM;
        ygoal = pickUpHeight;
        double y = ygoal - Globals.arm_offset_z+ Globals.gripper_offset;
        tgt_pos = new Translation2d(xgoal, y);
        Globals.debug[0] = (int)(y*1000);
        start_pos = m_arm.getArmPos();
        tgt_dist = start_pos.getDistance(tgt_pos);
        m_dx = tgt_pos.getX() - start_pos.getX();
        m_dy = tgt_pos.getY() - start_pos.getY();
        
        m_goal = new TrapezoidProfile.State(tgt_dist, 0);
        m_setpoint = new TrapezoidProfile.State(0,0);
        m_endFlag = false;
    }
    public double getItemY(int item) {
        /*
         * 0 - Dettol
         * 1 - Jagabee
         * 2 - Coke
         */
        //gets item type to pick and returns item coordinate
        double[] itemCo = new double[3];

        itemCo[0] = m_vision.getDettol(1);
        itemCo[1] = m_vision.getJagabee(1);
        itemCo[2] = m_vision.getCoke(1);
        
        return itemCo[item];
    }
    /**
     * Condition to end speed profile
     */
    public boolean endCondition()
    {
        return false;
    }
    @Override
    public void execute()
    {
         
        
        Globals.targetXArmPick = xgoal;
        m_vision.D_targetXArm.setNumber(Globals.targetXArmPick);
        m_profile = new TrapezoidProfile(m_constraints, m_goal, m_setpoint);

        m_setpoint = m_profile.calculate(dT);
        cur_pos = start_pos.plus(new Translation2d((m_setpoint.position*m_dx/tgt_dist),(m_setpoint.position*m_dy/tgt_dist)));

        m_arm.setArmPos(cur_pos.getX(),cur_pos.getY());
        
        if (m_profile.isFinished(dT) || endCondition() || m_sensor.getIRDistance2() <= 5) {
            //distance reached End the command
            
            m_arm.setArmPos(tgt_pos.getX(),tgt_pos.getY());
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