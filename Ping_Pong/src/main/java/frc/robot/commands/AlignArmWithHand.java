// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.Elbow;
import frc.robot.subsystems.Shoulder;
import frc.robot.subsystems.Slide;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class AlignArmWithHand extends ParallelCommandGroup {
  Elbow elbow;
  Shoulder shoulder;
  Slide slide; 
  public AlignArmWithHand(Elbow elbow, Shoulder shoulder, Slide slide) {
    this.elbow = elbow;
    this.shoulder = shoulder;
    this.slide = slide; 

    addCommands( new MoveShoulder(shoulder, -45), new AlignElbowAndSlideWithHand(elbow, slide));


    
  }
}
