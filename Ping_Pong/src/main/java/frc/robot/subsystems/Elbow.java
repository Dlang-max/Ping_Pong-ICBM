package frc.robot.subsystems;

import frc.robot.Constants.CANID;
import frc.robot.Constants.Governors;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Elbow class that contains all of the information needed to control our elbow motor during teleop
 */
public class Elbow extends SubsystemBase{

    private CANSparkMax elbow;

    /**
     * Constructs a new elbow motor 
     */
    public Elbow()
    {
        elbow = new CANSparkMax( CANID.ELBOW, MotorType.kBrushless );
        elbow.restoreFactoryDefaults();
        elbow.setIdleMode(IdleMode.kBrake);
        elbow.setInverted(false);
    }

    /**
     * Moves the elbow motor according to a given input speed
     * @param speed double input that comes from a controller
     */
    public void moveElbow( double speed )
    {
        elbow.set( speed * Governors.ELBOW_GOVERNOR );
    }

    /**
     * Stops the elbow motor
     */
    public void stop()
    {
        elbow.set( 0.0 );
    }
}
