package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Globals;
import frc.robot.RobotContainer;

public class Vision extends SubsystemBase{
    private final ShuffleboardTab tab = Shuffleboard.getTab("Vision");
    private final NetworkTableEntry D_cW = tab.add("cW", 0).getEntry();
    private final NetworkTableEntry D_targetX = tab.add("TargetX", 0).getEntry();
    private final NetworkTableEntry D_itemcnt = tab.add("itemcnt1", 0).getEntry();
    public final NetworkTableEntry D_targetXArm = tab.add("targetXArm", 0).getEntry();
    private final static Arm m_arm = RobotContainer.m_arm;
    private final String[] items = {
      "Dettol",
      "Jagabee",
      "Coke"
    };
    private final NetworkTableEntry D_currentItem = tab.add("CurrentItem", 0).getEntry();
    private final NetworkTableEntry D_AddedArmX = tab.add("AddedArmX", 0).getEntry();
    private final NetworkTableEntry D_AddedRobotX = tab.add("AddedRobotX", 0).getEntry();
    private final NetworkTableEntry D_useTF = tab.add("useTF", 0).getEntry();
    private final NetworkTableEntry D_T1 = tab.add("T1_full", 0).getEntry();
    private final NetworkTableEntry D_T2 = tab.add("T2_full", 0).getEntry();
    public Vision(){

        m_arm.setServoAngle3(280); // Look down
        //m_arm.setArmPos(0.328, 0.25);
        //new WaitCommand(1);
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

    public void TargetItem(){
      /*
       * target 1 = one
       * target 2 = two
       * target 3 = three
       */
      // gets the item in the chosen column and assigns it to Globals.curItem
      if (Globals.target1_full != true){
        if (Globals.Targets[Globals.Itemcnt][0] != 8){
          Globals.curItem = Globals.Targets[Globals.Itemcnt][0];
          Globals.Itemcnt ++;
        }
        else{
          Globals.target1_full = true;
        }
      }

      else if (Globals.target1_full == true && Globals.target2_full != true){
        if (Globals.Targets[Globals.Itemcnt2][1] != 8){
          Globals.curItem = Globals.Targets[Globals.Itemcnt2][0];
          Globals.Itemcnt2 ++;
        }
        else{
          Globals.target2_full = true;
        }
      }

      else if (Globals.target2_full == true && Globals.target3_full != true){
        if (Globals.Targets[Globals.Itemcnt3][2] != 8){
          Globals.curItem = Globals.Targets[Globals.Itemcnt3][0];
          Globals.Itemcnt3 ++;
        }
        else{
          Globals.target3_full = true;
        }
      }
      
    }
    

    @Override
    public void periodic()
    {
        //Globals.cW = getLine(2);
        //D_cW.setNumber(Globals.cW);
        //D_targetX.setNumber(getLine(0) - 345);
        D_currentItem.setString(items[Globals.curItem]);
        D_AddedRobotX.setNumber(((getItemX(Globals.curItem) -400) * Globals.convertPxToM));
        D_AddedArmX.setNumber(-(getItemY(Globals.curItem) - 300) * Globals.convertPxToM + 0.012);
        D_useTF.setBoolean(Globals.useTF);
        //D_T1.setBoolean(Globals.target1_full);
        //D_T2.setBoolean(Globals.target2_full);
        D_itemcnt.setNumber(Globals.Itemcnt);
    }
}
