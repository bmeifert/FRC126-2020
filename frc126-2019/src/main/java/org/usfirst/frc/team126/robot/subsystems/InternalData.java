package org.usfirst.frc.team126.robot.subsystems;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;

public class InternalData extends Subsystem {
	static ADXRS450_Gyro gyro;
	public void initDefaultCommand() {
	}

	// Match stats
	public static boolean isAuto() { // Is the robot in auto mode?
		return  RobotState.isAutonomous();
	}
	public static boolean isTeleop() { // Or is it in teleop mode?
		return  RobotState.isOperatorControl();
	}
	public static boolean isEnabled() { // Or is it just enabled at all?
		return  RobotState.isEnabled();
	}
	public static double getMatchTime() { // Get the time left in the match
		return  Timer.getMatchTime();
	}
	public static double getVoltage() { // Get battery voltage
		return RobotController.getBatteryVoltage();
	}
	public static void initGyro() {
		if(gyro == null) {
			try {
				gyro = new ADXRS450_Gyro();
			} catch(Exception e) {
				gyro = null;
			}
		}
	}
	public static void resetGyro() {
		if(gyro != null) {
			gyro.reset();
		}
	}
	public static double getGyroAngle() {
		if(gyro != null) {
			return gyro.getAngle();
		} else {
			return 0;
		}
	}

}
