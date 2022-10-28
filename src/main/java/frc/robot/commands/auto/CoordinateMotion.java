package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.geometry.Translation2d;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class CoordinateMotion extends SequentialCommandGroup {
  private double X;
  private double Y;
  private double Z;

  public CoordinateMotion(Translation2d coord){
    
    super(
      new MoveRobot(0,coord.getX(), 0, 0, 0.4),
      new MoveRobot(1,coord.getY(), 0, 0, 0.4)
    );
  }
}