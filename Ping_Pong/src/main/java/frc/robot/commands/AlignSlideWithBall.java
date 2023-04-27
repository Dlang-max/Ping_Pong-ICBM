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
  IntegerSubscriber width = pieces.getIntegerTopic("width").subscribe(0);

  IntegerSubscriber xMin = pieces.getIntegerTopic("xMin").subscribe(0);
  IntegerSubscriber xMax = pieces.getIntegerTopic("xMax").subscribe(0);
  IntegerSubscriber yMin = pieces.getIntegerTopic("yMin").subscribe(0);
  IntegerSubscriber yMax = pieces.getIntegerTopic("yMax").subscribe(0);

  IntegerSubscriber prevXMin = pieces.getIntegerTopic("pXMin").subscribe(0);
  IntegerSubscriber prevXMax = pieces.getIntegerTopic("pXMax").subscribe(0);
  IntegerSubscriber prevYMin = pieces.getIntegerTopic("pYMin").subscribe(0);
  IntegerSubscriber prevYMax = pieces.getIntegerTopic("pYMax").subscribe(0);



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

    int w = (int)width.get(); 

    int ymin = (int)yMin.get();
    int ymax = (int)yMax.get();
    int xmin = (int)xMin.get();
    int xmax = (int)xMax.get();

    int pymin = (int)prevYMin.get();
    int pymax = (int)prevYMax.get();
    int pxmin = (int)prevXMin.get();
    int pxmax = (int)prevXMax.get();



    int xCenter = getXCenter(); 

    PIDController slidePID = new PIDController(0.001, 0, 0);
    slidePID.setTolerance( 0.5 );

    double slidePower = slidePID.calculate(xCenter, WIDTH / 2);

    if( ymin == pymin && ymax == pymax && xmin == pxmin && xmax == pxmax )
    {
      slide.stop();
    }
    else if( w < 50 )
    {
      slide.stop();
    }
    else
    {
      slide.moveSlide(slidePower);
    }
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
