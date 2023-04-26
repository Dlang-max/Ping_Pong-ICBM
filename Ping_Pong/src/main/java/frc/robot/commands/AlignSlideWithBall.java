// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.IntegerSubscriber;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Slide;
import static frc.robot.Constants.CameraConstants.*;


public class AlignSlideWithBall extends CommandBase {
  
  Slide slide; 
  NetworkTableInstance inst = NetworkTableInstance.getDefault();

  NetworkTable pieces = inst.getTable("Vision");
  IntegerSubscriber xCenter = pieces.getIntegerTopic("xCenter").subscribe(0);

  public AlignSlideWithBall( Slide slide ) {

    this.slide = slide; 

    addRequirements(slide);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    int xCenter = getXCenter(); 

    PIDController slidePID = new PIDController(1, 0, 0);
    slidePID.setTolerance( 0.5 );

    double slidePower = slidePID.calculate(xCenter, WIDTH / 2);

    slide.moveSlide(slidePower);
  }

  @Override
  public void end(boolean interrupted) {
    slide.stop(); 
  }

  @Override
  public boolean isFinished() {
    return false;
  }

  private int getXCenter()
  {
    long x = xCenter.get(); 

    return (int)(x); 
  }
}
