package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import frc.robot.Globals;
import frc.robot.RobotContainer;

public class MovetoPoint extends MovetoB{

    private String m_pointName;
    public MovetoPoint(String pointName){
        super(RobotContainer.m_points.getPoint(pointName));
        m_pointName = pointName;
    }
    @Override
    public void initialize() {
        m_posB = RobotContainer.m_points.getPoint(m_pointName);
        
        super.initialize();
    }
    
}