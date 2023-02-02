package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile;
import frc.robot.Globals;
//RobotContainer import
import frc.robot.RobotContainer;

//Subsystem imports
import frc.robot.subsystems.OmniDrive;
import frc.robot.subsystems.Vision;

public class AlignGripper extends MoveRobot {
    // Grab the subsystem instance from RobotContainer
    private final static OmniDrive m_drive = RobotContainer.m_omnidrive;
    private final static Vision m_vision = RobotContainer.m_vision;
    private final double _startSpeed;
    private double camera_offset_pixels = 25;
    private double ratio = 0;

    /**
     * This command is used to align the robot to the object that is to be picked
     */
    public AlignGripper() {
        super(0, 0, 0, 0, 0.4 );
        _startSpeed = 0;  
    }
     /**
     * Runs before execute
     */
    @Override
    public void initialize()
    {   
        if(Globals.curItemType==0)// for standing coke
            ratio = Globals.CokeRatio;
        else
            ratio = 1;
        super.m_dist = ((Globals.curItemX -400 ) * Globals.convertPxToM)*ratio-0.01;
        super.initialize();
    }
}