package frc.robot.subsystems;

import frc.robot.Constants.CANID;
import frc.robot.Constants.Governors;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Shoulder class that contains all of the information needed to control our shoulder motor during teleop
 */
public class Shoulder extends SubsystemBase{

    private CANSparkMax shoulder;

    /**
     * Constructs a new shoulder motor 
     */
    public Shoulder()
    {
        shoulder = new CANSparkMax( CANID.SHOULDER, MotorType.kBrushless );
        shoulder.restoreFactoryDefaults();
        shoulder.setIdleMode(IdleMode.kBrake);
        shoulder.setInverted(false);
    }

    /**
     * Moves the shoulder motor according to a given input speed
     * @param speed double input that comes from a controller
     */
    public void moveShoulder( double speed )
    {
        shoulder.set( speed * Governors.SHOULDER_GOVERNOR );
    }

    /**
     * Stops the shoulder motor
     */
    public void stop()
    {
        shoulder.set( 0.0 );
    }
}
