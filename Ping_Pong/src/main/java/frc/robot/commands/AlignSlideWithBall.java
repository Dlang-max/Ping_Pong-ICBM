// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.BooleanSubscriber;
import edu.wpi.first.networktables.IntegerSubscriber;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Slide;
import static frc.robot.Constants.CameraConstants.*;
import static frc.robot.Constants.SlidePIDConstants.*;
import static frc.robot.Constants.LimitSwitchConstants.*;



public class AlignSlideWithBall extends CommandBase {  
  Slide slide;
  PIDController slidePID = new PIDController(SLIDE_P, SLIDE_I, SLIDE_D); 

  NetworkTableInstance inst = NetworkTableInstance.getDefault();

  NetworkTable coordinates = inst.getTable("Coordinates"); 
  IntegerSubscriber xCenter = coordinates.getIntegerTopic("xCenter").subscribe(WIDTH / 2);

  NetworkTable ballFound = inst.getTable("Found");
  BooleanSubscriber found = ballFound.getBooleanTopic("ballFound").subscribe(false);

  public AlignSlideWithBall( Slide slide ) {

    this.slide = slide; 
    addRequirements(slide);


  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    double slidePower = slidePID.calculate(getXCenter(), WIDTH / 2);

    if(!isBallFound())
    {
      slide.stop();
    }
    else
    {
      slide.moveSlide(-slidePower);
    }
    slide.checkSlideLimitSwitches();

  }

  @Override
  public void end(boolean interrupted) {
    slide.stop(); 
    slidePID.close();
  }

  @Override
  public boolean isFinished() {
    return false;
  }

  // x-coordinate center of ball
  private int getXCenter()
  {
    return (int)(xCenter.get()); 
  }

   // is the ball found
   private boolean isBallFound()
   {
     return found.get(); 
   }

}
