package org.usfirst.frc.team126.robot.subsystems;

import org.usfirst.frc.team126.robot.Robot;
import org.usfirst.frc.team126.robot.commands.DataPeriodic;

import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.command.Subsystem;

public class InternalData extends Subsystem {

	public void initDefaultCommand() {
		setDefaultCommand(new DataPeriodic());
	}

	// Power stats
	public double getBusVoltage() { // Get the primary bus voltage
		return  Robot.pdp.getVoltage();
	}
	public double getBusCurrent(int channel) { // Get the amperage of a single channel
		return  Robot.pdp.getCurrent(channel);
	}
	public double getBusTotalPower() { // Returns total power draw in watts
		return  Robot.pdp.getTotalPower();
	}

	// Match stats
	public boolean isAuto() { // Returns total power draw in watts
		return  RobotState.isAutonomous();
	}
	public boolean isTeleop() { // Returns total power draw in watts
		return  RobotState.isOperatorControl();
	}
	public boolean isEnabled() { // Returns total power draw in watts
		return  RobotState.isEnabled();
	}

}
