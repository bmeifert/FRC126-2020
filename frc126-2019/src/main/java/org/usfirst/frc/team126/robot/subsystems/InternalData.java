package org.usfirst.frc.team126.robot.subsystems;

import org.usfirst.frc.team126.robot.Robot;
import org.usfirst.frc.team126.robot.commands.DataPeriodic;

import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;

public class InternalData extends Subsystem {

	public void initDefaultCommand() {
		setDefaultCommand(new DataPeriodic());
	}

	// Power stats
	public static double getBusVoltage() { // Get the primary bus voltage
		return  Robot.pdp.getVoltage();
	}
	public static double getBusCurrent(int channel) { // Get the amperage of a single channel
		return  Robot.pdp.getCurrent(channel);
	}
	public static double getBusTotalPower() { // Get the total power draw in watts
		return  Robot.pdp.getTotalPower();
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

}
