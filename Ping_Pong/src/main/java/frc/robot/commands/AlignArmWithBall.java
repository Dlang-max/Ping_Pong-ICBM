// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.Elbow;
import frc.robot.subsystems.Shoulder;
import frc.robot.subsystems.Slide;
import frc.robot.subsystems.Wrist;

public class AlignArmWithBall extends ParallelCommandGroup {
  
  Slide slide;
  Elbow elbow; 
  Shoulder shoulder;
  Wrist wrist; 
  public AlignArmWithBall(Slide slide, Elbow elbow, Shoulder shoulder, Wrist wrist) {
    this.slide = slide; 
    this.elbow = elbow; 
    this.shoulder = shoulder; 
    this.wrist = wrist; 

    addCommands(new AlignSlideWithBall(slide), new MoveShoulder(shoulder, -45), new AlignElbowWithBall(elbow), new MoveWrist(wrist, 0));
     
  }
}
