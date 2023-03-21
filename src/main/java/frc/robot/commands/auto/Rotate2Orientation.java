package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.CommandBase;

import java.util.Map;
import java.util.function.Supplier;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Transform2d;
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
    private boolean updateLoopPoint = false;
    private double m_dist;
    private String m_target;
    private Supplier<Pose2d> pose2dSupplier;
    private Supplier<String> targetSupplier;
    /**
     * This command is used to align the robot to the object that is to be picked
     * @param angle - The orientation to rotate to
     */
    public Rotate2Orientation(double angle){
        super(2, 0, 0, 0, Math.PI/3);
        updateMap = false;
        updateLoopPoint = false;
        s_angle = angle;
    }


    /**
     * This command is used to align the robot to the object that is to be picked
     * @param pose - The pose of the target coordinates
     */
    public Rotate2Orientation(Pose2d pose){
        super(2, 0, 0, 0, Math.PI/3);
        s_angle = pose.getRotation().getDegrees();
        updateMap = false;
        updateLoopPoint = false;
    }
    public Rotate2Orientation(Supplier<Pose2d> pose){
        super(2, 0, 0, 0, Math.PI/3);
        pose2dSupplier = pose;
        updateMap = false;
        updateLoopPoint = false;
    }
    /**
     * This command is used to align the robot to the target on point map given a distance
     * @param target - The name of the target coordinates
     * @param dist - The distance away from the target coordinates
     */
    public Rotate2Orientation(String target, double dist){
        super(2, 0, 0, 0, Math.PI/3);
        updateMap = true;
        updateLoopPoint = false;
        m_target = target;
        m_dist = dist;
    }
    public Rotate2Orientation(Supplier<String> target, double dist){
        super(2, 0, 0, 0, Math.PI/3);
        updateMap = true;
        updateLoopPoint = false;
        targetSupplier = target;
        m_dist = dist;
    }
    
    /**
     * This command is used to align the robot during the mapping phase of task b
     */
    public Rotate2Orientation(){
        // Used to loop MovetoB commands using the Globals pose2dmovecommands variable
        super(2, 0, 0, 0, Math.PI/3);
        updateMap = false;
        updateLoopPoint = true;
    }
    
     /**
     * Runs before execute
     */
    @Override
    public void initialize()
    {   
        if(updateMap){
            if(targetSupplier != null){
                m_target = targetSupplier.get();
            }
            s_angle = RobotContainer.m_Grid.findGotoPos(RobotContainer.m_points.pointMap.get(m_target).getTranslation(), m_dist).getRotation().getDegrees();
        }
        else if(updateLoopPoint) {
            s_angle = Globals.pose2dMoveCommands[Globals.loopCount%4].getRotation().getDegrees();
        }
        if(pose2dSupplier != null){
            s_angle = pose2dSupplier.get().getRotation().getDegrees();
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