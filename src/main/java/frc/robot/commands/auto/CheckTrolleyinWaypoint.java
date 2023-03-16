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
  public static boolean MoveTrolleyCondition() {
    return m_trolleyFlag;
  }
  public static String trolleyID() {
    return m_trolley;
  }
  
  public CheckTrolleyinWaypoint(){
    super(
      new ConditionalCommand(
        new SequentialCommandGroup(
          new InstantCommand(()->System.out.println("onTrue!!!!!!!!!!!!!!!" )),
          new InstantCommand(()->System.out.println("Print Supplier")),
          new printSupplier(CheckTrolleyinWaypoint::trolleyID),
          new CheckAndMoveTarget(CheckTrolleyinWaypoint::trolleyID, 0.5),
          new InstantCommand(()->System.out.println("After CheckandMove" )),
          new Align2Trolley(),
          new TrolleyHolder(1),
          // new InstantCommand(()-> RobotContainer.m_points.removeObs(m_trolley)),
          new MovetoB(new Pose2d(new Translation2d(0.46, 0.3), new Rotation2d()))
        ),  new SequentialCommandGroup(
          new InstantCommand(()->System.out.print("onFalse!!!!!!!!!!!!!!!" ))),
        
        CheckTrolleyinWaypoint::MoveTrolleyCondition
      )
    );
  }
  @Override
  public void initialize() {
    for (Map.Entry<String, Pose2d> obstacleEntry : RobotContainer.m_points.obstacleMap.entrySet()) {
      if (obstacleEntry.getValue().getTranslation().getX() < Globals.curPose.getTranslation().getX() + Globals.robotRadius_m / 1000){
        m_trolleyFlag = true;
        m_trolley = obstacleEntry.getKey();
        break;
      }
    }
    System.out.println("Trolley Found: " + m_trolley);
    super.initialize();
  }
  
}
