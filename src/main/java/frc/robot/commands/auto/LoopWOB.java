package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.CommandBase;
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
      for(Globals.curTarget = 0; Globals.curTarget < 3; Globals.curTarget++) { // target
        for(Globals.curItemType = 0; Globals.curItemType < 3; Globals.curItemType++) { // item
          while (Globals.Targets[Globals.curTarget][Globals.curItemType]>0) { // while item is not zero
            if(m_vision.getObjects()[Globals.curItemType*3]>0){ // if box contains item
              Globals.curItemY = m_vision.getObjects()[Globals.curItemType*3+2];
              Globals.curItemX = m_vision.getObjects()[Globals.curItemType*3+1];
              
              return false;
            }
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