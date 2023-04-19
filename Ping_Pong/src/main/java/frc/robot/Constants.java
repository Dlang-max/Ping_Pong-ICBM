// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

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

    public static final int SHOULDER_ID = 10;
    public static final int ELBOW_ID = 11;
    public static final int WRIST_ID = 12; 

  }

  /**
   * Governors class keeps track of movement governors
   */
  public static class Governors
  {
    public static final double SHOULDER_GOVERNOR = 0.3;
    public static final double ELBOW_GOVERNOR = 0.1; 
  }

  /**
   * Governors class keeps track of movement governors
   */
  public static class ShoulderConstants
  {
    public static final double SHOULDER_P = 0.0102;
    public static final double SHOULDER_I = 0.006;
    public static final double SHOULDER_D = 0;  

    public static final double SHOULDER_GEAR_RATIO = 1 / (49);  
    public static final int DEGREES_IN_CIRCLE = 360;
    public static final double SHOULDER_POSITION_CONVERSION_FACTOR = SHOULDER_GEAR_RATIO * 2.0 * Math.PI;
  }

  public static class ElbowConstants
  {
    public static final double ELBOW_P = 0.012;
    public static final double ELBOW_I = 0.01;
    public static final double ELBOW_D = 0;  

    public static final double ELBOW_GEAR_RATIO = 1 / (49);  
    public static final int DEGREES_IN_CIRCLE = 360;
    public static final double ELBOW_POSITION_CONVERSION_FACTOR = ELBOW_GEAR_RATIO * 2.0 * Math.PI;
  }
}
