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

public class ArmPickX extends MoveArm {
    private final static Vision m_vision = RobotContainer.m_vision;
    private final static Arm m_arm = RobotContainer.m_arm;
    private final static Sensor m_sensor = RobotContainer.m_sensor;

    private double pickUpHeight = 0.03;
    private int m_type;
    /**
     * This command extends the arm towards the object to be picked up
     */
    public ArmPickX(int type){
        super(new Translation2d(0,0), 0.2);
        m_type = type;
    }
     /**
     * Runs before execute
     */
    @Override
    public void initialize() {
        // xgoal = 0.335 - (getItemY(Globals.curItem) - 120) * Globals.convertPxToMM + 0.012;
        Translation2d pos;
        if (m_type==0) {
            double x = m_arm.getArmPos().getX() + Globals.camera_offset - (Globals.curItemY - m_vision.getResolution(1)/2) * Globals.convertPxToM;
            pos = new Translation2d(x, m_arm.getArmPos().getY());
        }
        else if (m_type==1){
            if (Globals.curItemType==0){// for cokeU
                double y = (pickUpHeight + 0.04) - Globals.arm_offset_z+ Globals.gripper_offset;
                pos = new Translation2d(m_arm.getArmPos().getX(), y);   
            }
            else{
                double y = (pickUpHeight) - Globals.arm_offset_z+ Globals.gripper_offset;
                pos = new Translation2d(m_arm.getArmPos().getX(), y);
            }
        }
        else { 
            if (Globals.curItemType==0){// for cokeU
                double y = (pickUpHeight + 0.1) - Globals.arm_offset_z+ Globals.gripper_offset;
                pos = new Translation2d(m_arm.getArmPos().getX(), y);   
            }
            else{
                double y = (pickUpHeight + 0.05) - Globals.arm_offset_z+ Globals.gripper_offset;
                pos = new Translation2d(m_arm.getArmPos().getX(), y);
            }
        }
            
        super.tgt_pos = pos;
        super.initialize();
    }
}