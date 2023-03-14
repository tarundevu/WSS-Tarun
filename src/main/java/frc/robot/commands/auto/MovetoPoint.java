package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import frc.robot.Globals;
import frc.robot.RobotContainer;

public class MovetoPoint extends MovetoB{

    private String m_pointName;
    private double m_dist;
    public MovetoPoint(String pointName, double dist){
        super(RobotContainer.m_points.getPoint(pointName));
        m_pointName = pointName;
        m_dist = dist;
    }
    @Override
    public void initialize() {
        super.m_posB = RobotContainer.m_Grid.findGotoPos(RobotContainer.m_points.getPoint(m_pointName).getTranslation(), m_dist);
        System.out.println("_-_-_-_-_-_-_-_-_-_-_-MovetoPoint-_-_-_-_-_-_-_-_-_-_-_-_");
        System.out.println(super.m_posB.toString());
        super.initialize();
    }
    
}