package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile;
import frc.robot.Globals;
//RobotContainer import
import frc.robot.RobotContainer;

//Subsystem imports
import frc.robot.subsystems.OmniDrive;
import frc.robot.subsystems.Vision;

public class Rotate2Orientation extends MoveRobot {
    //Grab the subsystem instance from RobotContainer
    private final static OmniDrive m_drive = RobotContainer.m_omnidrive;
    private double m_angle = 0;
    private double s_angle = 0;
    /**
     * This command is used to align the robot to the object that is to be picked
     */
    public Rotate2Orientation(double angle){
        super(2, 0, 0, 0, 0.3);
        s_angle = angle;
    }
     /**
     * Runs before execute
     */
    @Override
    public void initialize()
    {   
        m_angle = s_angle;
        m_angle = m_angle - Globals.curDir;
        if (m_angle>180)
            m_angle  = m_angle - 360;
        else if (m_angle<-180)
            m_angle = m_angle + 360;
        else 
            m_angle = m_angle + 0;
        m_angle = m_angle * (Math.PI/180);
        //Globals.curAngle = m_angle;
        super.m_dist = m_angle;
        super.initialize();
    }
}