package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Globals;
import frc.robot.RobotContainer;

public class TrolleyAlignment extends MoveRobot{

    private int m_type;
    private double convertPxToM = 0.000875;//0.0008625;
    public TrolleyAlignment(int type){
        super(type,0,0,0,0.25);
        m_type = type;
    }
    @Override
    public void initialize(){
        if( (RobotContainer.m_vision.getLine()[0] > 0) || (RobotContainer.m_vision.getLine()[1] > 0) ){
            if (m_type == 0){
                super.m_dist = ((RobotContainer.m_vision.getLine()[0] - Globals.imW/2 ) * convertPxToM)-Globals.camera_mount_offset_x;
            }
            // else if (m_type == 1){
            //     // super.m_dist = ((450 - RobotContainer.m_vision.getLine()[1] ) * convertPxToM);
            //     double dist = 0;
            //     dist = RobotContainer.m_sensor.getIRDistance()/100;
            //     super.m_dist = dist;
            // }

        }
        else{
            super.m_dist = 0;
        }
        
        
        super.initialize();
    }
    @Override
    public boolean endCondition(){
        if (m_type == 0){
            if (Math.abs(RobotContainer.m_vision.getLine()[0] - Globals.imW/2 ) <3) {
                return true;
            }
        }
        // else if (m_type == 1){
        //     if (Math.abs(RobotContainer.m_vision.getLine()[1] - 450) <3) {
        //         return true;
        //     }
        // }
        return false;
    }
}