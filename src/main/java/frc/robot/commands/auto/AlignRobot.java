package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
//RobotContainer import
import frc.robot.RobotContainer;

//Subsystem imports
import frc.robot.subsystems.OmniDrive;
import frc.robot.subsystems.Vision;

public class AlignRobot extends CommandBase{
    //Grab the subsystem instance from RobotContainer
    private final static OmniDrive m_drive = RobotContainer.m_omnidrive;
    private final static Vision m_vision = RobotContainer.m_vision;
    private double targetW, targetX, targetY;
    private double speedX, speedY, speedW;
    private double x2, y2, w2;
    private double ax, ay, aw;
    private double centerX;
    private double centerY; 
    private int count;
   
    private boolean m_endFlag = false;
    private boolean useW = false;
    
    /**
     * This command aligns the robot to desired black/color objects (T Junction or color)
     * <p>
     * 
     * @param x - target X position
     * @param y - target Y position
     * @param w - true or false for using rotation(true for line, false for color)
     * <p>
     * Line-> x = 195, y = 175 | Color-> x = 195, y = 200 (Default values, to be adjusted)
     */
    public AlignRobot(int x, int y, boolean w){

        centerX = x;//100;
        centerY= y;//100; // wanted to change to 120
        useW = w;
        double[] line = m_vision.getLine();
        targetX = (line[0] - centerX);
        targetY = -(line[1] - centerY);
        targetW = -line[2];
        ax = 0.5*Constants.PID_DT;
        ay = 0.5*Constants.PID_DT;
        aw = 1*Constants.PID_DT;
    }
    /**
     * Runs before execute
     */
    @Override
    public void initialize()
    {   
        count = 0;
        double[] line = m_vision.getLine();
        targetX = (line[0] - centerX);
        targetY = -(line[1] - centerY);
        targetW = -line[2];
        m_endFlag = false;

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
        if(m_vision.getLine()[0] != 0 && m_vision.getLine()[0] != 1 )
        {
            double[] line = m_vision.getLine();
        targetX = (line[0] - centerX);
        targetY = -(line[1] - centerY);
        targetW = -line[2];
        speedX = 0.0015 * targetX;
        speedY = 0.0015 * targetY;
        speedW = useW? targetW: 0;        
        if (speedX>0) 
            if (speedX> (x2+ax)) speedX = x2 + ax;
        else
            if (speedX< (x2-ax)) speedX = x2 - ax;
        if (speedY>0) 
            if (speedY> (y2+ay)) speedY = y2 + ay;
        else
            if (speedY< (y2-ay)) speedY = y2 - ay;
        if (speedW>0) 
            if (speedW> (w2+aw)) speedW = w2 + aw;
        else
            if (speedW< (w2-aw)) speedX = w2 - aw;
        x2 = speedX;
        y2 = speedY;
        w2 = speedW;       
        m_drive.setRobotSpeedType(0, speedX);
        m_drive.setRobotSpeedType(1, speedY); // Y is working well
        m_drive.setRobotSpeedType(2, speedW);
        
        
        if (useW){
            if ( (Math.abs(line[0] - centerX) <2) &&  (Math.abs(line[1] - centerY) < 2) && (Math.abs(line[2]-Math.toRadians(0)) < 0.025) && count>= 5){
                m_endFlag = true;
                m_drive.setRobotSpeedType(0, 0);
                m_drive.setRobotSpeedType(1, 0); 
                m_drive.setRobotSpeedType(2, 0);
            }
        }
        else{
            if (Math.abs(line[0] - centerX) <2 &&  Math.abs(line[1] - centerY) < 2 && count >= 200){
                m_endFlag = true;
                m_drive.setRobotSpeedType(0, 0);
                m_drive.setRobotSpeedType(1, 0); 
                m_drive.setRobotSpeedType(2, 0);
            }
        }
          count ++;
        }
        else{
            m_drive.setRobotSpeedXYW(0, 0, 0);
            m_endFlag = true;
        }
        
        
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