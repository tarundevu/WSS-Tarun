package frc.robot.subsystems;

import java.util.function.Supplier;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Globals;
import frc.robot.RobotContainer;

public class Vision extends SubsystemBase{
    
    private final ShuffleboardTab tab = Shuffleboard.getTab("Vision");
    private NetworkTableInstance inst = NetworkTableInstance.getDefault();
    private NetworkTable table = inst.getTable("SmartDashboard");
    // private final NetworkTableEntry D_cW = tab.add("cW", 0).getEntry();
    private final NetworkTableEntry D_targetX = tab.add("TargetX", 0).getEntry();
    private final NetworkTableEntry D_curTarget = tab.add("curTarget", 0).getEntry();
    private final NetworkTableEntry D_curBin = tab.add("curBin", 0).getEntry();
    private final NetworkTableEntry D_JagabeeCount = tab.add("JagCnt", 0).getEntry();
    private final NetworkTableEntry D_DettolCount = tab.add("DettolCnt", 0).getEntry();
    private final NetworkTableEntry D_CokeCount = tab.add("CokeCnt", 0).getEntry();

    public final NetworkTableEntry D_targetXArm = tab.add("targetXArm", 0).getEntry();
    private final static Arm m_arm = RobotContainer.m_arm;
    // private final String[] items = {
    //   "Dettol",
    //   "Jagabee",
    //   "Coke"
    // };
    private final NetworkTableEntry D_currentItem = tab.add("CurrentItem", 0).getEntry();
    private final NetworkTableEntry D_currentItemX = tab.add("CurrentItemX", 0).getEntry();
    private final NetworkTableEntry D_currentItemY = tab.add("CurrentItemY", 0).getEntry();
    private final NetworkTableEntry D_AddedArmX = tab.add("AddedArmX", 0).getEntry();
    private final NetworkTableEntry D_AddedRobotX = tab.add("AddedRobotX", 0).getEntry();
    // private final NetworkTableEntry D_useTF = tab.add("useTF", 0).getEntry();
    private double[] defaultValue = new double[1];

    public Vision(){

        m_arm.setCameraAngle(280); // Look down
       
    }

    public double [] getLine(){

      double[] line = (SmartDashboard.getEntry("line").getDoubleArray(defaultValue));
      return line;
    }
    
    public double getResolution(int wh){
      double[] dimension = new double[2];

      dimension[0] = 800;//(SmartDashboard.getNumber("imW",0));
      dimension[1] = 600;//(SmartDashboard.getNumber("imH",0));
      return dimension[wh];
    }

    public void setcvMode(){
      SmartDashboard.putNumber("cvMode", Globals.cvMode);
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
       * 1,2 - Coke X,Y
       * 3 - Coke Count
       * 4,5 - Coke X,Y 
       * 6 - Dettol Count
       * 7,8 - Dettol X,Y 
       * 9 - Jagabee Count
       * 10,11 - Jagabee X,Y
       */
     
      double[] objects = (SmartDashboard.getEntry("objects").getDoubleArray(defaultValue));
      
      return objects;
  }
    @Override
    public void periodic()
    {
        D_curBin.setNumber(Globals.curBin);
        D_curTarget.setNumber(Globals.curTarget);
        D_currentItem.setNumber(Globals.curItemType);
        D_currentItemX.setNumber(Globals.curItemX);
        D_currentItemY.setNumber(Globals.curItemY);
        D_AddedRobotX.setNumber(((Globals.curItemX -400) * Globals.convertPxToM));
        D_AddedArmX.setNumber(m_arm.getArmPos().getX() + Globals.camera_offset - (Globals.curItemY - getResolution(1)/2) * Globals.convertPxToM);
        // D_JagabeeCount.setNumber(getObjects()[0]);
        // D_DettolCount.setNumber(getObjects()[3]);
        // D_CokeCount.setNumber(getObjects()[6]);
    
    }
}
