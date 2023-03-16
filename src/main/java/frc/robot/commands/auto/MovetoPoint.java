package frc.robot.commands.auto;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import frc.robot.Globals;
import frc.robot.RobotContainer;

public class MovetoPoint extends MovetoB{

    private String m_pointName;
    private double m_dist;
    private Supplier<String> m_pointNameSupplier;
    public MovetoPoint(String pointName, double dist){
        super(RobotContainer.m_points.getPoint(pointName));
        m_pointName = pointName;
        m_dist = dist;
    }
    public MovetoPoint(Supplier<String> pointName, double dist){
        super(RobotContainer.m_points.getPoint(pointName.get()));
        m_pointNameSupplier = pointName;
        m_dist = dist;
    }
    @Override
    public void initialize() {
        if(m_pointNameSupplier!=null){
            m_pointName = m_pointNameSupplier.get();
        }
        super.m_posB = RobotContainer.m_Grid.findGotoPos(RobotContainer.m_points.getPoint(m_pointName).getTranslation(), m_dist);
        System.out.println("_-_-_-_-_-_-_-_-_-_-_-MovetoPoint-_-_-_-_-_-_-_-_-_-_-_-_");
        System.out.println(super.m_posB.toString());
        super.initialize();
    }
    
}