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
import static frc.robot.Constants.ElbowConstants.*;


/**
 * Elbow class that contains all of the information needed to control our shoulder motor during teleop
 */
public class Elbow extends SubsystemBase{

    private CANSparkMax elbowMotor;
    private RelativeEncoder elbowEncoder;
    private DigitalInput elbowLimitSwitch;
    private SparkMaxPIDController elbowPIDController;
    private double elbowSetpoint = 0.0;
    private TrapezoidProfile motorProfile;
    private TrapezoidProfile.State targetState;
    private double feedforward;
    private Timer timer;

    /**
     * Constructs a new elbow motor 
     */
    public Elbow()
    {
        elbowMotor = new CANSparkMax( CANID.ELBOW, MotorType.kBrushless );
        elbowEncoder = elbowMotor.getEncoder(Type.kHallSensor, 42);
        elbowLimitSwitch = new DigitalInput(ELBOW_LIMIT_SWITCH_DIO_CHANNEL);

        elbowMotor.setInverted(false);
        elbowMotor.setSmartCurrentLimit(ELBOW_CURRENT_LIMIT);
        elbowMotor.enableSoftLimit(SoftLimitDirection.kForward, true);
        elbowMotor.enableSoftLimit(SoftLimitDirection.kReverse, true);
        elbowMotor.setSoftLimit(SoftLimitDirection.kForward, (float) SOFT_LIMIT_FORWARD);
        elbowMotor.setSoftLimit(SoftLimitDirection.kReverse, (float) SOFT_LIMIT_REVERSE);
        elbowMotor.setIdleMode(IdleMode.kBrake);

        elbowEncoder.setPositionConversionFactor(POSITION_CONVERSION_FACTOR);
        elbowEncoder.setVelocityConversionFactor(VELOCITY_CONVERSION_FACTOR);

        elbowPIDController = elbowMotor.getPIDController();
        elbowPIDController.setP(ELBOW_DEFAULT_P);
        elbowPIDController.setI(ELBOW_DEFAULT_I);
        elbowPIDController.setD(ELBOW_DEFAULT_D);

        elbowMotor.burnFlash(); 

        timer = new Timer();
        timer.start();
        timer.reset();
    
        elbowMotor.restoreFactoryDefaults();
        elbowMotor.setIdleMode(IdleMode.kBrake);
        elbowMotor.setInverted(false);

        updateMotionProfile();
        resetEncoder();
    }

  /**
   * Sets the elbow to a target position.
   * 
   * @param setpoint
   */
  public void setTargetPosition(double setpoint) {
    if (setpoint != elbowSetpoint) {
      elbowSetpoint = setpoint;
      updateMotionProfile();
    }
  }

  /**
   * Updates the motion profile with the current elbow position and velocity.
   */
  private void updateMotionProfile() {
    TrapezoidProfile.State state = new TrapezoidProfile.State(elbowEncoder.getPosition(), elbowEncoder.getVelocity());
    TrapezoidProfile.State goal = new TrapezoidProfile.State(elbowSetpoint, 0.0);
    motorProfile = new TrapezoidProfile(ELBOW_MOTION_CONSTRAINTS, goal, state);
    timer.reset();
  }

  /**
   * Automatically moves the elbow to the target position.
   */
  public void runAutomatic() {
    double elapsedTime = timer.get();
    // if motion profile is finished, set the target state to the current position
    if (motorProfile.isFinished(elapsedTime)) {
      targetState = new TrapezoidProfile.State(elbowSetpoint, 0.0);
    } else {
      targetState = motorProfile.calculate(elapsedTime);
    }
    // update the feedforward variable with the new target state
    feedforward = ELBOW_FF.calculate(elbowEncoder.getPosition() + ELBOW_ZERO_COSINE_OFFSET, targetState.velocity);
    // set the elbow motor speed to the target position
    elbowPIDController.setReference(targetState.position, CANSparkMax.ControlType.kPosition, 0, feedforward);
  }

  /**
   * Manually moves the elbow with a given power.
   * 
   * @param power
   */
  public void runManual(double power) {
    // get the current position of the encoder
    elbowSetpoint = elbowEncoder.getPosition();
    // create a new target state with the current encoder position and zero velocity
    targetState = new TrapezoidProfile.State(elbowSetpoint, 0.0);
    // create a new motion profile with the current state as the target state
    motorProfile = new TrapezoidProfile(ELBOW_MOTION_CONSTRAINTS, targetState, targetState);
    // update the feedforward variable with the new target state
    feedforward = ELBOW_FF.calculate(elbowEncoder.getPosition() + ELBOW_ZERO_COSINE_OFFSET, targetState.velocity);
    // set the elbow motor speed to manual control with scaled power
    elbowMotor.set((power * ELBOW_MANUAL_SCALED) + (feedforward / 12.0));
  }

  /**
   * Resets the elbow encoder
   */
  public void resetEncoder() {
    if (elbowEncoder.getPosition() != 0.0) {
      elbowEncoder.setPosition(0.0);
    }

  }


    /**
     * Moves the elbow motor according to a given input speed
     * @param speed double input that comes from a controller
     */
    public void moveElbow( double speed )
    {
        elbowMotor.set( speed * Governors.SHOULDER_GOVERNOR );
    }

    /**
     * Stops the elbow motor
     */
    public void stop()
    {
        elbowMotor.set( 0.0 );
    }
}
