// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.MoveArm;
import frc.robot.subsystems.Elbow;
import frc.robot.subsystems.Shoulder;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  private final Shoulder shoulder;
  private final Elbow elbow; 

  CommandXboxController operatorOI;

  public RobotContainer() {

    operatorOI = new CommandXboxController(0); 

    shoulder = new Shoulder();
    elbow = new Elbow(); 
   
    configureBindings();

   
    
    
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {

    shoulder.setDefaultCommand(
      new RunCommand( 
        () -> shoulder.moveShoulder( -MathUtil.applyDeadband(operatorOI.getLeftY(), 0.07 ) ),
         shoulder) );

    elbow.setDefaultCommand(
    new RunCommand( 
      () -> elbow.moveElbow( -MathUtil.applyDeadband(operatorOI.getRightY(), 0.07 ) ),
        elbow) );

    operatorOI.a().whileTrue( new MoveArm(shoulder, elbow) ); 

    operatorOI.b().onTrue( new InstantCommand( () -> shoulder.resetShoulderEncoder() ).andThen( new InstantCommand( () -> elbow.resetElbowEncoder() ) ) );

  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return null; 
  }
}
