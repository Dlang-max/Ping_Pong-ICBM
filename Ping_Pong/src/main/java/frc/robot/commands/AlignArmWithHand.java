// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.networktables.BooleanSubscriber;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.Elbow;
import frc.robot.subsystems.Hand;
import frc.robot.subsystems.Shoulder;
import frc.robot.subsystems.Slide;
import frc.robot.subsystems.Wrist;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class AlignArmWithHand extends ParallelCommandGroup {

  NetworkTableInstance inst = NetworkTableInstance.getDefault();


  NetworkTable hit = inst.getTable("Hit"); 
  BooleanSubscriber elbowClose = hit.getBooleanTopic("rightClosed").subscribe(false);
  BooleanSubscriber slideClosed = hit.getBooleanTopic("leftClosed").subscribe(false);
  
  Elbow elbow;
  Shoulder shoulder;
  Slide slide; 
  Wrist wrist; 
  Hand hand;
  public AlignArmWithHand(Elbow elbow, Shoulder shoulder, Slide slide, Wrist wrist, Hand hand) {
    this.elbow = elbow;
    this.shoulder = shoulder;
    this.slide = slide; 
    this.wrist = wrist; 
    this.hand = hand; 

    

    addCommands( new MoveShoulder(shoulder, -45), new AlignElbowAndSlideWithHand(elbow, slide), new MoveWrist(wrist, 0));
    


    
  }
}
