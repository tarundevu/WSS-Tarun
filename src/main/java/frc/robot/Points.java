package frc.robot;

import java.util.HashMap;
import java.util.Map;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Transform2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Astar.AStarAlgorithm;
import frc.robot.Astar.Grid;
import frc.robot.Astar.Layout;

public class Points{
  private final ShuffleboardTab tab = Shuffleboard.getTab("Points");
  private NetworkTableInstance inst = NetworkTableInstance.getDefault();

  private NetworkTable pointsTable = inst.getTable("Shuffleboard/Points");

  public Map<String, Pose2d> pointMap = new HashMap<>();
  public Map<String, Pose2d> obstacleMap = new HashMap<>();
  private Layout layout = RobotContainer.m_layout;
    // Target Areas
  public Pose2d redTarget = new Pose2d();
  public Pose2d greenTarget = new Pose2d();
  public Pose2d blueTarget = new Pose2d();
  public Pose2d trolley = new Pose2d();
  private double[] defaultValue = new double[12];
  public Pose2d camOffset = new Pose2d(0.00, 0.0, new Rotation2d(0)); // Update this
  // public Pose2d camOffset = new Pose2d(0.015, 0.58, new Rotation2d(0)); // Update this
  // public Pose2d camOffset = new Pose2d(-0.015, 0.55, new Rotation2d(0)); // Update this
  // public Pose2d camOffset = new Pose2d(-0.015, 0.5, new Rotation2d(0)); // Update this

  // public Pose2d camOffset = new Pose2d(0.015, 0.67, new Rotation2d(0)); // Update this
  // public Pose2d camOffset = new Pose2d(0.015, 1.02, new Rotation2d(0)); // Update this

  public Points() {
    pointMap.put("RedTarget", redTarget);
    pointMap.put("GreenTarget", greenTarget);
    pointMap.put("BlueTarget", blueTarget);
    pointMap.put("Trolley", trolley);
    //obstacleMap.put("Trolley", trolley);
  }
  

  public void resetMap() {
    pointMap.clear();
    
  }

  public void updatePoint(String pointname, Pose2d newpose) {
    pointMap.put(pointname, newpose);
    // if (pointname == "Trolley"){
    //   obstacleMap.put(pointname, newpose.plus(new Transform2d(new Translation2d(0,0.38),new Rotation2d())));
      
    // }
    // else{
    //   obstacleMap.put(pointname, newpose.plus(new Transform2d(new Translation2d(0,0.255),new Rotation2d())));
    // }
    if(pointname != "Trolley"){
      pointMap.put(pointname, newpose.plus(new Transform2d(new Translation2d(0.05, 0.30),new Rotation2d())));
      obstacleMap.put(pointname, newpose.plus(new Transform2d(new Translation2d(0.0, 0.95),new Rotation2d())));
    }
    else{
      // Adding Point Trolley
      pointMap.put(pointname, newpose.plus(new Transform2d(new Translation2d(0.05, 0.35),new Rotation2d())));
    }

    
    
  }
  public void updateObsPoint(String pointname, Pose2d newpose) {
    obstacleMap.put(pointname, newpose);
    
  }
  

  public void addPoint(String pointname, Pose2d newpose) {
    pointMap.put(pointname, newpose);
    
  }
  public void addObstaclePoint(String pointname, Pose2d newpose) {
    Pose2d pose = newpose;
    if (pointname == "Trolley"){
      obstacleMap.put(pointname, pose.plus(new Transform2d(new Translation2d(0,0.38),new Rotation2d())));
    }
    else{
      obstacleMap.put(pointname, pose.plus(new Transform2d(new Translation2d(0,0.255),new Rotation2d())));
    }
    obstacleMap.put(pointname, pose);
  }

  

  public Pose2d getPoint(String pointname) {
    if (pointMap.containsKey(pointname))
      return pointMap.get(pointname);
    else
      return Globals.curPose;
  }

  public Pose2d getObstacle(String pointname) {
    if (obstacleMap.containsKey(pointname))
      return obstacleMap.get(pointname);
    else
      return Globals.curPose;
    
  }
  
  
 
  public double[] getDistanceTarget(String targetName){
    double[] distance = (pointsTable.getEntry(targetName).getDoubleArray(defaultValue));
    return distance;
}
public void updatePoint(String targetName){
  double x, y;
  int w = (int)Globals.curPose.getRotation().getDegrees();
  if (w != -90){
      y = Globals.curPose.getTranslation().getY() + getDistanceTarget(targetName)[1] + RobotContainer.m_points.camOffset.getTranslation().getY();
      x = Globals.curPose.getTranslation().getX() + getDistanceTarget(targetName)[0] + RobotContainer.m_points.camOffset.getTranslation().getX();
  }
  else{
      x = Globals.curPose.getTranslation().getX() + getDistanceTarget(targetName)[1] + RobotContainer.m_points.camOffset.getTranslation().getY();
      y = Globals.curPose.getTranslation().getY() - getDistanceTarget(targetName)[0] + RobotContainer.m_points.camOffset.getTranslation().getX(); 
  }
  RobotContainer.m_points.updatePoint(targetName, new Pose2d(new Translation2d(x, y), Globals.curPose.getRotation()));  
}
public void updateObsPoint(String obsName){
  double x, y;
  int w = (int)Globals.curPose.getRotation().getDegrees();
  if (w != -90){
      y = Globals.curPose.getTranslation().getY() + getDistanceTarget(obsName)[1] + RobotContainer.m_points.camOffset.getTranslation().getY();
      x = Globals.curPose.getTranslation().getX() + getDistanceTarget(obsName)[0] + RobotContainer.m_points.camOffset.getTranslation().getX();
  }
  else{
      x = Globals.curPose.getTranslation().getX() + getDistanceTarget(obsName)[1] + RobotContainer.m_points.camOffset.getTranslation().getY();
      y = Globals.curPose.getTranslation().getY() - getDistanceTarget(obsName)[0] + RobotContainer.m_points.camOffset.getTranslation().getX(); 
  }
  RobotContainer.m_points.updateObsPoint(obsName, new Pose2d(new Translation2d(x, y), Globals.curPose.getRotation()));  
}
public void updateAllTarget(){
  String[] targetAreas = {"RedTarget", "GreenTarget", "BlueTarget", "Trolley"};
  for (String targetName: targetAreas){
      if(getDistanceTarget(targetName)[0] != 0 && getDistanceTarget(targetName)[1] != 0 ){
          updatePoint(targetName);
      }
  }
}
public void updateAllObs(){
  String[] targetAreas = {"Trolley", "Bin"};
  for (String targetName: targetAreas){
      if(getDistanceTarget(targetName)[0] != 0 && getDistanceTarget(targetName)[1] != 0 ){
          updateObsPoint(targetName);
      }
  }
}

// From hashmap
  public void AddObsGrid(){
    int tile_size_mm = RobotContainer.m_layout.tile_size_mm;
   
    for (Map.Entry<String, Pose2d> obstacleEntry :obstacleMap.entrySet()) {
          int cx_mm = (int)(obstacleEntry.getValue().getTranslation().getX()*1000);
          int cy_mm= (int)(obstacleEntry.getValue().getTranslation().getY()*1000);
          RobotContainer.m_Grid.AddObstacle(Math.round((float)cx_mm/tile_size_mm), Math.round((float)cy_mm/tile_size_mm), Math.round((float)300/tile_size_mm), Math.round((float)300/tile_size_mm), obstacleEntry.getValue().getRotation().getRadians());  
    }
        
    RobotContainer.m_Grid.ExpandObstacles(200);
  }
  public void removeObs(String key){
    RobotContainer.m_Grid = new Grid(RobotContainer.m_layout);
    RobotContainer.m_Astar = new AStarAlgorithm(RobotContainer.m_Grid);
    obstacleMap.remove(key);
    AddObsGrid();
  }
  public void SetTrolleysAsObstacles(){
    obstacleMap.put("T1", Layout.Convert_mm_Pose2d(Layout.T1Pos));
    obstacleMap.put("T2", Layout.Convert_mm_Pose2d(Layout.T2Pos));
    obstacleMap.put("T3", Layout.Convert_mm_Pose2d(Layout.T3Pos));
    AddObsGrid();
  }
}