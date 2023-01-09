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
    private NetworkTable table = inst.getTable("Vision");
    private final NetworkTableEntry D_cW = tab.add("cW", 0).getEntry();
    private final NetworkTableEntry D_targetX = tab.add("TargetX", 0).getEntry();
    private final NetworkTableEntry D_itemcnt = tab.add("itemcnt1", 0).getEntry();
    private final NetworkTableEntry D_JagabeeCount = tab.add("JagCnt", 0).getEntry();
    private final NetworkTableEntry D_DettolCount = tab.add("DettolCnt", 0).getEntry();
    private final NetworkTableEntry D_CokeCount = tab.add("CokeCnt", 0).getEntry();

    public final NetworkTableEntry D_targetXArm = tab.add("targetXArm", 0).getEntry();
    private final static Arm m_arm = RobotContainer.m_arm;
    private final String[] items = {
      "Dettol",
      "Jagabee",
      "Coke"
    };
    private final NetworkTableEntry D_currentItem = tab.add("CurrentItem", 0).getEntry();
    private final NetworkTableEntry D_currentItemX = tab.add("CurrentItemX", 0).getEntry();
    private final NetworkTableEntry D_currentItemY = tab.add("CurrentItemY", 0).getEntry();
    private final NetworkTableEntry D_AddedArmX = tab.add("AddedArmX", 0).getEntry();
    private final NetworkTableEntry D_AddedRobotX = tab.add("AddedRobotX", 0).getEntry();
    private final NetworkTableEntry D_useTF = tab.add("useTF", 0).getEntry();
    private double[] defaultValue = new double[1];
    // private final NetworkTableEntry D_T1 = tab.add("T1_full", 0).getEntry();
    // private final NetworkTableEntry D_T2 = tab.add("T2_full", 0).getEntry();
    //private final NetworkTableEntry D_Array = tab.addDoubleArray("array", array1).get;
    public Vision(){

        m_arm.setCameraAngle(280); // Look down
    }

    public double [] getLine(){
      double[] line = new double[3];

      line[0] = (SmartDashboard.getNumber("Bl_X",0));
      line[1] = (SmartDashboard.getNumber("Bl_Y",0));
      line[2] = (SmartDashboard.getNumber("Bl_W",0));
      return line;
    }
    
    public double getJagabee(int xy){
      double[] position = new double[2];

      position[0] = (SmartDashboard.getNumber("JagabeeX",0));
      position[1] = (SmartDashboard.getNumber("JagabeeY",0));
      return position[xy];
    }

    public double getDettol(int xy){
        double[] position = new double[2];

        position[0] = (SmartDashboard.getNumber("DettolX",0));
        position[1] = (SmartDashboard.getNumber("DettolY",0));
        return position[xy];
    }

    public double getCoke(int xy){
        double[] position = new double[2];

        position[0] = (SmartDashboard.getNumber("CokeX",0));
        position[1] = (SmartDashboard.getNumber("CokeY",0));
        return position[xy];
    }

    public double getVerticalLine(){
      return SmartDashboard.getNumber("VerticalLine",0);
    }

    public void getOmega(){
        //Globals.cW = getLine(2);
    }
    
    public double getResolution(int wh){
      double[] dimension = new double[2];

      dimension[0] = 800;//(SmartDashboard.getNumber("imW",0));
      dimension[1] = 600;//(SmartDashboard.getNumber("imH",0));
      return dimension[wh];
    }

    public void setUseTF(){
      SmartDashboard.putBoolean("UseTF", Globals.useTF);
    }

    public double getItemX(int item) {
        /*
        * 1 - Dettol
        * 2 - Jagabee
        * 3 - Coke
        */
        //gets item type to pick and returns item coordinate
        double[] itemCo = new double[3];

        itemCo[0] = getDettol(0);
        itemCo[1] = getJagabee(0);
        itemCo[2] = getCoke(0);
        
        return itemCo[item];
    }

    public double getItemY(int item) {
        /*
        * 0 - Dettol
        * 1 - Jagabee
        * 2 - Coke
        */
        //gets item type to pick and returns item coordinate
        double[] itemCo = new double[3];

        itemCo[0] = getDettol(1);
        itemCo[1] = getJagabee(1);
        itemCo[2] = getCoke(1);
        
        return itemCo[item];
    }
    // gets the number of items in each target area from networktables
    public void getWOBItems(){
      //double[] defaultValue = new double[1];
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

    public void ItemToPick(){
      /*
       * target 1 = one
       * target 2 = two
       * target 3 = three
       */
      // gets the item in the chosen column and assigns it to Globals.curItemType
      int Jag = 0;
      int Dettol = 0;
      int Coke = 0;
      int total = 0;
      for (int i = 0; i < 3; i++){
        if (i==0)
        Jag = Globals.Targets[Globals.curTarget][i];
        else if (i==1)
        Dettol = Globals.Targets[Globals.curTarget][i];
        else if (i==2)
        Coke = Globals.Targets[Globals.curTarget][i];
      }
      total = Jag+Dettol+Coke;
			int[] Task = new int[total];
      // Appends the items into array Task[]
      for (int i = 0; i<Jag; i++) {
        Task[i] = 1;  
      }
      for (int i = Jag; i<(Jag+Dettol); i++) {
        Task[i] = 0;
      }
      for (int i = Jag+Dettol; i<(Coke+Jag+Dettol); i++) {
        Task[i] = 2;
      }
      Globals.curItemType = Task[Globals.Itemcnt]; // assigns current item
      Globals.Itemcnt++;
      if (Globals.Itemcnt >= total) {
				Globals.curTarget++; // changes the target
				Globals.Itemcnt = 0;
			}
    }

    public double[] getObjects(){
      /*
       * 0 - Dettol Count
       * 1,2 - Dettol X,Y
       * 3 - Jagabee Count
       * 4,5 - Jagabee X,Y 
       * 6 - Coke Count
       * 7,8 - Coke X,Y 
       */
     
      double[] objects = (SmartDashboard.getEntry("objects").getDoubleArray(defaultValue));
      
      return objects;
  }
    @Override
    public void periodic()
    {
        //Globals.cW = getLine(2);
        //D_cW.setNumber(Globals.cW);
        //D_targetX.setNumber(getLine(0) - 345);
        // D_currentItem.setNumber(Globals.curItemType);
        // D_currentItemX.setNumber(Globals.curItemX);
        // D_currentItemY.setNumber(Globals.curItemY);
        D_currentItem.setNumber(Globals.curItemType);
        D_currentItemX.setNumber(Globals.curItemX);
        D_currentItemY.setNumber(Globals.curItemY);
        D_AddedRobotX.setNumber(((Globals.curItemX -400) * Globals.convertPxToM));
        D_AddedArmX.setNumber(m_arm.getArmPos().getX() + Globals.camera_offset - (Globals.curItemY - getResolution(1)/2) * Globals.convertPxToM);
        D_useTF.setBoolean(Globals.useTF);
        //D_T1.setBoolean(Globals.target1_full);
        //D_T2.setBoolean(Globals.target2_full);
        D_itemcnt.setNumber(Globals.Itemcnt);
        //double[] defaultValue = new double[1];
        double[] areas = table.getEntry("area").getDoubleArray(defaultValue);
        // D_JagabeeCount.setNumber(getObjects()[0]);
        // D_DettolCount.setNumber(getObjects()[3]);
        // D_CokeCount.setNumber(getObjects()[6]);
        // D_CurrentItem.setNumber(Globals.curItemType);
        // System.out.println(areas[0]);
        // System.out.println(areas[1]);
        // System.out.println(areas[2]);
    }
}
