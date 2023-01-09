package frc.robot.commands.auto;

import java.nio.channels.InterruptibleChannel;

import edu.wpi.first.wpilibj2.command.CommandBase;
//import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.auto.MoveRobotSense.end_func;

/**
 * DriveMotor class
 * <p>
 * This class creates the inline auto command to drive the motor
 */
public class LoopCmd extends CommandBase {
    // private final static OmniDrive m_drive = RobotContainer.m_omnidrive;
    public static boolean cmdEndFlag;
    private int state;
    private boolean scheduleFlag;
    protected boolean m_endFlag;
    protected SequentialCommandGroup cmd;
    protected final end_func fn_ptr;

    interface end_func {
        public boolean endCondition();
    }

    public LoopCmd(SequentialCommandGroup cmdToRun, end_func fn) {
        cmd = cmdToRun;
        fn_ptr = fn;
        // addRequirements(m_drive);
    }

    public LoopCmd(SequentialCommandGroup cmdToRun) {
        cmd = cmdToRun;
        fn_ptr = null;
        
    }
    @Override
    public void initialize() {
        state = 0;
        scheduleFlag = true;
        m_endFlag = false;
        cmdEndFlag = false;
        // Globals.debug[0]=Globals.debug[1]=Globals.debug[2]=0;
    }

    @Override
    public void execute() {
        if (cmd.isScheduled() == false) {
            // launch command group
            // CommandScheduler.getInstance().schedule(cmd);
            if (fn_ptr.endCondition()) {
                m_endFlag = true;
            }

            else {
                cmd.schedule(false);
            }

        }
        // Globals.debug[0] = state;
    }

    @Override
    public boolean isFinished() {
        // if (m_endFlag)
        // Globals.debug[2] = -1;
        return m_endFlag;
    }

    @Override
    public void end(boolean interrupted) {
        // Globals.debug[1] = -1;
    }
}