// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
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

    int yCenter = getYCenter(); 

    PIDController elbowPID = new PIDController(.0022, 0.0, 0.0);
    elbowPID.setTolerance( 0.5 );

    double elbowPower = elbowPID.calculate(yCenter, HEIGHT / 2); 
    elbow.moveElbow(elbowPower);

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
