package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.CommandBase;
//RobotContainer import
import frc.robot.RobotContainer;

//Subsystem imports
import frc.robot.subsystems.OmniDrive;
import frc.robot.subsystems.Vision;

public class AlignRobot extends CommandBase{
    //Grab the subsystem instance from RobotContainer
    private final static OmniDrive m_drive = RobotContainer.m_omnidrive;
    private final static Vision m_vision = RobotContainer.m_vision;
    public double targetW, targetX, targetY;
    private double speedX, speedY, speedW;
    public double centerX;
    public double centerY; 
    public double distance;
    private boolean m_endFlag = false;
    /**
     * This command aligns the robot to desired black objects (T Junction or Trolley)
     * <p>
     * 
     * @param x - target X position
     * @param y - target Y position
     */
    

    public AlignRobot(double x, double y){
        centerX = x;
        centerY = y;
        targetW = -m_vision.getLine(2);
        targetX = (m_vision.getLine(0) - centerX);
        targetY = -(m_vision.getLine(1) - centerY);
    }
    public AlignRobot(){
        centerX = 155.0;
        centerY = 200.0;
        targetW = -m_vision.getLine(2);
        targetX = (m_vision.getLine(0) - centerX);
        targetY = -(m_vision.getLine(1) - centerY);
    }
    /**
     * Runs before execute
     */
    @Override
    public void initialize()
    {   
        targetW = -m_vision.getLine(2);
        targetX = (m_vision.getLine(0) - centerX);
        targetY = -(m_vision.getLine(1) - centerY);
    }
    /**
     * Condition to end speed profile
     * Used by derived class to terminate the profile early
     */
    public boolean endCondition()
    {
        return false;
    }
    /**
     * Called continously until command is ended
     */
    @Override
    public void execute()
    {
        targetW = -m_vision.getLine(2);
        targetX = (m_vision.getLine(0) - centerX);
        targetY = -(m_vision.getLine(1) - centerY);
        speedX = 0.002 * targetX;
        speedY = 0.002 * targetY;
        speedW = 0.5 * targetW;
        m_drive.setRobotSpeedType(0, speedX);
        m_drive.setRobotSpeedType(1, speedY); // Y is working well
        m_drive.setRobotSpeedType(2, speedW);
        
        // if (((m_vision.getLine(0) - centerX)) <1 && -(m_vision.getLine(1) - centerY) < 1 &&-m_vision.getLine(2)<0.05){
        //     m_endFlag = true;
        // }
       
        
        
    }
     /**
     * Called when the command is told to end or is interrupted
     */
    @Override
    public void end(boolean interrupted)
    {

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
