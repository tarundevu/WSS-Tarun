package frc.robot;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.util.ElementScanner6;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import frc.robot.Astar.Layout;

//Put all global variables here
public class Globals
{
    static public int menuItem;

    static public final int DNUM = 4;
    static public int debug[] = new int[DNUM];
    static public String[] debugNames = new String[] {"debug0", "debug1", "debug2", "debug3"};
// Vision //
    /** (length in m)/(800 pixels) */
    public static double convertPxToM = 0.00060625;//0.0006225;//0.0006075 // 0.56/800 , 0.00058 good // Resolution
    public static double camera_offset = 0.1; //actual is 10.5cm
    public static double camera_mount_offset_x = 0.015;// actual is 1.5cm
    public static double arm_offset_y = 0.13; // 0.125
    public static double arm_offset_z = 0.25;
    public static double gripper_offset = 0.19; // actual is 0.19
    /** (height of cam - (height of coke - 3cm))/(height of cam) */
    public static double CokeRatio = 0.79; // actual is 0.805
    public static double AdjustFactor = 1;
    public static int imH = 600;
    public static int imW = 800;
    /**
     *  CokeU = 0
     *  Coke  = 1
     *  Dettol  = 2
     *  Jagabee = 3
     */
    public static int curItemType = 0;
    public static double curItemX;
    public static double curItemY;
    /**
     *  Red   = 0
     *  Green = 1
     *  Blue  = 2
     */
    public static int curTarget = 0;
    /**
     *  Bin1 = 0
     *  Bin2 = 1
     */
    public static int curBin = 0;
    /** 
     *  Idle = -1 (does nothing, use to turn off the other modes)<p>
     *  Line detection = 0 <p>
     *  Object Detection = 1 <p>
     *  Work Order Board = 2
     */
    public static int cvMode = -1;
    public static Pose2d[] TargetList = new Pose2d[] {};
    public static Pose2d[] TrolleyList = new Pose2d[] {};
    /**
	   *                                              C|D|J  <p>            
	   *                                            R|x|x|x| <p>
	   *                                            G|x|x|x| <p>
	   *                                            B|x|x|x| <P>
     * 
     *  This array stores the number of items in each target area
     *  NOTE: even though coke upright and coke are different objects, they are stored under the same column
	   */
    public static int[][] Targets = new int[][] {}; 
// Omnidrive // 
    public static double curDir = 0;
    public static double curAngle = 0;
    public static double startYaw;
    public static int curTrolley = 0;
// Extra //
    public static int LoopCnt = 0; // use as counter for loops
    public static int loopCount = 0;
    public static Pose2d curPose;
    public static double[][] moveCommands = {
      {2,Math.PI/2,0,0, Math.PI/2},
      {2,-Math.PI/2,0,0, Math.PI/2},
      {0, -0.43, 0, 0, 0.4},
      {0, -0.43, 0, 0, 0.4}
    };
// End Conditions //

    public static float robotRadius_mm = 240;

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
              // if last item
              if((RobotContainer.m_vision.getObjects()[0*3]+RobotContainer.m_vision.getObjects()[1*3]+RobotContainer.m_vision.getObjects()[2*3]+RobotContainer.m_vision.getObjects()[3*3])==1)
                Globals.curBin++;

              Globals.curItemY = RobotContainer.m_vision.getObjects()[Globals.curItemType*3+2];
              Globals.curItemX = RobotContainer.m_vision.getObjects()[Globals.curItemType*3+1];
              Globals.Targets[Globals.curTarget][0]--; // decrements ONLY the column[0] with coke
              return false;
            }
            else if((RobotContainer.m_vision.getObjects()[0*3]+RobotContainer.m_vision.getObjects()[1*3]+RobotContainer.m_vision.getObjects()[2*3]+RobotContainer.m_vision.getObjects()[3*3])==0){
              if (Globals.curBin==0){
                Globals.curBin++;
                return false;
              }
              else 
                return true;
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
              // If last item
              if((RobotContainer.m_vision.getObjects()[0*3]+RobotContainer.m_vision.getObjects()[1*3]+RobotContainer.m_vision.getObjects()[2*3]+RobotContainer.m_vision.getObjects()[3*3])==1)
                Globals.curBin++;

              Globals.curItemY = RobotContainer.m_vision.getObjects()[Globals.curItemType*3+2];
              Globals.curItemX = RobotContainer.m_vision.getObjects()[Globals.curItemType*3+1];
              Globals.Targets[Globals.curTarget][Globals.curItemType-1]--;
              return false;
            }
            else if((RobotContainer.m_vision.getObjects()[0*3]+RobotContainer.m_vision.getObjects()[1*3]+RobotContainer.m_vision.getObjects()[2*3]+RobotContainer.m_vision.getObjects()[3*3])==0){
              if (Globals.curBin==0){
                Globals.curBin++;
                return false;
              }
              else 
                return true;
              
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

  public static boolean endConditionTaskBMapping(){
    loopCount++;
    // Count 19
    if(loopCount<19){
        return false;
    }
    else{
        loopCount = 0;
        return true;
    }
  } 
}