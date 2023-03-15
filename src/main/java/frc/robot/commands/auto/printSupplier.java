package frc.robot.commands.auto;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class printSupplier extends SequentialCommandGroup{
  public printSupplier(Supplier<String> targetName){
    super(new InstantCommand(()-> System.out.println(targetName.get())));
    
  }
}
