package frc.robot.commands.auto;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile.Constraints;
import edu.wpi.first.wpilibj.trajectory.constraint.CentripetalAccelerationConstraint;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.RobotContainer;
import frc.robot.Astar.Layout;
import frc.robot.Astar.Node;
import frc.robot.Astar.Tile;
import frc.robot.utils.MyGenerateTrajectory;
import frc.robot.utils.OmniTrackTrajectoryCommand;

/**
 * DriveMotor class
 * <p>
 * This class creates the inline auto command to drive the motor
 */
public class MovetoB extends SequentialCommandGroup
{   
// Waypoints for testing purpose
//   static private List<Translation2d> waypoints = List.of(
//       new Translation2d(0.0, 0.0), // start
//       new Translation2d(0.5, 0.5),
//       new Translation2d(1.0, 0.5),
//       new Translation2d(1.0, 1.0),
//       new Translation2d(0.0, 0.1)

//   );
    private Trajectory m_Trajectory ;
    private List<Translation2d> m_pathWayPoints;
    //Set max velocity, acceleration and centripedal acceleration (turn speed)
    static private final CentripetalAccelerationConstraint m_CurveConstraint = new CentripetalAccelerationConstraint(0.5);
    static private final TrajectoryConfig m_Config = new TrajectoryConfig(0.45, 0.4).addConstraint(m_CurveConstraint).setReversed(false);

    protected Pose2d m_posB;
  
    static MyGenerateTrajectory myGenerateTrajectory = new MyGenerateTrajectory();

    @Override
    public void initialize() {

        //Astar works in cells (tiles)
        //Need to convert from real unit (x,y) position into nearest tile
        //
        Pose2d curPose = RobotContainer.m_omnidrive.getPose();
        int start_x = Layout.Convert_m_cell(curPose.getTranslation().getX());
        int start_y = Layout.Convert_m_cell(curPose.getTranslation().getY());

        //calculate error between real position and the tile center it is mapped to 
        //
        double dx, dy;
        dx = m_posB.getTranslation().getX() - curPose.getTranslation().getX();
        dy = m_posB.getTranslation().getY() - curPose.getTranslation().getY();

        Tile nodeStart, nodeEnd;
        nodeStart = RobotContainer.m_Grid.find(start_x, start_y);
        
        //Improve accuracy by adding rounding error of start_x,y to end_x,y
        //Rounding error occurs when converting from real unit meter to cell unit
        //
        // int end_x = Layout.Convert_m_cell(m_posB.getTranslation().getX());
        // int end_y = Layout.Convert_m_cell(m_posB.getTranslation().getY());        
        //Reduce quantised position error
        int end_x = start_x + Layout.Convert_m_cell(dx);
        int end_y = start_y + Layout.Convert_m_cell(dy);

        nodeEnd = RobotContainer.m_Grid.find(end_x, end_y);

        // Call A* to solve path from point A to point B
        //
        RobotContainer.m_Astar.setStart(nodeEnd);
        RobotContainer.m_Astar.setEnd(nodeStart);

        System.out.printf("start=%d,%d, end=%d,%d dxy=%d,%d dxy=%f,%f\n\n", 
            start_x, start_y, end_x, end_y, Layout.Convert_m_cell(dx), Layout.Convert_m_cell(dy), dx,dy);
        RobotContainer.m_Astar.solve();
        
        // Get the path waypoints
        ArrayList<Node> AstarPathWayPoints = RobotContainer.m_Astar.getPathWayPoints();
        System.out.println(AstarPathWayPoints);

        m_pathWayPoints = new ArrayList<>();

        //Convert from Astar cell waypoints into real unit (meter)
        if (AstarPathWayPoints != null) {
            for (Node n : AstarPathWayPoints) {

                if (n instanceof Tile) {
                    Tile t = (Tile) n;
                    System.out.println(t);
                    //Convert from cell unit to metre
                    Translation2d pt = new Translation2d(t.getX(), t.getY());
                    m_pathWayPoints.add(Layout.Convert_cell_m(pt));
                    System.out.printf("x,y=%d,%d, end=%f,%f\n", t.getX(), t.getY(), Layout.Convert_cell_m(pt).getX(), Layout.Convert_cell_m(pt).getY());
                }
            }
        }
        else {
            //How to handle no proper path situation?
        }

        //generate trajectory based on A* output
        //QuinticHermite works better
        m_Trajectory =
            //myGenerateTrajectory.generateTrajectoryClampedCubic(m_pathWayPoints, m_Config, 0.05);
            myGenerateTrajectory.generateTrajectoryQuinticHermite(m_pathWayPoints, m_Config, 0.1);

        super.initialize();

    }

    public Trajectory getTrajectory() {
        return m_Trajectory;
    }

	public MovetoB(Pose2d posB)
    {
        super (
            //Start with trajectory following
            new WaitCommand(0.4),
            new OmniTrackTrajectoryCommand(
                myGenerateTrajectory::getTrajectory,
                RobotContainer.m_omnidrive::getPose,
                // Position contollers
                new PIDController(0.25, 0, 0),
                new PIDController(0.25, 0, 0),
                new ProfiledPIDController(1, 0, 0, new Constraints(Math.PI, Math.PI) ))
                // RobotContainer.m_omnidrive )

            //End with rotation to the target heading???

        );
        m_posB = posB;

    }
}