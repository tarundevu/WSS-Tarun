package frc.robot.commands.auto;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Globals;

public class PerspTfCamPos extends SequentialCommandGroup{
    public PerspTfCamPos() {
        super(
            new MoveArm(new Translation2d(0.3,0.4), 2),
            new MoveCamera(Globals.PerspTfCamAngle)
        );
    }
}