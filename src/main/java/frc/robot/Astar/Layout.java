package frc.robot.Astar;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.geometry.Transform2d;

public class Layout {
    // Dimension of layout in real unit
    public static final double x_size_m = 2.250;
    public static final double y_size_m = 4.500;
    public static final double tile_size_m = 0.025;
    
    //List all fixed walls in layout here
    //List all fixed walls in layout here
    public static final double walls_m[][] = {
        //Boundary
        {0,         0,         x_size_m,     0   }, 
        {x_size_m,  0,         x_size_m,     y_size_m}, 
        {x_size_m,  y_size_m,  0,            y_size_m}, 
        {0,         y_size_m,  0,            0   }, 
        // Other walls

   
    };

    //List all fixed rectangular obstacles in layout here
    public static final double obs_m[][] = {
        //x0    y0      xSize   ySize   Angle
        {1.400, 1.100,  0.300,  0.420,  0 },   //pickup bin
        {1.380, 3.000,  0.300,  0.420,  -Math.PI/4 },   //pickup bin
    };
    public static final double obsRound_m[][] = {
        //x,    y,      diameter
        // {2.100, 4.310,  0.300}, //red
        // {0.200, 4.370 , 0.300}, //green
        // {1.100, 4.330,  0.300}  //blue
        // {0.980,  3.910,  0.300} //red
        // {0.290,  4.370,  0.300}, //green
        // {1.860,  3.830,  0.300}  //blue
      
    };

    //Coordinates of PickUp bin
    //72cm behind pickup bin
    // At 45 deg, it's 0.707*72cm = 50.9cm

    public static final Pose2d TestPickUpBinPos = new Pose2d(0.63, 1.05,  new Rotation2d(-Math.PI/2) );
    public static final Pose2d PickUpBinPos = new Pose2d(1.4-0.72, 1.1,  new Rotation2d(-Math.PI/2) );
    public static final Pose2d PickUpBin2Pos = new Pose2d(1.38-0.509, 3.0+0.509,  new Rotation2d(-Math.PI*3/4) );
    //These are coordinates of the red colored target area (NOTE: indicate the angle of orientation)
    public static final Pose2d RedPos = new Pose2d(2, 4.32,  new Rotation2d(Math.toRadians(-45)));
  
    //These are coordinates of the green colored target area
    public static final Pose2d GreenPos = new Pose2d(0.175, 2.66,  new Rotation2d(Math.toRadians(90)));

    //These are coordinates of the blue colored target area
    public static final Pose2d BluePos = new Pose2d(0.940, 1.85,  new Rotation2d(Math.toRadians(90)));

    //These are coordinates of the trolleys (NOTE: indicate the angle of orientation) 
    public static final Pose2d T1Pos = new Pose2d(0.5, 1.6,  new Rotation2d(Math.toRadians(90)));
  
    public static final Pose2d T2Pos = new Pose2d(2, 1.85,  new Rotation2d(Math.toRadians(-90)));

    public static final Pose2d T3Pos = new Pose2d(0.150, 4.35,  new Rotation2d(Math.toRadians(45)));

    // Position for robot to go to for reading work order
    public static final Pose2d workOrderPos = new Pose2d(1.1, 0.250,  new Rotation2d(Math.toRadians(-90)));

    // Robot start position.
    public static final Pose2d startPos = new Pose2d(0.210, 0.210, new Rotation2d(-Math.PI/2) );
    // public static final Pose2d startPos = new Pose2d(0.96,1.1, new Rotation2d(-Math.PI/2) ); //For open house/testing

     public Layout() {
       
      
    }

     /**
   * Convert a point in cell to m
   *
   * @param pt (int metre)
   * @return pt (in cell size)
   */
    static public Translation2d Convert_cell_m(Translation2d pt) {
    Translation2d pt_m = new Translation2d(pt.getX()*tile_size_m, pt.getY()*tile_size_m);
    return pt_m;
    }

    /**
    * Convert from metre to grid cell index
    *
    * @param m length in metre
    * @return Grid cell index
    */
    static public int Convert_m_cell (double m) {
        return (int) Math.round(m/tile_size_m);
    }

    public double [][] getWalls() {
        return walls_m;
    }

    public double [][] getObs() {
        return obs_m;
    }

    public double [][] getObsRound() {
        return obsRound_m;
    }
}