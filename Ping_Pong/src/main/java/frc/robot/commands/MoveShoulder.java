// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shoulder;
import static frc.robot.Constants.ShoulderConstants.*; 

public class MoveShoulder extends CommandBase {
  
  private Shoulder shoulder;
  private double angle; 
  private PIDController shoulderPID; 

  public MoveShoulder(Shoulder shoulder, double angle) {
    this.shoulder = shoulder; 
    this.angle = angle; 

    addRequirements(shoulder);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    shoulderPID = new PIDController( SHOULDER_P, SHOULDER_I, SHOULDER_D); 
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double shoulderPosition = shoulder.getShoulderPosition() * 6; 

    double power = shoulderPID.calculate(shoulderPosition, angle); 

    shoulder.moveShoulder(power);

    System.out.println("Shoulder Pos: " + shoulder.getShoulderPosition() * 6);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
