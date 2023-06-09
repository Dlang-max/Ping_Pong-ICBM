// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Hand;
import static frc.robot.Constants.HandConstants.*;

/**
 * Swings paddle when operator presses A button
 */
public class HitBall extends CommandBase {
  Hand hand; 

  PIDController handEndPID; 
  PIDController handStartPID; 
  boolean endReached = false;


  public HitBall(Hand hand) {
    this.hand = hand; 

    addRequirements(hand);
  }

  @Override
  public void initialize() {
    handEndPID = new PIDController( HAND_P, HAND_I, HAND_D);
    handEndPID.setTolerance(5);
    handEndPID.setSetpoint(-90);

    handStartPID = new PIDController( HAND_P, HAND_I, HAND_D);
    handStartPID.setTolerance(2.0);
    handStartPID.setSetpoint(0);
   }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
      if(!handEndPID.atSetpoint() && !endReached || hand.getHandPosition() * 50 < handEndPID.getSetpoint())
      {
        double elbowPosition = hand.getHandPosition() * 50; 
        double power = handEndPID.calculate(elbowPosition, -90) * 2;
        hand.moveHand(power);
      }
      else if(!handStartPID.atSetpoint() && handEndPID.atSetpoint())
      {
        endReached = true; 
        double elbowPosition = hand.getHandPosition() * 50; 
        double power = handStartPID.calculate(elbowPosition, 0) * 2;
        hand.moveHand(power);
      }
      else if(handStartPID.atSetpoint())
      {
        endReached = false; 
      }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    hand.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
