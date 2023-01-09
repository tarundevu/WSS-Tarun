package frc.robot.subsystems;

import java.util.Map;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SelectCommand;
//WPI imports
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Globals;
import frc.robot.commands.auto.CP4;
import frc.robot.commands.auto.MoveBack;
import frc.robot.commands.auto.MoveCurve;
import frc.robot.commands.auto.MoveLeft;
import frc.robot.commands.auto.MoveRight;
import frc.robot.commands.auto.MoveTest;
import frc.robot.commands.gamepad.OI;

public class Menu extends SubsystemBase
{

    private final OI m_oi;

    // Shuffleboard
    private final ShuffleboardTab tab = Shuffleboard.getTab("Menu");
    private final NetworkTableEntry D_button = tab.add("button", -1).getEntry();
    private final NetworkTableEntry D_menu = tab.add("menu", "?").getEntry();
    private NetworkTableEntry D_debug[] = new NetworkTableEntry[Globals.DNUM];
    private final NetworkTableEntry D_CP1 = tab.add("CP1", false).getEntry();
    private final NetworkTableEntry D_CP2 = tab.add("CP2", false).getEntry();
    private final NetworkTableEntry D_CP3 = tab.add("CP3", false).getEntry();
    private final NetworkTableEntry D_CP4 = tab.add("CP4", false).getEntry();
    private final NetworkTableEntry D_CP6 = tab.add("CP6", false).getEntry();
   
    int menuNum=0;
    private final String[] menuName;

    public Menu(final OI oi) {

        for (int i=0; i<Globals.DNUM; i++) {
            D_debug[i] = tab.add(Globals.debugNames[i], -1).getEntry();
        }
        m_oi = oi;
        m_oi.buttonStart.whenPressed(             
            new SelectCommand(
                Map.ofEntries(
                    Map.entry(menuNum++, new MoveLeft()),
                    Map.entry(menuNum++, new MoveRight()),
                    Map.entry(menuNum++, new MoveBack()),
                    Map.entry(menuNum++, new MoveTest()),
                    Map.entry(menuNum++, new MoveCurve()) 
                ), ()->Globals.menuItem
            ) 
        );
        menuName = new String[] {
            "task0",
            "task1",
            "task2",
            "task3",
            "task4" 
        };

        //A-up button, Y-down button
        m_oi.buttonA.whenPressed( ()->{Globals.menuItem--;Globals.menuItem=(Globals.menuItem+menuNum)%menuNum;});
        m_oi.buttonY.whenPressed( ()->{Globals.menuItem++;Globals.menuItem%=menuNum;});
    }
    
    public boolean getCP1(){
        return D_CP1.getBoolean(false);
    }

    public boolean getCP2(){
        return D_CP2.getBoolean(false);
    }

    public boolean getCP3(){
        return D_CP3.getBoolean(false);
    }

    public boolean getCP4(){
        return D_CP4.getBoolean(false);
    }

    public boolean getCP6(){
        return D_CP6.getBoolean(false);
    }

    @Override
    public void periodic()
    {
      
        D_menu.setString( menuName[Globals.menuItem]);
        D_button.setNumber(m_oi.getDriveButtons());
        for (int i=0; i<Globals.DNUM; i++) {
            D_debug[i].setNumber(Globals.debug[i]);
        }
        D_CP1.setBoolean(getCP1());
        D_CP2.setBoolean(getCP2());
        D_CP3.setBoolean(getCP3());
        D_CP4.setBoolean(getCP4());
        D_CP6.setBoolean(getCP6());
    }
}