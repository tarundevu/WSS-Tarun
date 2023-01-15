package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ProxyScheduleCommand;
import edu.wpi.first.wpilibj2.command.ScheduleCommand;
//import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Vision;
import frc.robot.Globals;

public class LoopWOB extends LoopCmd
{
    private final static Vision m_vision = RobotContainer.m_vision;
    
    public boolean endCondition(){
      // loops targets
      for(Globals.curTarget = 0; Globals.curTarget < 3; Globals.curTarget++) { 
        // loops items
        for(Globals.curItemType = 0; Globals.curItemType < 3; Globals.curItemType++) {
          // while array is not empty
          while (Globals.Targets[Globals.curTarget][Globals.curItemType]>0) { 
            // check if box contains item
            if(m_vision.getObjects()[Globals.curItemType*3]>0){ 
              // when last object is picked up, move on to 2nd pick up bin
              if((m_vision.getObjects()[0*3]+m_vision.getObjects()[1*3]+m_vision.getObjects()[2*3])==1)
                Globals.curBin = 1;

              Globals.curItemY = m_vision.getObjects()[Globals.curItemType*3+2];
              Globals.curItemX = m_vision.getObjects()[Globals.curItemType*3+1];
              Globals.Targets[Globals.curTarget][Globals.curItemType]--;
              return false;
            }
            else // if box does not contain current item carry on
              break; 
            
          }
        }
      }
    return true;
    }


    public LoopWOB(SequentialCommandGroup cmdToRun)
    {
        
        super(cmdToRun);
        
    }
    @Override
    public void execute()
    {
        
        if (cmd.isScheduled() == false) {
            //End condition for loopCmd
            if (endCondition()) {
                m_endFlag = true;
            }
            else {
                //schedule command
                cmd.schedule(false);
            }
        } 

    }

}