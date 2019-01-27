package org.usfirst.frc.team126.robot.subsystems;

import org.usfirst.frc.team126.robot.commands.DataPeriodic;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;

public class InternalData extends Subsystem {

	public void initDefaultCommand() {
		setDefaultCommand(new DataPeriodic());
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

}
