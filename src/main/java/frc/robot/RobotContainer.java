/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;


import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Astar.AStarAlgorithm;
import frc.robot.Astar.Grid;
import frc.robot.Astar.Layout;
import frc.robot.commands.TeleCmd;
import frc.robot.commands.auto.AutoMainCmd;
import frc.robot.commands.gamepad.OI;
import frc.robot.subsystems.Menu;
import frc.robot.subsystems.OmniDrive;
import frc.robot.subsystems.Sensor;
import frc.robot.subsystems.Vision;
import frc.robot.utils.OmniDriveOdometry;
import frc.robot.subsystems.Arm;

public class RobotContainer {

  //subsystems
  public final static OI m_oi = new OI();
  public final static OmniDrive m_omnidrive = new OmniDrive();
  public final static Sensor m_sensor = new Sensor(); 
  public final static Arm m_arm = new Arm();
  public final static Vision m_vision = new Vision();
  public final static Points m_points = new Points();
  //user menu
  public final static Menu m_menu = new Menu(m_oi);
  //commands
  public final static TeleCmd m_teleCmd = new TeleCmd(m_omnidrive, m_oi, m_arm);
  public static AutoMainCmd m_autoCmd;
  //Create grid
  public static Layout m_layout;
  public static Grid m_Grid;
  public static AStarAlgorithm m_Astar;
  public static OmniDriveOdometry m_od;

  public RobotContainer()
  {
      //Create new instances

      //Set the default command for the hardware subsytem
      //m_omnidrive.setDefaultCommand(m_teleCmd);
    InitMap();
  }

  public static void InitMap() {
    m_layout = new Layout();
    m_Grid = new Grid(m_layout);
    m_Grid.AddFixedObstacles(m_layout);
    m_Grid.ExpandObstacles(Globals.robotRadius_mm);

    // Create solver
    m_Astar = new AStarAlgorithm(m_Grid);
    m_autoCmd = new AutoMainCmd();
  }
  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_autoCmd;
  }
  public Command getTeleopCommand() {
    // An ExampleCommand will run in autonomous
    return m_teleCmd;
  }
}
