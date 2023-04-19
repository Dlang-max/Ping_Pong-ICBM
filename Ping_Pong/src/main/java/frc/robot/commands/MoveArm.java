// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.Elbow;
import frc.robot.subsystems.Shoulder;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class MoveArm extends ParallelCommandGroup {
  /** Creates a new MoveArm. */
  Shoulder shoulder;
  Elbow elbow;
  public MoveArm( Shoulder shoulder, Elbow elbow ) {
    this.shoulder = shoulder;
    this.elbow = elbow; 

    addCommands( new MoveShoulder(shoulder, -90), new MoveElbow(elbow, 90));
    // new MoveShoulder(shoulder, -90), 
    // new MoveElbow(elbow, 9
  }

}
