package frc.robot.commands.auto;

import javax.lang.model.util.ElementScanner6;
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

public class ArmPick extends MoveArm {
    private final static Vision m_vision = RobotContainer.m_vision;
    private final static Arm m_arm = RobotContainer.m_arm;
    private final static Sensor m_sensor = RobotContainer.m_sensor;
    private double TrolleyconvertPxToM = 0.0008625;
    private double pickUpHeight = 0.02;
    private double ratio = 0;
    private int m_type;
    /**
     * This command extends the arm towards the object to be picked up
     */
    public ArmPick(int type){
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
        if (m_type==0) { // MOves arm in the x direction
            if (Globals.curItemType==0)// for cokeU
                ratio = Globals.CokeRatio;
            else
                ratio = 1;
            double x = m_arm.getArmPos().getX() + Globals.camera_offset - (Globals.curItemY - m_vision.getResolution(1)/2) * Globals.convertPxToM*ratio;
            pos = new Translation2d(x, m_arm.getArmPos().getY());
        }   
        else if (m_type==1){ // Moves the arm in the y direction to the picking height
            if (Globals.curItemType==0){// for cokeU
                double y = (pickUpHeight + 0.05) - Globals.arm_offset_z+ Globals.gripper_offset;
                pos = new Translation2d(m_arm.getArmPos().getX(), y);   
            }
            else{
                double y = (pickUpHeight) - Globals.arm_offset_z+ Globals.gripper_offset;
                pos = new Translation2d(m_arm.getArmPos().getX(), y);
            }
        }
        else if (m_type==2){ //  MOves arm in the Y direction but at a height from the object
            if (Globals.curItemType==0){// for cokeU
                double y = (pickUpHeight + 0.12) - Globals.arm_offset_z+ Globals.gripper_offset;
                pos = new Translation2d(m_arm.getArmPos().getX(), y);   
            }
            else{
                double y = (pickUpHeight + 0.07) - Globals.arm_offset_z+ Globals.gripper_offset;
                pos = new Translation2d(m_arm.getArmPos().getX(), y);
            }
        }
        else{ // To place item in Trolley
            double x  = m_arm.getArmPos().getX() + ((400 - RobotContainer.m_vision.getLine()[1] ) * TrolleyconvertPxToM)-0.05;
            pos = new Translation2d(x, 0.12);
            
        }
      
          
        
            
        super.tgt_pos = pos;
        super.initialize();
    }
}