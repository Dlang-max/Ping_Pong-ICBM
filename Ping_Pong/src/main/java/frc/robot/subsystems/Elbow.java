package frc.robot.subsystems;

import frc.robot.Constants.CANID;
import frc.robot.Constants.Governors;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxRelativeEncoder.Type;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.ElbowConstants.*;
import static frc.robot.Constants.CANID.*;
import static frc.robot.Constants.Governors.*;





/**
 * Elbow class that contains all of the information needed to control our shoulder motor during teleop
 */
public class Elbow extends SubsystemBase{

    private CANSparkMax elbow;
    private RelativeEncoder elbowEncoder;


    /**
     * Constructs a new elbow motor 
     */
    public Elbow()
    {
        elbow = new CANSparkMax( ELBOW_ID, MotorType.kBrushless );
        elbow.restoreFactoryDefaults();
        elbow.setIdleMode(IdleMode.kBrake);
        elbow.setInverted(false);

        elbowEncoder = elbow.getEncoder(Type.kHallSensor, 42);
        elbowEncoder.setPositionConversionFactor(ELBOW_POSITION_CONVERSION_FACTOR);

        elbow.burnFlash(); 
        resetElbowEncoder();
    }


    /**
     * Moves the elbow motor according to a given input speed
     * @param speed double input that comes from a controller
     */
    public void moveElbow( double speed )
    {
        elbow.set( speed * ELBOW_GOVERNOR );
    }

    /**
     * Gets the current position of the motor
     * 
     * @return the current position of the motor
     */
    public double getElbowPosition()
    {
        return elbowEncoder.getPosition(); 
    }


    /**
     * Stops the elbow motor
     */
    public void stop()
    {
        elbow.set( 0.0 );
    }

    /**
     * Resets the elbow encoder
     */
    public void resetElbowEncoder()
    {
        elbowEncoder.setPosition(0.0);
    }
}
