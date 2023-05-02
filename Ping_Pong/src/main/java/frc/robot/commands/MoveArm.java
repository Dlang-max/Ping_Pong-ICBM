package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.subsystems.Elbow;
import frc.robot.subsystems.Hand;
import frc.robot.subsystems.Shoulder;
import frc.robot.subsystems.Wrist;


public class MoveArm extends ParallelCommandGroup {
  Shoulder shoulder;
  Elbow elbow;
  Wrist wrist; 
  Hand hand;
  
  public MoveArm( Shoulder shoulder, Elbow elbow, Wrist wrist, Hand hand ) {
    this.shoulder = shoulder;
    this.elbow = elbow; 
    this.wrist = wrist; 
    this.hand = hand; 

    addCommands( new HitBall(hand) )
    ;

    // addCommands( new MoveShoulder(shoulder, -45), 
    //   new RunCommand(() -> hand.moveHand(-0.5), hand).withTimeout(0.5).andThen(new RunCommand( () -> hand.moveHand(0.5), hand)).withTimeout(0.5));
  
  }

}
