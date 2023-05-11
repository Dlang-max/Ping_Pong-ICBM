// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shoulder;
import static frc.robot.Constants.ShoulderConstants.*; 

/**
 * Keeps the shoulder at a certain angle. 
 */
public class MoveShoulder extends CommandBase {
  
  private Shoulder shoulder;
  private double angle; 
  private ProfiledPIDController shoulderPID; 

  public MoveShoulder(Shoulder shoulder, double angle) {
    this.shoulder = shoulder; 
    this.angle = angle; 

    addRequirements(shoulder);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    shoulderPID = new ProfiledPIDController( SHOULDER_P, SHOULDER_I, SHOULDER_D, new TrapezoidProfile.Constraints(-10,-10 )); 
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double shoulderPosition = shoulder.getShoulderPosition() * 6; 

    double power = shoulderPID.calculate(shoulderPosition, angle); 

    shoulder.moveShoulder(power);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shoulder.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
