// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.BooleanSubscriber;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Hand;
import static frc.robot.Constants.HandConstants.*;

public class HitBallWithHand extends CommandBase {
  Hand hand; 

  PIDController handPID; 
  boolean endReached = false;

  NetworkTableInstance inst = NetworkTableInstance.getDefault();

  NetworkTable hit = inst.getTable("Hit"); 
  BooleanSubscriber elbowClose = hit.getBooleanTopic("rightClosed").subscribe(false);
  BooleanSubscriber slideClosed = hit.getBooleanTopic("leftClosed").subscribe(false);


  public HitBallWithHand(Hand hand) {
    this.hand = hand; 

    addRequirements(hand);
  }

  @Override
  public void initialize() {
    handPID = new PIDController( HAND_P, HAND_I, HAND_D);
    handPID.setTolerance(5);
    handPID.setSetpoint(-90);
   }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double position = hand.getHandPosition() * 50;
    if(elbowClose.get() && slideClosed.get())
    {
      handPID.setSetpoint(-90);
      double power = handPID.calculate(position, -90);
      hand.moveHand(power);
    }
    else if(!elbowClose.get() && !slideClosed.get())
    {
      handPID.setSetpoint(0);
      double power = handPID.calculate(position, 0);
      hand.moveHand(power);
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
