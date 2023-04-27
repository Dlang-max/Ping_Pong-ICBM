// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.IntegerSubscriber;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Elbow;
import static frc.robot.Constants.CameraConstants.*;


public class AlignElbowWithBall extends CommandBase {
  
  Elbow elbow; 

  NetworkTableInstance inst = NetworkTableInstance.getDefault();
  NetworkTable pieces = inst.getTable("Vision");

  IntegerSubscriber yCenter = pieces.getIntegerTopic("yCenter").subscribe(0);
  IntegerSubscriber width = pieces.getIntegerTopic("width").subscribe(0);

  IntegerSubscriber xMin = pieces.getIntegerTopic("xMin").subscribe(0);
  IntegerSubscriber xMax = pieces.getIntegerTopic("xMax").subscribe(0);
  IntegerSubscriber yMin = pieces.getIntegerTopic("yMin").subscribe(0);
  IntegerSubscriber yMax = pieces.getIntegerTopic("yMax").subscribe(0);

  IntegerSubscriber prevXMin = pieces.getIntegerTopic("pXMin").subscribe(0);
  IntegerSubscriber prevXMax = pieces.getIntegerTopic("pXMax").subscribe(0);
  IntegerSubscriber prevYMin = pieces.getIntegerTopic("pYMin").subscribe(0);
  IntegerSubscriber prevYMax = pieces.getIntegerTopic("pYMax").subscribe(0);



  public AlignElbowWithBall( Elbow elbow ) {

    this.elbow = elbow; 

    addRequirements(elbow);
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

    int y = getYCenter(); 

    PIDController elbowPID = new PIDController(.001, 0.0, 0.0);
    elbowPID.setTolerance( 0.5 );

    double elbowPower = elbowPID.calculate(y, HEIGHT / 2); 


    if( ymin == pymin && ymax == pymax && xmin == pxmin && xmax == pxmax )
    {
      elbow.stop();
    }
    else if( w < 50 )
    {
      elbow.stop();
    }
    else
    {
      elbow.moveElbow(elbowPower);
    }

    System.out.println("yCenter: " + getYCenter());
    System.out.println("elbow power: " + elbowPower);
  }

  @Override
  public void end(boolean interrupted) {
    elbow.stop(); 
  }

  @Override
  public boolean isFinished() {
    return false;
  }

  private int getYCenter()
  {
    long y = yCenter.get(); 
    return (int)(y); 
  }
}
