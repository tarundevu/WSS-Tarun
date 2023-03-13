package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile;
import frc.robot.Globals;

public class MoveRobotWayPoint extends MoveRobot {
    public MoveRobotWayPoint(){
        super((int)Globals.moveCommands[Globals.loopCount%4][0], 0,0,0,Globals.moveCommands[Globals.loopCount%4][4]);
        // super((int)Globals.moveCommands[Globals.loopCount%4][0], Globals.moveCommands[Globals.loopCount%4][1], Globals.moveCommands[Globals.loopCount%4][2], Globals.moveCommands[Globals.loopCount%4][3], Globals.moveCommands[Globals.loopCount%4][4]);
    }
    @Override
    public void initialize(){
        
        super.m_profType = (int)Globals.moveCommands[Globals.loopCount%4][0];
        super.m_dist = Globals.moveCommands[Globals.loopCount%4][1];
        super.m_startSpeed = Globals.moveCommands[Globals.loopCount%4][2];
        super.m_endSpeed = Globals.moveCommands[Globals.loopCount%4][3];
        super.m_maxSpeed = Globals.moveCommands[Globals.loopCount%4][4];
        if (m_profType==2){
            super.m_constraints = new TrapezoidProfile.Constraints(super.m_maxSpeed, Math.PI/2);
        }
        else{
            super.m_constraints = new TrapezoidProfile.Constraints(super.m_maxSpeed, 0.5);
        }
        super.initialize();
    }
}