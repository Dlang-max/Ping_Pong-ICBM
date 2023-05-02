package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxRelativeEncoder.Type;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.HandConstants.*;
import static frc.robot.Constants.CANID.*;
import static frc.robot.Constants.Governors.*;





/**
 * Elbow class that contains all of the information needed to control our shoulder motor during teleop
 */
public class Hand extends SubsystemBase{

    private CANSparkMax hand;
    private RelativeEncoder handEncoder;


    /**
     * Constructs a new elbow motor 
     */
    public Hand()
    {
        hand = new CANSparkMax( HAND_ID, MotorType.kBrushless );
        hand.restoreFactoryDefaults();
        hand.setIdleMode(IdleMode.kBrake);
        hand.setInverted(false);

        handEncoder = hand.getEncoder(Type.kHallSensor, 42);
        handEncoder.setPositionConversionFactor(HAND_POSITION_CONVERSION_FACTOR);

        hand.burnFlash(); 
        resetHandEncoder();
    }


    /**
     * Moves the elbow motor according to a given input speed
     * @param speed double input that comes from a controller
     */
    public void moveHand( double speed )
    {
        hand.set( speed * HAND_GOVERNOR );
    }

    /**
     * Gets the current position of the motor
     * 
     * @return the current position of the motor
     */
    public double getHandPosition()
    {
        return handEncoder.getPosition(); 
    }


    /**
     * Stops the elbow motor
     */
    public void stop()
    {
        hand.set( 0.0 );
    }

    /**
     * Resets the elbow encoder
     */
    public void resetHandEncoder()
    {
        handEncoder.setPosition(0.0);
    }
}
