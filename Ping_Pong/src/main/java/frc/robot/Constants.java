// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }

  /**
   * CANID class keeps track of CAN IDs
   */
  public static class CANID{

    public static final int SHOULDER = -0;
    public static final int ELBOW = -0;

  }

  /**
   * Governors class keeps track of movement governors
   */
  public static class Governors
  {
    public static final double SHOULDER_GOVERNOR = 0.75;
    public static final double ELBOW_GOVERNOR = 0.75; 
  }

  public static final class ShoulderConstants {
    public static final double SOFT_LIMIT_REVERSE = -0.1;
    public static final double SOFT_LIMIT_FORWARD = 4.5;

    public static final int SHOULDER_LIMIT_SWITCH_DIO_CHANNEL = 0; //?

    public static final int SHOULDER_CURRENT_LIMIT = 20;
    public static final double SHOULDER_MANUAL_SCALED = 0.42;

    public static final double SHOULDER_GEAR_RATIO = 1.0 / (49); // 1:224 planetary gear ratio
    public static final double POSITION_CONVERSION_FACTOR = SHOULDER_GEAR_RATIO * 2.0 * Math.PI;
    public static final double VELOCITY_CONVERSION_FACTOR = SHOULDER_GEAR_RATIO * 2.0 * Math.PI / 60.0;
    public static final double SHOULDER_FREE_SPEED = 11000.0 * VELOCITY_CONVERSION_FACTOR;
    public static final double SHOULDER_ZERO_COSINE_OFFSET = -Math.PI / 6;
    public static final ArmFeedforward SHOULDER_FF = new ArmFeedforward(0.0, 0.4, 12.0 / SHOULDER_FREE_SPEED, 0.0);
    public static final double SHOULDER_DEFAULT_P = 0.79;
    public static final double SHOULDER_DEFAULT_I = 0.02;
    public static final double SHOULDER_DEFAULT_D = 0.0;
    public static final Constraints SHOULDER_MOTION_CONSTRAINTS = new Constraints(0.66, 0.66);
  }

  
  public static final class ElbowConstants {
    public static final double SOFT_LIMIT_REVERSE = -0.1;
    public static final double SOFT_LIMIT_FORWARD = 4.5;

    public static final int ELBOW_LIMIT_SWITCH_DIO_CHANNEL = 1; //?

    public static final int ELBOW_CURRENT_LIMIT = 20;
    public static final double ELBOW_MANUAL_SCALED = 0.42;

    public static final double ELBOW_GEAR_RATIO = 1.0 / (49); // 1:224 planetary gear ratio
    public static final double POSITION_CONVERSION_FACTOR = ELBOW_GEAR_RATIO * 2.0 * Math.PI;
    public static final double VELOCITY_CONVERSION_FACTOR = ELBOW_GEAR_RATIO * 2.0 * Math.PI / 60.0;
    public static final double ELBOW_FREE_SPEED = 11000.0 * VELOCITY_CONVERSION_FACTOR;
    public static final double ELBOW_ZERO_COSINE_OFFSET = -Math.PI / 6;
    public static final ArmFeedforward ELBOW_FF = new ArmFeedforward(0.0, 0.4, 12.0 / ELBOW_FREE_SPEED, 0.0);
    public static final double ELBOW_DEFAULT_P = 0.79;
    public static final double ELBOW_DEFAULT_I = 0.02;
    public static final double ELBOW_DEFAULT_D = 0.0;
    public static final Constraints ELBOW_MOTION_CONSTRAINTS = new Constraints(0.66, 0.66);
  }
}
