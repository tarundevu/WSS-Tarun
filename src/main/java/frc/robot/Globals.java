package frc.robot;

//Put all global variables here
public class Globals
{
    static public int menuItem;

    static public final int DNUM = 4;
    static public int debug[] = new int[DNUM];
    static public String[] debugNames = new String[] {"debug0", "debug1", "debug2", "debug3"};
    static public int loopCnt = 0;
    public static double baseOffsetX = -0.21;
    public static double baseOffsetY = 0.415;
    public static double desOffsetX = 0.2;
    public static double desOffsetY = 0.2;
    public static double[] cW = RobotContainer.m_vision.getLine();
    public static double convertPxToM = 0.000625; // 0.56/800 , 0.00058 good
    public static double cameraAngle = 290;
    public static double armDefaultX = 0.20;
    public static double armDefaultY = 0.09;
    public static double[] itemOffsets = {0.01,0.003,0.015};
    public static double targetXArmPick;
    public static double[] yellowBinDimension = {0.415,0.295};
    public static double frontIROffset = 0.215;
    public static double camera_offset = 0.12;
    public static double camera_mount_offset = 0.015;
    public static double arm_offset_y = 0.13; // 0.125
    public static double arm_offset_z = 0.25;
    public static double gripper_offset = 0.16;
    public static int curItemType = 0;
    public static double curItemX;
    public static double curItemY;
    public static int curTarget = 0;
    public static boolean useTF;
    public static int Itemcnt = 0;
    public static boolean target1_full = false;
    public static boolean target2_full = false;
    public static boolean target3_full = false;
    /*
	 *                                              J|D|C              
	 *                                            R|x|x|x|
	 *                                            G|x|x|X|
	 *                                            B|x|x|x|
	 */
    public static int[][] Targets = new int[][] { 
                                                }; 
}