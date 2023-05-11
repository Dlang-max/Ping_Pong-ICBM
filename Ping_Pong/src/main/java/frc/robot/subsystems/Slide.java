package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxRelativeEncoder.Type;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.CANID.*;
import static frc.robot.Constants.LimitSwitchConstants.*;



/**
 * Slide class that contains all of the information needed to control our slide motor during teleop
 */
public class Slide extends SubsystemBase{

    private CANSparkMax slide;
    private RelativeEncoder slideEncoder;

    private DigitalInput leftLimitSwitch;
    private DigitalInput rightLimitSwitch;



    /**
     * Constructs a new slide motor 
     */
    public Slide()
    {
        slide = new CANSparkMax( SLIDE_ID, MotorType.kBrushless );
        slide.restoreFactoryDefaults();
        slide.setIdleMode(IdleMode.kBrake);
        slide.setInverted(false);

        slideEncoder = slide.getEncoder(Type.kHallSensor, 42);
        slide.burnFlash(); 
        resetSlideEncoder();

        leftLimitSwitch = new DigitalInput(LEFT_LIMIT_SWITCH_DIO_CHANNEL);
        rightLimitSwitch = new DigitalInput(RIGHT_LIMIT_SWITCH_DIO_CHANNEL);
    }

    @Override
    public void periodic()
    {
        checkSlideLimitSwitches();
    }


    /**
     * Moves the slide motor according to a given input speed
     * @param speed double input that comes from a controller
     */
    public void moveSlide( double speed )
    {
        
        slide.set( speed );
    }

    /**
     * Moves the slide motor according to a given input speed
     * @param speed double input that comes from a controller
     */
    public void moveSlideRight( double speed )
    {
        slide.set( speed );
    }

    /**
     * Moves the slide motor according to a given input speed
     * @param speed double input that comes from a controller
     */
    public void moveSlideLeft( double speed )
    {
        slide.set( -speed );
    }

    /**
     * Resets the slide encoder
     */
    public void resetSlideEncoder()
    {
        slideEncoder.setPosition(0.0);
    }

    /**
     * Stops the slide motor
     */
    public void stop()
    {
        slide.set( 0.0 );
    }

    /**
     * Checks the limit switches on the slide
     */
    public void checkSlideLimitSwitches()
    {
        if(!leftLimitSwitch.get() || !rightLimitSwitch.get())
        {
            stop(); 
        }
    }
}
