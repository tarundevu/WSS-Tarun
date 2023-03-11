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
import frc.robot.commands.auto.CP1;
import frc.robot.commands.auto.CP2;
import frc.robot.commands.auto.CP3;
import frc.robot.commands.auto.CP4;
import frc.robot.commands.auto.CP5;
import frc.robot.commands.auto.CP6;
import frc.robot.commands.auto.CP7;
import frc.robot.commands.auto.MoveBack;
import frc.robot.commands.auto.MoveCurve;
import frc.robot.commands.auto.MoveLeft;
import frc.robot.commands.auto.MoveRight;
import frc.robot.commands.auto.MoveTest;
import frc.robot.commands.auto.TestColor;
import frc.robot.commands.auto.TestLine;
import frc.robot.commands.auto.TestMotionRot;
import frc.robot.commands.auto.TestMotionX;
import frc.robot.commands.auto.TestMotionY;
import frc.robot.commands.auto.TestPicking;
import frc.robot.commands.gamepad.OI;

public class Menu extends SubsystemBase
{

    private final OI m_oi;

    // Shuffleboard
    private final ShuffleboardTab tab = Shuffleboard.getTab("Menu");
    private final NetworkTableEntry D_button = tab.add("button", -1).getEntry();
    private final NetworkTableEntry D_menu = tab.add("menu", "?").getEntry();
    private NetworkTableEntry D_debug[] = new NetworkTableEntry[Globals.DNUM];
    //tab.add("Motion", TestMotion);

   
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
                    Map.entry(menuNum++, new CP1()),
                    Map.entry(menuNum++, new CP2()),
                    Map.entry(menuNum++, new CP3()),
                    Map.entry(menuNum++, new CP4()),                    
                    Map.entry(menuNum++, new CP5("RedTarget")),
                    Map.entry(menuNum++, new CP5("GreenTarget")),  
                    Map.entry(menuNum++, new CP5("BlueTarget")),                
                    Map.entry(menuNum++, new CP6()),
                    Map.entry(menuNum++, new CP7())
                ), ()->Globals.menuItem
            ) 
        );
        menuName = new String[] {
            "core1",
            "core2",
            "core3",
            "core4",
            "core5",
            "core6",
            "core7"
        };
        
        tab.add("TestMotionX", new TestMotionX());
        tab.add("TestMotionY", new TestMotionY());
        tab.add("TestMotionRot", new TestMotionRot());
        tab.add("TestPicking", new TestPicking());
        tab.add("TestLine", new TestLine());
        tab.add("TestColor", new TestColor());
        
        //A-up button, Y-down button
        m_oi.buttonA.whenPressed( ()->{Globals.menuItem--;Globals.menuItem=(Globals.menuItem+menuNum)%menuNum;});
        m_oi.buttonY.whenPressed( ()->{Globals.menuItem++;Globals.menuItem%=menuNum;});
    }
    
   
    @Override
    public void periodic()
    {
      
        D_menu.setString( menuName[Globals.menuItem]);
        D_button.setNumber(m_oi.getDriveButtons());
        for (int i=0; i<Globals.DNUM; i++) {
            D_debug[i].setNumber(Globals.debug[i]);
        }
       
    }
}