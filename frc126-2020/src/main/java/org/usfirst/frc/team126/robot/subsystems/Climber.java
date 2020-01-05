package org.usfirst.frc.team126.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;

import org.usfirst.frc.team126.robot.Robot;
import org.usfirst.frc.team126.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Climber extends Subsystem {
	public void initDefaultCommand() {
	}
	public static void setClimber(double speed) {
		if(Robot.climberMotor.getOutputCurrent() > 20) {
			Robot.climberMotor.set(ControlMode.PercentOutput, 0);
		} else {
			if(Math.abs(speed) < 0.1) {
				Robot.climberMotor.set(ControlMode.PercentOutput, 0.05 * RobotMap.climberInversion);
			} else {
				Robot.climberMotor.set(ControlMode.PercentOutput, speed * RobotMap.climberInversion);
			}
		}

	}
}