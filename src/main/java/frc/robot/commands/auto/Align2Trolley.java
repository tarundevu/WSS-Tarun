package frc.robot.commands.auto;


import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Globals;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Vision;

public class Align2Trolley extends SequentialCommandGroup{
  private final static Arm m_arm = RobotContainer.m_arm;
  private final static Vision m_vision = RobotContainer.m_vision;
  public Align2Trolley(){
    super(
      // Lifts arm
      new MoveArm(new Translation2d(0.33,0.3), 0.5),
      new DetectionPosition(),
      // Align to trolley
      // new InstantCommand(()-> Globals.cvMode = 0),
      // new AlignRobot("trolley"),
      // sets cvMode to trolley alignment
      new InstantCommand(()-> Globals.cvMode = 5),
      new WaitCommand(2),
      // resets cvMode to idle
      new InstantCommand(()-> Globals.cvMode=-1),
      // Align trolley X
      new TrolleyAlignment(0),
      // Align trolley Y
      // new TrolleyAlignment(1)
      // new InstantCommand(()-> RobotContainer.m_sensor.setIRDist()),
      // new MoveRobot(1, Globals.IRdist, 0, 0, 0.25)
      new MoveRobotSense(1, 0.3, 0, 0,0.1, ()-> RobotContainer.m_sensor.getIRDistance()<=15),
      new MoveRobot(1, 0.07, 0, 0, 0.1)
      // new WaitCommand(2),
      // new MoveRobot(1, 0.03, 0, 0, 0.1)
    );
  }
}