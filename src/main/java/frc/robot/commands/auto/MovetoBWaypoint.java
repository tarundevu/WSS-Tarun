package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Transform2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import frc.robot.Globals;
import frc.robot.RobotContainer;

public class MovetoBWaypoint extends MovetoB{
  private Pose2d waypoint;
  private Translation2d addDist;
  private Translation2d curPose;
  private Rotation2d curRot;
  private Rotation2d addRotation;
  public MovetoBWaypoint() {
    super(new Pose2d(new Translation2d(0.46, 0.26), new Rotation2d()));
    
  }
  @Override
    public void initialize(){
      System.out.println("***************************");
      curPose = Globals.curPose.getTranslation();
      curRot = Globals.curPose.getRotation();
      addDist = Globals.pose2dMoveCommands[Globals.loopCount%4].getTranslation();
      addRotation = Globals.pose2dMoveCommands[Globals.loopCount%4].getRotation();
      waypoint = new Pose2d(new Translation2d(curPose.getX() + addDist.getX(), curPose.getY() + addDist.getY()), new Rotation2d());
      // // waypoint = new Pose2d(new Translation2d(curPose.getX() + addDist.getX(), curPose.getY() + addDist.getY()), new Rotation2d(curRot.getRadians() + addRotation.getRadians()));
      // waypoint = Globals.waypoint;
      System.out.println("Waypoint: " + waypoint);
      System.out.println("Curpose: " + curPose);
      super.m_posB = waypoint;
      MovetoB.m_posB2 = waypoint;
      super.initialize();
    }
}
