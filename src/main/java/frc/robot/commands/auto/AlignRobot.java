package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
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
    private double centerX;
    private double centerY; 
    private int count;
   
    private boolean m_endFlag = false;
    private boolean useW = false;
    
    /**
     * This command aligns the robot to desired black objects (T Junction or Trolley)
     * <p>
     * 
     * @param x - target X position
     * @param y - target Y position
     */
    public AlignRobot(String object){
        useW = object == "trolley"? false:true;
        centerX = 100;
        centerY = 110;
        // targetW = -m_vision.getLine(2);
        double[] line = m_vision.getLine();
        targetX = (line[0] - centerX);
        targetY = -(line[1] - centerY);
    }

    public AlignRobot(){
        // When Width = 300
        // centerX = 155.0;
        // centerY = 200.0;

        // Width = 200
        centerX = 100;
        centerY= 100; // wanted to change to 120
        useW = true;
        double[] line = m_vision.getLine();
        targetX = (line[0] - centerX);
        targetY = -(line[1] - centerY);
        targetW = -line[2];
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
        double[] line = m_vision.getLine();
        targetX = (line[0] - centerX);
        targetY = -(line[1] - centerY);
        targetW = -line[2];
        // speedX = 0.01 * Math.signum(targetX)*Math.sqrt(Math.abs(targetX));
        // speedY = 0.01 * Math.signum(targetY) *Math.sqrt(Math.abs(targetY));
        speedX = 0.002 * targetX;
        speedY = 0.002 * targetY;
        speedW = useW? 0.5 * targetW: 0;
        m_drive.setRobotSpeedType(0, speedX);
        m_drive.setRobotSpeedType(1, speedY); // Y is working well
        m_drive.setRobotSpeedType(2, speedW);
        
        // if (((m_vision.getLine(0) - centerX)) <1 && -(m_vision.getLine(1) - centerY) < 1 &&-m_vision.getLine(2)<0.05){
        //     m_endFlag = true;
        //     m_drive.setRobotSpeedType(0, 0);
        //     m_drive.setRobotSpeedType(1, 0); // Y is working well
        //     m_drive.setRobotSpeedType(2, 0);
        // }
        
        if (useW==false){
            if (((line[0] - centerX)) <1 && -(line[1] - centerY) < 1 &&-line[2]<0.05 && count >= 200) {
                
                m_drive.setRobotSpeedType(0, 0);
                m_drive.setRobotSpeedType(1, 0); // Y is working well
                m_drive.setRobotSpeedType(2, 0);
                m_endFlag = true;
            }  
          }
        else if (useW==true){
            
            
            if (((line[0] - centerX)) <1 && -(line[1] - centerY) < 1 &&-line[2]<0.05 && count >= 200) {
                
                m_drive.setRobotSpeedType(0, 0);
                m_drive.setRobotSpeedType(1, 0); // Y is working well
                m_drive.setRobotSpeedType(2, 0);
                m_endFlag = true;
            }
        }
          count ++;
        
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
