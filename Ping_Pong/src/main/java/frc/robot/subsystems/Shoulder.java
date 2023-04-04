package frc.robot.subsystems;

import frc.robot.Constants.CANID;
import frc.robot.Constants.Governors;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMax.SoftLimitDirection;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxRelativeEncoder.Type;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.ShoulderConstants.*;


/**
 * Shoulder class that contains all of the information needed to control our shoulder motor during teleop
 */
public class Shoulder extends SubsystemBase{

    private CANSparkMax shoulderMotor;
    private RelativeEncoder shoulderEncoder;
    private DigitalInput shoulderLimitSwitch;
    private SparkMaxPIDController shoulderPIDController;
    private double shoulderSetpoint = 0.0;
    private TrapezoidProfile motorProfile;
    private TrapezoidProfile.State targetState;
    private double feedforward;
    private Timer timer;

    /**
     * Constructs a new shoulder motor 
     */
    public Shoulder()
    {
        shoulderMotor = new CANSparkMax( CANID.SHOULDER, MotorType.kBrushless );
        shoulderEncoder = shoulderMotor.getEncoder(Type.kHallSensor, 42);
        shoulderLimitSwitch = new DigitalInput(SHOULDER_LIMIT_SWITCH_DIO_CHANNEL);

        shoulderMotor.setInverted(false);
        shoulderMotor.setSmartCurrentLimit(SHOULDER_CURRENT_LIMIT);
        shoulderMotor.enableSoftLimit(SoftLimitDirection.kForward, true);
        shoulderMotor.enableSoftLimit(SoftLimitDirection.kReverse, true);
        shoulderMotor.setSoftLimit(SoftLimitDirection.kForward, (float) SOFT_LIMIT_FORWARD);
        shoulderMotor.setSoftLimit(SoftLimitDirection.kReverse, (float) SOFT_LIMIT_REVERSE);
        shoulderMotor.setIdleMode(IdleMode.kBrake);

        shoulderEncoder.setPositionConversionFactor(POSITION_CONVERSION_FACTOR);
        shoulderEncoder.setVelocityConversionFactor(VELOCITY_CONVERSION_FACTOR);

        shoulderPIDController = shoulderMotor.getPIDController();
        shoulderPIDController.setP(SHOULDER_DEFAULT_P);
        shoulderPIDController.setI(SHOULDER_DEFAULT_I);
        shoulderPIDController.setD(SHOULDER_DEFAULT_D);

        shoulderMotor.burnFlash(); 

        timer = new Timer();
        timer.start();
        timer.reset();
    
        shoulderMotor.restoreFactoryDefaults();
        shoulderMotor.setIdleMode(IdleMode.kBrake);
        shoulderMotor.setInverted(false);

        updateMotionProfile();
        resetEncoder();
    }

  /**
   * Sets the shoulder to a target position.
   * 
   * @param setpoint
   */
  public void setTargetPosition(double setpoint) {
    if (setpoint != shoulderSetpoint) {
      shoulderSetpoint = setpoint;
      updateMotionProfile();
    }
  }

  /**
   * Updates the motion profile with the current shoulder position and velocity.
   */
  private void updateMotionProfile() {
    TrapezoidProfile.State state = new TrapezoidProfile.State(shoulderEncoder.getPosition(), shoulderEncoder.getVelocity());
    TrapezoidProfile.State goal = new TrapezoidProfile.State(shoulderSetpoint, 0.0);
    motorProfile = new TrapezoidProfile(SHOULDER_MOTION_CONSTRAINTS, goal, state);
    timer.reset();
  }

  /**
   * Automatically moves the shoulder to the target position.
   */
  public void runAutomatic() {
    double elapsedTime = timer.get();
    // if motion profile is finished, set the target state to the current position
    if (motorProfile.isFinished(elapsedTime)) {
      targetState = new TrapezoidProfile.State(shoulderSetpoint, 0.0);
    } else {
      targetState = motorProfile.calculate(elapsedTime);
    }
    // update the feedforward variable with the new target state
    feedforward = SHOULDER_FF.calculate(shoulderEncoder.getPosition() +SHOULDER_ZERO_COSINE_OFFSET, targetState.velocity);
    // set the shoulder motor speed to the target position
    shoulderPIDController.setReference(targetState.position, CANSparkMax.ControlType.kPosition, 0, feedforward);
  }

  /**
   * Manually moves the shoulder with a given power.
   * 
   * @param power
   */
  public void runManual(double power) {
    // get the current position of the encoder
    shoulderSetpoint = shoulderEncoder.getPosition();
    // create a new target state with the current encoder position and zero velocity
    targetState = new TrapezoidProfile.State(shoulderSetpoint, 0.0);
    // create a new motion profile with the current state as the target state
    motorProfile = new TrapezoidProfile(SHOULDER_MOTION_CONSTRAINTS, targetState, targetState);
    // update the feedforward variable with the new target state
    feedforward = SHOULDER_FF.calculate(shoulderEncoder.getPosition() + SHOULDER_ZERO_COSINE_OFFSET, targetState.velocity);
    // set the shoulder motor speed to manual control with scaled power
    shoulderMotor.set((power * SHOULDER_MANUAL_SCALED) + (feedforward / 12.0));
  }

  /**
   * Resets the shoulder encoder
   */
  public void resetEncoder() {
    if (shoulderEncoder.getPosition() != 0.0) {
      shoulderEncoder.setPosition(0.0);
    }

  }


    /**
     * Moves the shoulder motor according to a given input speed
     * @param speed double input that comes from a controller
     */
    public void moveShoulder( double speed )
    {
        shoulderMotor.set( speed * Governors.SHOULDER_GOVERNOR );
    }

    /**
     * Stops the shoulder motor
     */
    public void stop()
    {
        shoulderMotor.set( 0.0 );
    }
}
