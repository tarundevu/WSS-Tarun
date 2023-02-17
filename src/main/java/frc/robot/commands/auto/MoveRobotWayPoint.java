package frc.robot.commands.auto;

import frc.robot.Globals;

public class MoveRobotWayPoint extends MoveRobot {
    public MoveRobotWayPoint(){
        super((int)Globals.moveCommands[Globals.loopCount%4][0], Globals.moveCommands[Globals.loopCount%4][1], Globals.moveCommands[Globals.loopCount%4][2], Globals.moveCommands[Globals.loopCount%4][3], Globals.moveCommands[Globals.loopCount%4][4]);
    }
    @Override
    public void initialize(){
        super.m_profType = (int)Globals.moveCommands[Globals.loopCount%4][0];
        super.m_dist = Globals.moveCommands[Globals.loopCount%4][1];
        super.m_startSpeed = Globals.moveCommands[Globals.loopCount%4][2];
        super.m_endSpeed = Globals.moveCommands[Globals.loopCount%4][3];
        super.m_maxSpeed = Globals.moveCommands[Globals.loopCount%4][4];
        super.initialize();
    }
}