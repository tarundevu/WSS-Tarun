package frc.robot.subsystems;

//Java imports

//Vendor imports

import com.studica.frc.Cobra;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
//import edu.wpi.first.wpilibj.Servo;
//import edu.wpi.first.wpilibj.Ultrasonic;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
//WPI imports
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Globals;

public class Sensor extends SubsystemBase
{
    //Creates all necessary hardware interface here for sensors
    //For servo testing also????

    // Sensors
    private final DigitalInput input10;
    private final AnalogInput sharp22;
    private final AnalogInput sharp23;
    private Cobra cobra;
    private double y_value=0;
    //private Ultrasonic sonar;

    // Good for debugging
    // Shuffleboard
    private final ShuffleboardTab tab = Shuffleboard.getTab("Sensors");
    private final NetworkTableEntry D_inputDisp = tab.add("inputDisp", false).getEntry();
    public final NetworkTableEntry D_Cnt = tab.add("Cnt", 0).getEntry();
    private final NetworkTableEntry D_sharp22 = tab.add("IRsensor1", 0).getEntry();
    private final NetworkTableEntry D_sharp23 = tab.add("IRsensor2", 0).getEntry();
    private final NetworkTableEntry D_Cobra_0 = tab.add("cobra 0", 0).getEntry();
    private final NetworkTableEntry D_Cobra_1 = tab.add("cobra 1", 0).getEntry();
    private final NetworkTableEntry D_Cobra_total = tab.add("cobra total", 0).getEntry();

    //Subsystem for sensors
    //This is just an example.
    public Sensor() {
        
        input10 = new DigitalInput(Constants.INPUT1);
        sharp22 = new AnalogInput(0);
        sharp23 = new AnalogInput(1);
        cobra = new Cobra();
        //sonar = new Ultrasonic(Constants.SONIC_TRIGG, Constants.SONIC_ECHO);
        
    }
   
    public Boolean getSwitch() {
        return input10.get();
    }
    /**
     * Call for the raw ADC value
     * <p>
     * 
     * @param channel range 0 - 3 (matches what is on the adc)
     * @return value between 0 and 2047 (11-bit)
     */
    // public int getCobraRawValue(final int channel) {
    //     return cobra.getRawValue(channel);
    // }
    public int getCobraTotal(){
        return (cobra.getRawValue(1)+cobra.getRawValue(2));
    }

    /**
     * Call for the distance measured by the Sharp IR Sensor
     * <p>
     * 
     * @return value between 0 - 100 (valid data range is 8cm - 80cm)
     */
    public double getIRDistance() {
        return (Math.pow(sharp22.getAverageVoltage(), -1.2045)) * 27.726;
        
    }
    public double getIRDistance2() {
        return (Math.pow(sharp23.getAverageVoltage(), -1.2045)) * 27.726;
        
    }
    // public double getUltrasonicDistance(){
        
    //     return sonar.getRangeMM();
    // }
    /**
     * Code that runs once every robot loop
     */
    @Override
    public void periodic()
    {
        //Display on shuffleboard
        //These display is good for debugging but may slow system down.
        //Good to remove unnecessary display during competition
        //Cnt++;
       // D_inputDisp.setBoolean(getSwitch());
        D_sharp22.setNumber(getIRDistance());
        //D_sharp23.setNumber(getIRDistance2());
        // D_Cobra_0.setNumber(getCobraRawValue(0));
        // D_Cobra_1.setNumber(getCobraRawValue(1));
        // D_Cobra_total.setNumber(getCobraTotal());
        //D_sonar.setNumber(getUltrasonicDistance());
       
        
    }
}