package frc.robot.commands.auto;
import frc.robot.commands.auto.MoveRobot;


/**
 * SimpleDrive class
 * <p>
 * This class drives a motor 
 */
public class MoveRobotSense extends MoveRobot
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
     * @param dist - distance to move
     * @param startSpeed -  starting speed of robot
     * @param endSpeed - ending speed og robot
     * @param maxSpeed - max speed of robot
     * @param f - function that defines early end condition
     */
    public MoveRobotSense(int type, double dist, double startSpeed, double endSpeed, double maxSpeed, end_func f)
    {
        super(type, dist, startSpeed, endSpeed, maxSpeed );
        f_ptr = f;
    }

    @Override
    public boolean endCondition()
    {
        return f_ptr.endCondition();
    }
}