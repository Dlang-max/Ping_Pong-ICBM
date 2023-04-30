// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.networktables.BooleanSubscriber;
import edu.wpi.first.networktables.DoubleSubscriber;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Elbow;
import frc.robot.subsystems.Slide;

public class AlignElbowAndSlideWithHand extends CommandBase {

  Elbow elbow;
  Slide slide; 

  NetworkTableInstance inst = NetworkTableInstance.getDefault();

  NetworkTable power = inst.getTable("Power"); 
  DoubleSubscriber elbowPower = power.getDoubleTopic("elbowPower").subscribe(0.0);
  DoubleSubscriber slidePower = power.getDoubleTopic("slidePower").subscribe(0.0);

  NetworkTable hit = inst.getTable("Hit"); 
  BooleanSubscriber elbowClose = hit.getBooleanTopic("rightClosed").subscribe(false);
  BooleanSubscriber slideClosed = hit.getBooleanTopic("leftClosed").subscribe(false);

  public AlignElbowAndSlideWithHand(Elbow elbow, Slide slide) {
    this.elbow = elbow;
    this.slide = slide; 
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    double elbowSpeed = elbowPower.get();
    double slideSpeed = slidePower.get();

    if(elbow.getElbowPosition() * 6 < -30 || elbow.getElbowPosition() * 6 > 90)
    {
      elbow.stop();
      slide.moveSlide(slideSpeed);
    }
    else
    {
      elbow.moveElbow(elbowSpeed);
      slide.moveSlide(slideSpeed);
    }
  }

  @Override
  public void end(boolean interrupted) {
    elbow.stop();
    slide.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}