package frc.robot.Astar;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.geometry.Transform2d;

public class Layout {
    // Dimension of layout in real unit
    public static final int x_size_mm = 2250;
    public static final int y_size_mm = 4500;
    public static final int tile_size_mm = 25;
    public static final float tile_size_meter = tile_size_mm/1000.0f;
    public static final int X_SIZE = Math.round((float)x_size_mm/tile_size_mm)+1; //??
    public static final int Y_SIZE = Math.round((float)y_size_mm/tile_size_mm)+1;
    
    //List all fixed walls in layout here
    public static final int walls_mm[][] = {
        //(x0,y0) (x1,y1) end coordinates of line
        //x0,   y0,    x1,    y1
        //Boundary
        {0,  0,     x_size_mm,  0}, 
        {0,  0,     0,  y_size_mm}, 
        {0,     y_size_mm,  x_size_mm,     y_size_mm   }, 
        {x_size_mm,  y_size_mm,  x_size_mm,     0}, 
        
    };

    //List all fixed rectangular obstacles in layout here
    public static final int obs_mm[][] = {
        //(x0,y0) - centre of box
        //x0   y0    xSize ySize Angle
        {1400, 1100,  300,  420, 0 },   //Pick Up bin 1
        {1380, 3000,  300,  420, 45},   //Pick Up bin 2
        // {1140, 4390,  320, 230,  0 }, 
        // {1957, 2038-425,  150,  210,  0 }, 
       
    };

    //Coordinates of PickUp bin
    public static final int PickUpBinPos[] = {680,1100, -90};
    public static final int PickUpBin2Pos[] = {920,3470, -135};
    //These are coordinates of the red colored target area (NOTE: indicate the angle of orientation)
    public static final int RedPos[] = {2000,4320, -45};
  
    //These are coordinates of the green colored target area
    public static final int GreenPos[] = {175,2660, 90};

    //These are coordinates of the blue colored target area
    public static final int BluePos[] = {940,1850, 90};//{1000,1800, -90};

    //These are coordinates of the trolleys (NOTE: indicate the angle of orientation) 
    public static final int T1Pos[] = {500,1600, 90};
  
    public static final int T2Pos[] = {2000,1850,-90};

    public static final int T3Pos[] = {150, 4350, 45};

    // Position for robot to go to for reading work order
    public static final int workOrderPos[] = {1100, 250, -90};//1100

    // Robot start position.
    public static final int startPos[] = {210, 210, -90}; //start position
    // public static final int startPos[] = {960, 1100, -90}; //For open house

    private int walls[][];
    private int obs[][];

    public Layout() {
        int i, j;
        
        //Convert walls in mm to walls in cell size
        walls = new int[walls_mm.length][4];
        for (i=0; i< walls_mm.length; i++) {
            for (j=0; j<4; j++) {
                walls[i][j] = Math.round((float)walls_mm[i][j]/tile_size_mm);
            }
        }

        //Convert obstacles in mm to obstacles in cell size
        obs = new int[obs_mm.length][5];
        for (i=0; i< obs_mm.length; i++) {
            for (j=0; j<4; j++) {
                obs[i][j] = Math.round((float)obs_mm[i][j]/tile_size_mm);
            }
            obs[i][4] = obs_mm[i][4];  //Angle in degree stays the same
        }

    }

    static public Translation2d Convert_cell_m(Translation2d pt) {
        Translation2d pt_m = new Translation2d(pt.getX()*tile_size_meter, pt.getY()*tile_size_meter);
        return pt_m;
    }

    static public int Convert_m_cell (double m) {
        return Math.round((float)m*1000/tile_size_mm);
    }
    
    static public Pose2d Convert_mm_Pose2d (int pos[]) {
        return new Pose2d(pos[0]/1000.0f, pos[1]/1000.0f, new Rotation2d(pos[2]*Math.PI/180));
    }

    public int [][] getWalls() {
        return walls;
    }

    public int [][] getObs() {
        return obs;
    }
}