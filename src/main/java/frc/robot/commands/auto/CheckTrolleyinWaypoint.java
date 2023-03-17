package frc.robot.commands.auto;

import java.util.Map;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Globals;
import frc.robot.RobotContainer;

public class CheckTrolleyinWaypoint extends SequentialCommandGroup{
  private static boolean m_trolleyFlag = false;
  private static String m_trolley;
  private static Pose2d previousPose;
  public static boolean MoveTrolleyCondition() {
    return m_trolleyFlag;
  }
  public static String trolleyID() {
    return m_trolley;
  }
  public static Pose2d previousPose() {
    return previousPose;
  }
  public static Pose2d placeholderTrolleyPos() {
    return RobotContainer.m_Grid.findGotoPos(Globals.placeholderTrolleyPos[Globals.placeholderCount].getTranslation(), 0.5);
  }
  
  
  public CheckTrolleyinWaypoint(){
    super(
      new ConditionalCommand(
        new SequentialCommandGroup(
          new InstantCommand(()->System.out.println("onTrue!!!!!!!!!!!!!!!" )),
  

          new InstantCommand(()-> previousPose = RobotContainer.m_omnidrive.getPose()),
          new InstantCommand(()->System.out.println("Stored current pose as previous pose: " + previousPose)),
          new InstantCommand(()->System.out.println("Point " + RobotContainer.m_points.getPoint(m_trolley))),
          new CheckAndMoveTarget(CheckTrolleyinWaypoint::trolleyID, 0.5),
          new Align2Trolley(),
          new TrolleyHolder(1),
          new InstantCommand(()-> RobotContainer.m_points.removeObs(m_trolley)),
          new InstantCommand(()->System.out.println("Removed Obstacle " + m_trolley)),
          new InstantCommand(()->System.out.println("Placeholder Pos: " + RobotContainer.m_Grid.findGotoPos(Globals.placeholderTrolleyPos[Globals.placeholderCount].getTranslation(), 0.5))),
          new MovetoB(CheckTrolleyinWaypoint::placeholderTrolleyPos),
          new Rotate2Orientation(CheckTrolleyinWaypoint::placeholderTrolleyPos),
          new InstantCommand(()-> Globals.placeholderCount++),
          new InstantCommand(()->System.out.println("placeholderCount " + Globals.placeholderCount)),
          new InstantCommand(()-> RobotContainer.m_points.addPoint(m_trolley, Globals.placeholderTrolleyPos[Globals.placeholderCount])), 
          new TrolleyHolder(0),
          new MoveRobot(1, -0.05, 0, 0, 0.1),
          new MovetoB(CheckTrolleyinWaypoint::previousPose),
          new Rotate2Orientation(CheckTrolleyinWaypoint::previousPose),
          new InstantCommand(()-> m_trolleyFlag = false),
          new MoveArm(new Translation2d(0.3,0.4), 2),
          new MoveCamera(282)

        ),  new SequentialCommandGroup(
          new InstantCommand(()->System.out.print("onFalse!!!!!!!!!!!!!!!" ))),
        
        CheckTrolleyinWaypoint::MoveTrolleyCondition
      )
    );
  }
  @Override
  public void initialize() {
    for (Map.Entry<String, Pose2d> obstacleEntry : RobotContainer.m_points.obstacleMap.entrySet()) {
      if (obstacleEntry.getValue().getTranslation().getX() < Globals.curPose.getTranslation().getX() + Globals.robotRadius_mm / 1000){
        m_trolleyFlag = true;
        m_trolley = obstacleEntry.getKey();
        break;
      }
    }
    System.out.println("Trolley Found: " + m_trolley);
    super.initialize();
  }
  
}
