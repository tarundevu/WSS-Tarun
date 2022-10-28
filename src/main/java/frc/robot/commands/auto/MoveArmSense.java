
package frc.robot.commands.auto;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import frc.robot.commands.auto.MoveArm;


/**
 * SimpleDrive class
 * <p>
 * This class drives a motor 
 */
public class MoveArmSense extends MoveArm
{
    private final end_func f_ptr;
    interface end_func {
        public boolean endCondition();
    }
    
    /**
     * This command moves the robot a certain distance following a trapezoidal speed profile 
     * or terminate early when condition is met.
     * <p>
     * 
     * @param type - 0, 1 or 2 for x, y, or w speed
     * @param maxSpeed - max speed of robot
     * @param f - function that defines early end condition
     */
    public MoveArmSense(Translation2d pos,double maxspeed, end_func f)
    {
    
        super(pos, maxspeed);
        f_ptr = f;
    }

    @Override
    public boolean endCondition()
    {
        return f_ptr.endCondition();
    }
}