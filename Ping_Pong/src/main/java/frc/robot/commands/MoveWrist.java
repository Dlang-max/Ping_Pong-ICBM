// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Wrist;

import static frc.robot.Constants.WristConstants.*; 

public class MoveWrist extends CommandBase {
  
  private Wrist wrist;
  private double angle; 
  private PIDController wristPID; 

  public MoveWrist(Wrist wrist, double angle) {
    this.wrist = wrist; 
    this.angle = angle; 

    addRequirements(wrist);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    wristPID = new PIDController( WRIST_P, WRIST_I, WRIST_D); 

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    double wristPosition = wrist.getWristPosition() * 10; 
    double power = wristPID.calculate(wristPosition, angle);

    wrist.moveWrist(power);
    
    System.out.println("Wrist Pos: " + wrist.getWristPosition() * 10);
    System.out.println("power:" + power);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    wrist.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
