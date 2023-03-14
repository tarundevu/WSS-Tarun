package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.CommandBase;

import java.util.Map;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile;
import frc.robot.Globals;
import frc.robot.Robot;
//RobotContainer import
import frc.robot.RobotContainer;

//Subsystem imports
import frc.robot.subsystems.OmniDrive;
import frc.robot.subsystems.Vision;

public class Rotate2Orientation extends MoveRobot {
    //Grab the subsystem instance from RobotContainer
    private final static OmniDrive m_drive = RobotContainer.m_omnidrive;
    private final static Vision m_vision = RobotContainer.m_vision;
    // private static double convertPxToMM = 0.1/50;
    // private final double _startSpeed;
    private double m_angle =0;
    private double s_angle = 0;
    private boolean updateMap = false;
    private double m_dist;
    private String m_target;
    /**
     * This command is used to align the robot to the object that is to be picked
     * @param angle - The orientation to rotate to
     */
    public Rotate2Orientation(double angle){
        super(2, 0, 0, 0, Math.PI/3);
        
        s_angle = angle;
    }
    /**
     * This command is used to align the robot to the object that is to be picked
     * @param pose - The pose of the target coordinates
     */
    public Rotate2Orientation(Pose2d pose){
        super(2, 0, 0, 0, Math.PI/3);
        s_angle = pose.getRotation().getDegrees();
    }
    public Rotate2Orientation(String target, double dist){
        super(2, 0, 0, 0, Math.PI/3);
        updateMap = true;
        m_target = target;
        m_dist = dist;
    }
    
     /**
     * Runs before execute
     */
    @Override
    public void initialize()
    {   
        if(updateMap){
            s_angle = RobotContainer.m_Grid.findGotoPos(RobotContainer.m_points.pointMap.get(m_target).getTranslation(), m_dist).getRotation().getDegrees();
        }
        m_angle = s_angle;
        m_angle = m_angle - Globals.curDir;
        // Globals.curAngle = m_angle;
        if (m_angle>180)
            m_angle  = m_angle - 360;
        else if (m_angle<-180)
            m_angle = m_angle + 360;
        else 
            m_angle = m_angle + 0;
        m_angle = m_angle * (Math.PI/180);
        System.out.println("Rotate2Orientation: " + m_angle);   
        super.m_dist = m_angle;
        super.initialize();
    }
}