package org.usfirst.frc.team126.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;

import org.usfirst.frc.team126.robot.Robot;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Intake extends Subsystem {

	static double intakeSpeed = 0;
	public void initDefaultCommand() {
	}

	public static void intakeOff() {
		Robot.intakeMotor.set(ControlMode.PercentOutput, 0);
	}
	public static void setIntake(double speed, boolean isSmoothed) {
		if(isSmoothed) {
			speed = (intakeSpeed * 9 + speed) / 10;
			Robot.intakeMotor.set(ControlMode.PercentOutput, speed);
			intakeSpeed = speed;
			
		}
		else {
			speed = (intakeSpeed * 9 + speed) / 10;
			Robot.intakeMotor.set(ControlMode.PercentOutput, speed);
			intakeSpeed = speed;
		}

	}
	public static double getSpeed() {
		return intakeSpeed;
	}

}