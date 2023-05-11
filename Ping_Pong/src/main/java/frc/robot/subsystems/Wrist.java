package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxRelativeEncoder.Type;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.WristConstants.*;
import static frc.robot.Constants.CANID.*;
import static frc.robot.Constants.Governors.*;





/**
 * Wrist class that contains all of the information needed to control our wrist motor during teleop
 */
public class Wrist extends SubsystemBase{

    private CANSparkMax wrist;
    private RelativeEncoder wristEncoder;


    /**
     * Constructs a new wrist motor 
     */
    public Wrist()
    {
        wrist = new CANSparkMax( WRIST_ID, MotorType.kBrushless );
        wrist.restoreFactoryDefaults();
        wrist.setIdleMode(IdleMode.kBrake);
        wrist.setInverted(false);

        wristEncoder = wrist.getEncoder(Type.kHallSensor, 42);
        wristEncoder.setPositionConversionFactor(WRIST_POSITION_CONVERSION_FACTOR);

        wrist.burnFlash(); 
        resetWristEncoder();
    }


    /**
     * Moves the wrist motor according to a given input speed
     * @param speed double input that comes from a controller
     */
    public void moveWrist( double speed )
    {
        wrist.set( speed * WRIST_GOVERNOR );
    }

    /**
     * Gets the current position of the motor
     * 
     * @return the current position of the motor
     */
    public double getWristPosition()
    {
        return wristEncoder.getPosition(); 
    }


    /**
     * Stops the wrist motor
     */
    public void stop()
    {
        wrist.set( 0.0 );
    }

    /**
     * Resets the wrist encoder
     */
    public void resetWristEncoder()
    {
        wristEncoder.setPosition(0.0);
    }
}
