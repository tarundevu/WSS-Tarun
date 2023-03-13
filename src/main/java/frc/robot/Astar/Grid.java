package frc.robot.Astar;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;

public class Grid extends Network{

    private int xSize, ySize;
    private ArrayList<Tile> tiles;

    public Grid(Layout layout) {
        xSize = Layout.X_SIZE;
        ySize = Layout.Y_SIZE;

        tiles = new ArrayList<>();
        //Add individual cell
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                Tile t = new Tile(i, j);
                tiles.add(t);
            }
        }
        System.out.println(xSize);
        System.out.println(ySize);
        System.out.println(tiles.size());
        
        //Pre assign neighbours
        for (Tile t : getTiles()) {
            t.calculateNeighbours(this, true);
        }

    }

        /**
     * Add fixed obstacles
     * @param none
     */

    public void AddFixedObstacles(Layout layout) {
        //Add fixed walls
        int[][] walls = layout.getWalls();
        for(int i=0; i< walls.length; i++) {
            AddWall(walls[i][0], walls[i][1], walls[i][2], walls[i][3]);
        }

        //Add fixed obstacles
        int[][] obs = layout.getObs();
        for(int i=0; i< obs.length; i++) {
            AddObstacle(obs[i][0], obs[i][1], obs[i][2], obs[i][3], obs[i][4]*Math.PI/180);
        }
        
        int[][] obsRound = layout.getObsRound();
        for(int i=0; i< obsRound.length; i++) {
            AddObsRound(obsRound[i][0], obsRound[i][1], obsRound[i][2]);
        }
     }
     /**
   * Expand obstacle to push robot away. Robot can still go through this expanded cell if necessary
   *
   * @param robotRadius_mm robot radius in mm
   */
    public void ExpandObstacles(float robotRadius_mm) {

        //Expand obstacle by robot radius. These are no entry zones
        int robotRadius = Math.round((float)robotRadius_mm/Layout.tile_size_mm);

        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {
                Node current = find(x, y);
                if (current.getObsValue() == Node.maxObsValue) {
                    boolean edgeFlag = false;
                    for (Node n : current.getNeighbours()) {
                        // temporary value
                        if (n.getObsValue() != Node.maxObsValue) {
                            edgeFlag = true;
                            break;
                        }
                    }
                    // expand obstacle only for cell on the edge of obstacle
                    if (edgeFlag == true) {
                        for (int kx=-robotRadius; kx<=robotRadius; kx++) {
                            for (int ky=-robotRadius; ky<=robotRadius; ky++) {
                                double dist = Math.sqrt(kx*kx + ky*ky);
                                if (dist<=robotRadius) {
                                    int ox = x + kx;
                                    int oy = y + ky;
                                    Node obsCell = find(ox, oy);
                                    if (obsCell != null && obsCell.getObsValue()!=Node.maxObsValue) {
                                        obsCell.setObsValue(Node.maxObsValue-1);

                                    }

                                }

                            }
                        }

                    }
                }
            }
        }


        //Expand obstacle by path cost. These are high cost cell to force robot away from obstacle
        //It is possible for robot to enter these cells (tiles).
        //Number of cells to expand and their values are defined here
        double keepOutDist_mm = 250;      
        int numOfCells = Math.round((float)keepOutDist_mm/Layout.tile_size_mm);  
        double expansion[] = new double[numOfCells];
        double factor = 0.75;//Math.exp(Math.log(0.1f)/numOfCells);
        double cost = Node.maxObsValue*factor;
        for (int i=0; i<numOfCells; i++) { 
            expansion[i] = cost;
            cost *= factor;
        }


        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {

                Node current = find(x, y);
                if (current.getObsValue() == (Node.maxObsValue-1)) {
                    boolean edgeFlag = false;
                    for (Node n : current.getNeighbours()) {
                        // temporary value
                        if (n.getObsValue() < (Node.maxObsValue-1)) {
                            edgeFlag = true;
                            break;
                        }
                    }
                    // expand obstacle only for cell on the edge of obstacle
                    if (edgeFlag == true) {
                        for (int kx=-numOfCells; kx<=numOfCells; kx++) {
                            for (int ky=-numOfCells; ky<=numOfCells; ky++) {
                                int dist = Math.round((float)Math.sqrt(kx*kx + ky*ky));
                                if (dist<numOfCells) {
                                    int ox = x + kx;
                                    int oy = y + ky;
                                    Node obsCell = find(ox, oy);
                                    if (obsCell != null && obsCell.getObsValue()<expansion[dist]) {
                                        obsCell.setObsValue(expansion[dist]);

                                    }

                                }

                            }
                        }

                    }
                }
            }
        }

    }


    /**
   * Add rectangular shape obstacle to field.
   *
   * @param x0 centre X pos
   * @param y0 centre Y pos
   * @param xSize X size of rect
   * @param ySize Y size of rect
   * @param angle orientation of rectangle
   */
    public void AddObstacle(int x0, int y0, int xSize, int ySize, double angle) {
        for (int x=-xSize/2; x<xSize/2; x++) {
            for (int y=-ySize/2; y<ySize/2; y++) {
                int xx = (int)Math.round(x*Math.cos(angle) - y*Math.sin(angle));
                int yy = (int)Math.round(x*Math.sin(angle) + y*Math.cos(angle));
                Tile t = find(xx+x0,yy+y0);
                if (t!=null)
                    t.setObsValue(Node.maxObsValue);
            }
        }
    }

    public void AddObsRound(int x0, int y0, int diameter) {
        double radius = diameter/2;
        int numOfCells = diameter/2;  
        System.out.printf("cell = %d\n", numOfCells);
        for (int x=-diameter/2; x<diameter/2; x++) {
            for (int y=-diameter/2; y<diameter/2; y++) {
                int dist = Math.round((float)Math.sqrt(x*x + y*y));
                if (dist<numOfCells) {
                    Tile t = find(x+x0,y+y0);
                    if (t!=null)
                        t.setObsValue(Node.maxObsValue);
                }
            }
        }
    }

    /**
     * function ClearObs() - clear all obstacle to redraw
     **/
    public void ClearObs() {
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                Node n = find(i, j);
                n.setObsValue(0);
            }
        }
    }
    /**
     * function findLine() - to find that belong to line connecting the two points
     **/
    public void AddWall(int x0, int y0, int x1, int y1) {
  
        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);

        int sx = x0 < x1 ? 1 : -1;
        int sy = y0 < y1 ? 1 : -1;

        int err = dx - dy;
        int e2;

        while (true) {
            Tile t = find(x0,y0);
            if (t!=null)
                t.setObsValue(Node.maxObsValue);

            if (x0 == x1 && y0 == y1)
                break;

            e2 = 2 * err;
            if (e2 > -dy) {
                err = err - dy;
                x0 = x0 + sx;
            }

            if (e2 < dx) {
                err = err + dx;
                y0 = y0 + sy;
            }
        }
    }

    public int getxSize() {
        return xSize;
    }

    public int getySize() {
        return ySize;
    }

    public ArrayList<Tile> getTiles() {
        return tiles;
    }
    
    public Tile find(int x, int y){
        // for(Tile t : tiles){
        //     if(t.getX() == x && t.getY() == y)
        //         return t;
        // }
        // return null;
        if (x>=0 && x<xSize && y>=0 && y<ySize)
            return tiles.get(x*ySize+y);
        else return null;
    }
    double[] angle =  new double[] {0, Math.PI/4, Math.PI, Math.PI*3/4, Math.PI, -Math.PI*3/4, -Math.PI/2, -Math.PI/4 };
    /**
   * Find free position for robot to goto an object
   * The function will search all the space from the 8 NSEW directions
   * @param x object centre X pos (in m)
   * @param y object centre Y pos (in m)
   * @param dist dist of robot pos from object (in m)
   */
    public Pose2d findGotoPos(double x, double y, double dist){
        Tile t;
        Pose2d[] pos = new Pose2d[8];
        double[] cost = new double[8];

        double lowestCost = Node.maxObsValue;
        int lowestIdx = 0;
        // System.out.println(":::::::::::::::::::");
        // System.out.printf("x,y=%f, %f\n", x, y);
        for(int i=0; i<8; i++) {
            double pos_x = x + dist*Math.cos(angle[i]);
            double pos_y = y + dist*Math.sin(angle[i]);
            int grid_x = Layout.Convert_m_cell(pos_x);
            int grid_y = Layout.Convert_m_cell(pos_y);

            t = find(grid_x, grid_y);
            if ( t != null) {
                cost[i] = t.getObsValue();
                // System.out.printf("x,y=%d,%d a=%f cost=%f\n", grid_x, grid_y, angle[i], cost[i]);
            }
            else {
                cost[i] = Node.maxObsValue;
            }
            pos[i] = new Pose2d(pos_x, pos_y, new Rotation2d(angle[i]));
            if (lowestCost>cost[i]) {
                lowestCost = cost[i];
                lowestIdx = i;
                // System.out.println(pos[i]);
                // System.out.printf("xl,yl=%d,%d cost=%f\n", grid_x, grid_y, lowestCost);
            }

        }

        // Find best position. It should be based on lowest cost. If there are more than 1 lowest cost,
        // the chose the nearest point.
        // For now, use the lowest first find.
        return pos[lowestIdx];
    }

    @Override
    public Iterable<Node> getNodes() {
        ArrayList<Node> nodes = new ArrayList<>();
        nodes.addAll(tiles);
        return nodes;
    }
}