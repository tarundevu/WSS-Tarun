package frc.robot.subsystems;

import java.util.Map;
import java.util.function.Supplier;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Globals;
import frc.robot.OmniDriveOdometry;
import frc.robot.RobotContainer;


public class Vision extends SubsystemBase{
    
    private final ShuffleboardTab tab = Shuffleboard.getTab("Vision");
    private NetworkTableInstance inst = NetworkTableInstance.getDefault();
    private NetworkTable table = inst.getTable("Shuffleboard/Vision");
    // private final NetworkTableEntry D_cW = tab.add("cW", 0).getEntry();
    private final NetworkTableEntry D_curTarget = tab.add("curTarget", 0).getEntry();
    private final NetworkTableEntry D_curBin = tab.add("curBin", 0).getEntry();
    // private final NetworkTableEntry D_JagabeeCount = tab.add("JagCnt", 0).getEntry();
    // private final NetworkTableEntry D_DettolCount = tab.add("DettolCnt", 0).getEntry();
    // private final NetworkTableEntry D_CokeCount = tab.add("CokeCnt", 0).getEntry();
    
    private final static Arm m_arm = RobotContainer.m_arm;
    private final NetworkTableEntry D_currentItem = tab.add("CurrentItem", 0).getEntry();
    private final NetworkTableEntry D_currentItemX = tab.add("CurrentItemX", 0).getEntry();
    private final NetworkTableEntry D_currentItemY = tab.add("CurrentItemY", 0).getEntry();
    private final NetworkTableEntry D_AddedArmX = tab.add("AddedArmX", 0).getEntry();
    private final NetworkTableEntry D_AddedRobotX = tab.add("AddedRobotX", 0).getEntry();
    private final NetworkTableEntry D_cvMode = tab.add("cvMode", 0).getEntry(); 
    private final NetworkTableEntry D_colorMode = tab.add("ColorMode", 0).getEntry();
    // private final NetworkTableEntry D_useTF = tab.add("useTF", 0).getEntry();
    private final NetworkTableEntry D_CameraMountOffsetX = tab.addPersistent("CameraMountOffsetX", 0.01).withWidget(BuiltInWidgets.kNumberSlider)
    .withProperties(Map.of("min", 0, "max", 0.1)).getEntry();
    private final NetworkTableEntry D_ConvertPxToM = tab.addPersistent("ConvertPxToM", 0.000625).withWidget(BuiltInWidgets.kNumberSlider)
    .withProperties(Map.of("min", 0, "max", 0.0007)).getEntry();
    private final NetworkTableEntry D_ArmOffsetZ = tab.addPersistent("ArmOffsetZ", 0.25).withWidget(BuiltInWidgets.kNumberSlider)
            .withProperties(Map.of("min", 0, "max", 0.5)).getEntry();
    private final NetworkTableEntry D_GripperOffsetZ = tab.addPersistent("GripperOffsetZ", 0.16).withWidget(BuiltInWidgets.kNumberSlider)
            .withProperties(Map.of("min", 0, "max", 0.25)).getEntry();
    private final NetworkTableEntry D_CokeRatio = tab.addPersistent("CokeRatio", 0.85).withWidget(BuiltInWidgets.kNumberSlider)
            .withProperties(Map.of("min", 0, "max", 1)).getEntry();
            
    private double[] defaultValue = new double[12];
    
    public Vision(){

  
    }
    
    public double [] getLine(){

      double[] line = (table.getEntry("line").getDoubleArray(defaultValue));
      return line;
    }
    
    public double getResolution(int wh){
      double[] dimension = new double[2];

      dimension[0] = 800;//(SmartDashboard.getNumber("imW",0));
      dimension[1] = 600;//(SmartDashboard.getNumber("imH",0));
      return dimension[wh];
    }

    // gets the 1d array passed from networktables and stores it in a 2d array
    public void getWOBItems(){
      // reads the array passed to the networktable
      double[] WOB = table.getEntry("WOB").getDoubleArray(defaultValue);
      // stores the data in Globals in a 2d array
      int[][] Targets = new int[3][3];
      int index = 0;
		  for (int ROW = 0; ROW < 3; ROW++){
		    for (int COL = 0; COL < 3; COL++) {
		    Targets[ROW][COL] = (int) WOB[index];
		    index++;
		    }
		  }
      Globals.Targets = Targets;
    }

    public double[] getObjects(){
     /*
       * 0 - CokeU Count
       * 1,2 - CokeU X,Y
       * 3 - Coke Count
       * 4,5 - Coke X,Y 
       * 6 - Dettol Count
       * 7,8 - Dettol X,Y 
       * 9 - Jagabee Count
       * 10,11 - Jagabee X,Y
       */
     
      double[] objects = (table.getEntry("objects").getDoubleArray(defaultValue));
      
      return objects;
  }

  public double[] getDistanceTarget(String targetName){
    double[] distance = (table.getEntry(targetName).getDoubleArray(defaultValue));
    return distance;
  }
  public void setColor(String color){
      /*
      * Black : 0
      * Red : 1
      * Green : 2
      * Blue: 3
      */
      int mode = 0;
      if (color == "Black"){
          mode = 0;
      }
      else if(color == "Red"){
          mode = 1;
      }
      else if(color == "Green"){
          mode = 2 ;
      }
      else if(color == "Blue"){
          mode = 3;
      }
      D_colorMode.setNumber(mode);
      
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

  public void updateAllPoints(){
      String[] targetAreas = {"RedTarget", "GreenTarget", "BlueTarget", "Trolley"};
      for (String targetName: targetAreas){
          if(getDistanceTarget(targetName)[0] != 0 && getDistanceTarget(targetName)[1] != 0 ){
              updatePoint(targetName);
          }
      }
  }

    public void initialize(){
      // Code written here will run before execute //
     Globals.curBin = 0;
     Globals.curTarget = 0;
     Globals.curItemType = 0;
     Globals.curAngle = 0;
     m_arm.setCameraAngle(290);
      
    }
    @Override
    public void periodic()
    {
      Globals.convertPxToM = D_ConvertPxToM.getDouble(0.0006225);
      Globals.camera_mount_offset_x = D_CameraMountOffsetX.getDouble(0.01);
      Globals.arm_offset_z = D_ArmOffsetZ.getDouble(0.25);
      Globals.gripper_offset = D_GripperOffsetZ.getDouble(0.19);
      Globals.CokeRatio = D_CokeRatio.getDouble(0.79);

        D_curBin.setNumber(Globals.curBin);
        D_curTarget.setNumber(Globals.curTarget);
        D_currentItem.setNumber(Globals.curItemType);
        // D_currentItemX.setNumber(Globals.curItemX);
        // D_currentItemY.setNumber(Globals.curItemY);
        // D_JagabeeCount.setNumber(getObjects()[0]);
        // D_DettolCount.setNumber(getObjects()[3]);
        // D_CokeCount.setNumber(getObjects()[6]);
        D_cvMode.setNumber(Globals.cvMode);
    
    }
}