package frc.robot.subsystems;

import frc.robot.Constants.CANID;
import static frc.robot.Constants.ShoulderConstants.*;

import frc.robot.Constants.Governors;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxRelativeEncoder.Type;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


/**
 * Shoulder class that contains all of the information needed to control our shoulder motor during teleop
 */
public class Shoulder extends SubsystemBase{

    private CANSparkMax shoulder;
    private RelativeEncoder shoulderEncoder;


    /**
     * Constructs a new shoulder motor 
     */
    public Shoulder()
    {
        shoulder = new CANSparkMax( CANID.SHOULDER_ID, MotorType.kBrushless );
        shoulder.restoreFactoryDefaults();
        shoulder.setIdleMode(IdleMode.kBrake);
        shoulder.setInverted(false);

        shoulderEncoder = shoulder.getEncoder(Type.kHallSensor, 42);
        shoulderEncoder.setPositionConversionFactor(SHOULDER_POSITION_CONVERSION_FACTOR);

        shoulder.burnFlash(); 
        resetShoulderEncoder();
    }


    /**
     * Moves the shoulder motor according to a given input speed
     * @param speed double input that comes from a controller
     */
    public void moveShoulder( double speed )
    {
        shoulder.set( speed * Governors.SHOULDER_GOVERNOR );
    }

    public double getShoulderPosition()
    {
        return shoulderEncoder.getPosition(); 
    }

    /**
     * Resets the shoulder encoder
     */
    public void resetShoulderEncoder()
    {
        shoulderEncoder.setPosition(0.0);
    }

    /**
     * Stops the shoulder motor
     */
    public void stop()
    {
        shoulder.set( 0.0 );
    }




}
