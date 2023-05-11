// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.BooleanSubscriber;
import edu.wpi.first.networktables.IntegerSubscriber;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Elbow;
import static frc.robot.Constants.CameraConstants.*;
import static frc.robot.Constants.ElbowPIDConstants.*;

/**
 * Aligns the elbow with the ball.
 */
public class AlignElbowWithBall extends CommandBase {
  
  Elbow elbow; 
  PIDController elbowPID = new PIDController(ELBOW_P, ELBOW_I, ELBOW_D);

  NetworkTableInstance inst = NetworkTableInstance.getDefault();

  NetworkTable coordinates = inst.getTable("Coordinates"); 
  IntegerSubscriber yCenter = coordinates.getIntegerTopic("yCenter").subscribe(HEIGHT / 2);

  NetworkTable ballFound = inst.getTable("Found");
  BooleanSubscriber found = ballFound.getBooleanTopic("ballFound").subscribe(false);


  public AlignElbowWithBall( Elbow elbow ) {

    this.elbow = elbow; 
    addRequirements(elbow);

  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    System.out.println("Found: " + found.get());
    double elbowPower = elbowPID.calculate(getYCenter(), HEIGHT / 2); 
    if(!found.get())
    {
      elbow.stop();
    }
    else if((elbow.getElbowPosition() * 6 < 0 && elbowPower < 0) || (elbow.getElbowPosition() * 6 > 110 && elbowPower > 0)  )
    {
      elbow.stop();
    }
    else
    {
      elbow.moveElbow(elbowPower);
    }
  }

  @Override
  public void end(boolean interrupted) {
    elbow.stop(); 
    elbowPID.close();
  }

  @Override
  public boolean isFinished() {
    return false;
  }

  // y-coordinate for center of ball
  private int getYCenter()
  {
    return (int)(yCenter.get());
  }
}
