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
  private double[] defaultValue = new double[13];
  public Pose2d camOffset = new Pose2d(0.015, 0.59, new Rotation2d(0)); // Update this
  private int trolleyCount = 1;
  private int binCount = 1;

  public Points() {
    trolleyCount = 1;
    binCount = 1;
    pointMap.put("Bin1", new Pose2d(new Translation2d((double)(Layout.obs_mm[0][0]/1000.0),(double)(Layout.obs_mm[0][1]/1000)), new Rotation2d(Layout.obs_mm[0][4]/180 * Math.PI)));
    obstacleMap.put("Bin1", new Pose2d(new Translation2d((double)(Layout.obs_mm[0][0]/1000.0),(double)(Layout.obs_mm[0][1]/1000)), new Rotation2d(Layout.obs_mm[0][4]/180 * Math.PI)));
    pointMap.put("T1",trolley);
    pointMap.put("T2",trolley);
    pointMap.put("T3",trolley);
    pointMap.put("RedTarget",redTarget);
    pointMap.put("BlueTarget",blueTarget);
    pointMap.put("GreenTarget",greenTarget);
  }
  

  public void resetMap() {
    trolleyCount = 1;
    binCount = 1;
    pointMap.clear();
    pointMap.put("Bin1", new Pose2d(new Translation2d((double)(Layout.obs_mm[0][0]/1000.0),(double)(Layout.obs_mm[0][1]/1000)), new Rotation2d(Layout.obs_mm[0][4]/180 * Math.PI)));
    
  }
  public void resetObsMap() {
    obstacleMap.clear();
  }

  public void updateObsPoint(String pointname, Pose2d newpose) {
    obstacleMap.put(pointname, newpose);
    
  }
  
  public void addPoint(String pointname, Pose2d newpose) {
    pointMap.put(pointname, newpose);
    
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
  // If trolley, check size of array
  double x, y;
  int w = (int)Globals.curPose.getRotation().getDegrees();
  int totalTrolleys = 1, totalBins = 1;
  boolean alreadyExist = false;
  double tolerance = 0.3;
  Pose2d upperbound = new Pose2d();
  Pose2d lowerbound = new Pose2d();
  if(targetName == "Trolley"){
    totalTrolleys += getDistanceTarget(targetName)[2] > 0? 1 : 0 + getDistanceTarget(targetName)[4] > 0? 1 : 0;
    for(int i = 0; i< totalTrolleys; i++){
      alreadyExist = false;
      if (w != -90){  // If robot is in the same axis as the arena
          y = RobotContainer.m_omnidrive.getPose().getTranslation().getY() + getDistanceTarget(targetName)[i * 2 + 1] + camOffset.getTranslation().getY();
          x = RobotContainer.m_omnidrive.getPose().getTranslation().getX() + getDistanceTarget(targetName)[i * 2 + 0] + camOffset.getTranslation().getX();
      }
      else{
          x = RobotContainer.m_omnidrive.getPose().getTranslation().getX() + getDistanceTarget(targetName)[i * 2 + 1] + camOffset.getTranslation().getY();
          y = RobotContainer.m_omnidrive.getPose().getTranslation().getY() - getDistanceTarget(targetName)[i * 2 + 0] + camOffset.getTranslation().getX(); 
      }

      upperbound = new Pose2d(new Translation2d(x + tolerance, y + tolerance), new Rotation2d());
      lowerbound = new Pose2d(new Translation2d(x - tolerance, y - tolerance), new Rotation2d());
      
      
      for(Pose2d location : pointMap.values()){
        if((location.getTranslation().getX() >= lowerbound.getTranslation().getX() && location.getTranslation().getX() <=upperbound.getTranslation().getX()) && (location.getTranslation().getY() >= lowerbound.getTranslation().getY() && location.getTranslation().getY() <=upperbound.getTranslation().getY())){
          alreadyExist = true;
          break;
        }
      }
        
        if(!alreadyExist){
          String name = new String();
          name = "T" + trolleyCount++;
          pointMap.put(name, new Pose2d(new Translation2d(x, y), Globals.curPose.getRotation()));
          obstacleMap.put(name, new Pose2d(new Translation2d(x, y), Globals.curPose.getRotation()));    
        }
      }
  }
  // else if(targetName == "Bin"){
  //   totalBins += getDistanceTarget(targetName)[3] > 0? 1 : 0 ;
  //   for(int i = 0; i< totalBins; i++){
  //     alreadyExist = false;
  //     if (w != -90){  // If robot is in the same axis as the arena
  //         y = RobotContainer.m_omnidrive.getPose().getTranslation().getY() + getDistanceTarget(targetName)[i * 3 + 1] + camOffset.getTranslation().getY();
  //         x = RobotContainer.m_omnidrive.getPose().getTranslation().getX() + getDistanceTarget(targetName)[i * 3 + 0] + camOffset.getTranslation().getX();
  //     }
  //     else{
  //         x = RobotContainer.m_omnidrive.getPose().getTranslation().getX() + getDistanceTarget(targetName)[i * 3 + 1] + camOffset.getTranslation().getY();
  //         y = RobotContainer.m_omnidrive.getPose().getTranslation().getY() - getDistanceTarget(targetName)[i * 3 + 0] + camOffset.getTranslation().getX(); 
  //     }

  //     upperbound = new Pose2d(new Translation2d(x + tolerance, y + tolerance), new Rotation2d());
  //     lowerbound = new Pose2d(new Translation2d(x - tolerance, y - tolerance), new Rotation2d());
      
      
  //     for(Pose2d location : pointMap.values()){
  //       if((location.getTranslation().getX() >= lowerbound.getTranslation().getX() && location.getTranslation().getX() <=upperbound.getTranslation().getX()) && (location.getTranslation().getY() >= lowerbound.getTranslation().getY() && location.getTranslation().getY() <=upperbound.getTranslation().getY())){
  //         alreadyExist = true;
  //         break;
  //       }
  //     }
        
  //       if(!alreadyExist){
  //         String name = new String();
  //         name = "Bin" + ++binCount;
  //         pointMap.put(name, new Pose2d(new Translation2d(x, y), Globals.curPose.getRotation()));
  //         obstacleMap.put(name, new Pose2d(new Translation2d(x, y), Globals.curPose.getRotation()));    
  //       }
  //     }
  // }
  else{
    if (w != -90){
      y = RobotContainer.m_omnidrive.getPose().getTranslation().getY() + getDistanceTarget(targetName)[1] + camOffset.getTranslation().getY();
      x = RobotContainer.m_omnidrive.getPose().getTranslation().getX() + getDistanceTarget(targetName)[0] + camOffset.getTranslation().getX();
    }
    else{
          x = RobotContainer.m_omnidrive.getPose().getTranslation().getX() + getDistanceTarget(targetName)[1] + camOffset.getTranslation().getY();
          y = RobotContainer.m_omnidrive.getPose().getTranslation().getY() - getDistanceTarget(targetName)[0] + camOffset.getTranslation().getX(); 
    }  
      
      
      // double upperbound = distanceFromOrigin + tolerance;
      // double lowerbound = distanceFromOrigin - tolerance;
      upperbound = new Pose2d(new Translation2d(x + tolerance, y + tolerance), new Rotation2d());
      lowerbound = new Pose2d(new Translation2d(x - tolerance, y - tolerance), new Rotation2d());
      
      // Some values given by python script is lesser than startpos, why? idk
      
      for(Pose2d location : pointMap.values()){
        if((location.getTranslation().getX() >= lowerbound.getTranslation().getX() && location.getTranslation().getX() <=upperbound.getTranslation().getX()) && (location.getTranslation().getY() >= lowerbound.getTranslation().getY() && location.getTranslation().getY() <=upperbound.getTranslation().getY())){
          alreadyExist = true;
          break;
        }
      }
        
      if(!alreadyExist){
            pointMap.put(targetName, new Pose2d(new Translation2d(x, y), Globals.curPose.getRotation()));
      }    
  }

      
}

public void updateAllPoints(){
  String[] targetAreas = {"RedTarget", "GreenTarget", "BlueTarget", "Trolley", "Bin"};
  for (String targetName: targetAreas){
      if(getDistanceTarget(targetName)[0] != 0 && getDistanceTarget(targetName)[1] != 0 ){
          updatePoint(targetName);
      }
  }
}


// From hashmap
  public void AddObsGrid(){
    int tile_size_mm = RobotContainer.m_layout.tile_size_mm;
   
    for (Map.Entry<String, Pose2d> obstacleEntry :obstacleMap.entrySet()) {
      if(obstacleEntry.getKey().contains( "T")) {
          int cx_mm = (int)(obstacleEntry.getValue().getTranslation().getX()*1000);
          int cy_mm= (int)(obstacleEntry.getValue().getTranslation().getY()*1000);
          RobotContainer.m_Grid.AddObsRound(Math.round((float)cx_mm/tile_size_mm), Math.round((float)cy_mm/tile_size_mm), Math.round((float)300/tile_size_mm));  
      }
      else if(obstacleEntry.getKey().contains( "Bin")) {
        int cx_mm = (int)(obstacleEntry.getValue().getTranslation().getX()*1000);
        int cy_mm= (int)(obstacleEntry.getValue().getTranslation().getY()*1000);
        RobotContainer.m_Grid.AddObstacle(Math.round((float)cx_mm/tile_size_mm), Math.round((float)cy_mm/tile_size_mm), Math.round((float)300/tile_size_mm), Math.round((float)420/tile_size_mm), obstacleEntry.getValue().getRotation().getRadians());  
      }
          
    }
        
    RobotContainer.m_Grid.ExpandObstacles(Globals.robotRadius_mm);
  }
  public void removeObs(String key){
    RobotContainer.m_Grid.ClearObs(); 
    RobotContainer.m_Grid.AddFixedObstacles(RobotContainer.m_layout);
    RobotContainer.m_Grid.ExpandObstacles(Globals.robotRadius_mm);
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