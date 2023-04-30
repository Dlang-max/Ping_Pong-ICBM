// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Elbow;
import static frc.robot.Constants.ElbowConstants.*; 

public class MoveElbow extends CommandBase {
  
  private Elbow elbow;
  private double angle; 
  private PIDController elbowPID; 

  public MoveElbow(Elbow elbow, double angle) {
    this.elbow = elbow; 
    this.angle = angle; 

    addRequirements(elbow);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    elbowPID = new PIDController( ELBOW_P, ELBOW_I, ELBOW_D); 

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    double elbowPosition = elbow.getElbowPosition() * 6; 
    double power = elbowPID.calculate(elbowPosition, angle);

    elbow.moveElbow(power);
    
    //double power = shoulderPID.calculate()
    System.out.println("Elbow Pos: " + elbow.getElbowPosition() * 6);
    System.out.println("power:" + power);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    elbow.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
