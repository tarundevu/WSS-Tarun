package frc.robot.commands.auto;


import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Globals;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Vision;
// This command is used to align to the trolley
public class Align2Trolley extends SequentialCommandGroup{
  private final static Arm m_arm = RobotContainer.m_arm;
  private final static Vision m_vision = RobotContainer.m_vision;
  public Align2Trolley(){
    super(
      // Lifts arm
      new DetectionPosition(),
      // sets cvMode to trolley alignment
      new InstantCommand(()-> Globals.cvMode = 5),
      new WaitCommand(4),
      // resets cvMode to idle
      new InstantCommand(()-> Globals.cvMode=-1),
      // Align trolley X
      new TrolleyAlignment(0),
      // Align trolley Y
      new MoveRobotSense(1, 0.32, 0, 0,0.1, ()-> RobotContainer.m_sensor.getIRDistance()<=15),
      new MoveRobot(1, 0.07, 0, 0, 0.1)
    );
  }
}