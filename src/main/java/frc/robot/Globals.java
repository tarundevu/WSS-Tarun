package frc.robot;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import frc.robot.Astar.Layout;

//Put all global variables here
public class Globals
{
    static public int menuItem;

    static public final int DNUM = 4;
    static public int debug[] = new int[DNUM];
    static public String[] debugNames = new String[] {"debug0", "debug1", "debug2", "debug3"};
    public static double curDir = 0;
    public static double convertPxToM = 0.0006225;//0.000590;//0.0006075 // 0.56/800 , 0.00058 good // Resolution
    public static double camera_offset = 0.09;
    public static double camera_mount_offset_x = 0.015; 
    public static double arm_offset_y = 0.13; // 0.125
    public static double arm_offset_z = 0.25;
    public static double gripper_offset = 0.19;
    public static double CokeRatio = 0.79;
    public static double AdjustFactor = 0.8;
    public static int imH = 600;
    public static int imW = 800;
    public static int LoopCnt = 0; // use as counter for loops
    /*
     *  CokeU = 0
     *  Coke  = 1
     *  Dettol  = 2
     *  Jagabee = 3
     */
    public static int curItemType = 0;
    public static double curItemX;
    public static double curItemY;
    public static double curAngle = 0;
    public static double IRdist = 0;
    public static Pose2d curPose;
    public static int loopCount = 0;
    public static double startYaw;
    /*
     *  Red   = 0
     *  Green = 1
     *  Blue  = 2
     */
    public static int curTarget = 0;
    /*
     *  Bin1 = 0
     *  Bin2 = 1
     */
    public static int curBin = 0;
    /*  
     *  Idle = -1 (does nothing, use to turn off the other modes)
     *  Line detection = 0
     *  Object Detection = 1
     *  Work Order Board = 2
     */
    public static int cvMode = -1;

    public static double[][] moveCommands = {
      {2,Math.PI/2,0,0, Math.PI/2},
      {2,-Math.PI/2,0,0, Math.PI/2},
      {0, -0.43, 0, 0, 5},
      {0, -0.43, 0, 0, 5}
    };

    public static Pose2d[] TargetList = new Pose2d[] {};
    public static Pose2d[] TrolleyList = new Pose2d[] {};
    /*
	   *                                              C|D|J              
	   *                                            R|x|x|x|
	   *                                            G|x|x|x|
	   *                                            B|x|x|x|
     * 
     *  This array stores the number of items in each target area
     *  NOTE: even though coke upright and coke are different objects, they are stored under the same column
	   */
    public static int[][] Targets = new int[][] {}; 
    
    // End condition for pick and place
    // NOTE: 2d array has 3 columns but there are 4 objects
    public static boolean WOBLoopCondition(){
        // loops targets
      for(Globals.curTarget = 0; Globals.curTarget < 3; Globals.curTarget++) { 
        // loops items
        for(Globals.curItemType = 0; Globals.curItemType < 4; Globals.curItemType++) {
          // IF curItem is a coke
          if(Globals.curItemType==0 || Globals.curItemType==1){
            // while array is not empty
            while (Globals.Targets[Globals.curTarget][0]>0) { 
                // check if box contains item
              if(RobotContainer.m_vision.getObjects()[Globals.curItemType*3]>0){ 

                Globals.curItemY = RobotContainer.m_vision.getObjects()[Globals.curItemType*3+2];
                Globals.curItemX = RobotContainer.m_vision.getObjects()[Globals.curItemType*3+1];
                Globals.Targets[Globals.curTarget][0]--; // decrements ONLY the column[0] with coke
                return false;
              }
              else // if box does not contain current item carry on
                break; 
            }
          }
          // If the item is not coke
          else{
            // while array is not empty
            while (Globals.Targets[Globals.curTarget][Globals.curItemType-1]>0) { 
            // check if box contains item
              if(RobotContainer.m_vision.getObjects()[Globals.curItemType*3]>0){ 

                Globals.curItemY = RobotContainer.m_vision.getObjects()[Globals.curItemType*3+2];
                Globals.curItemX = RobotContainer.m_vision.getObjects()[Globals.curItemType*3+1];
                Globals.Targets[Globals.curTarget][Globals.curItemType-1]--;
                return false;
              }
              else // if box does not contain current item carry on
                break; 
            }
          }
        }
      }
      Globals.curItemType = 0;
      Globals.curTarget = 0;
    return true;
  }

  public static boolean endConditionCP5(String targetArea){
    loopCount++;
    if(loopCount<19 && RobotContainer.m_vision.getDistanceTarget(targetArea)[0] == 0){
        return false;
    }
    else{
        loopCount = 0;
        return true;
    }
  } 

  public static boolean endConditionCP7(){
    loopCount++;
    // Count 19
    if(loopCount<19 && (RobotContainer.m_vision.getDistanceTarget("Trolley")[0] == 0)){
        return false;
    }
    else{
        loopCount = 0;
        return true;
    }
  } 
}