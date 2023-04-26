package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.Elbow;
import frc.robot.subsystems.Shoulder;


public class MoveArm extends ParallelCommandGroup {
  Shoulder shoulder;
  Elbow elbow;
  public MoveArm( Shoulder shoulder, Elbow elbow ) {
    this.shoulder = shoulder;
    this.elbow = elbow; 

    addCommands( new MoveShoulder(shoulder, -45));
  
  }

}
