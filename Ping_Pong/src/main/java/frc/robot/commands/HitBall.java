// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Hand;
import static frc.robot.Constants.HandConstants.*; 


public class HitBall extends CommandBase {
  Hand hand; 
  double angle; 
  PIDController handPID; 

  public HitBall(Hand hand, double angle) {
    this.hand = hand; 
    this.angle = angle; 

    addRequirements(hand);
  }

  @Override
  public void initialize() {
    handPID = new PIDController( HAND_P, HAND_I, HAND_D);
    handPID.setTolerance(2.0);
   }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double elbowPosition = hand.getHandPosition() * 50; 
    double power = handPID.calculate(elbowPosition, angle) * 2;

    hand.moveHand(power);

    System.out.println("Hand Pos: " + hand.getHandPosition() * 50);
    System.out.println("power:" + power);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    hand.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return handPID.atSetpoint();
  }
}
