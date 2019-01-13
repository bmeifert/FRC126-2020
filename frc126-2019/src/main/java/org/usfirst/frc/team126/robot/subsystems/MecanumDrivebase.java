package org.usfirst.frc.team126.robot.subsystems;

import org.usfirst.frc.team126.robot.Robot;
import org.usfirst.frc.team126.robot.RobotMap;
import org.usfirst.frc.team126.robot.commands.DriveWithJoysticks;
import edu.wpi.first.wpilibj.command.Subsystem;
import com.ctre.phoenix.motorcontrol.ControlMode;

public class MecanumDrivebase extends Subsystem {

	double frontLeftMultiplier, frontRightMultiplier, backLeftMultiplier, backRightMultiplier;
	double flSpeed, frSpeed, blSpeed, brSpeed;

	public void initDefaultCommand() {
		setDefaultCommand(new DriveWithJoysticks());
		flSpeed = 0;
		frSpeed = 0;
		blSpeed = 0;
		brSpeed = 0;
	}

	public void Drive(double fb, double lr, double rot, boolean isCurved, boolean isSmoothed, int smoothFactor) { // Smooth drive
		if(isCurved) { // curve inputs for more prescision

			if(fb > 0) {
				fb = fb * fb; // if it's stupid but it works it's not stupid
			}
			else {
				fb = 0 - fb * fb;
			}
			if(lr > 0) {
				lr = lr * lr;
			}
			else {
				lr = 0 - lr * lr;
			}
			if(rot > 0) {
				rot = rot * rot;
			}
			else {
				rot = 0 - rot * rot;
			}
		}

		if(smoothFactor < 1) { // Smooth factor failsafe
			smoothFactor = 1;
		}
		if(smoothFactor > 50) {
			smoothFactor = 50;
		}

		frontLeftMultiplier = fb + rot;
		frontRightMultiplier = fb - rot;
		backLeftMultiplier = fb + rot;
		backRightMultiplier = fb - rot;
		lr = 0; // WEST COAST DRIVE SIMULATOR 2019
		if(lr > 0) { // Sideways coefficients (take precedence over but do not limit movement)
			frontLeftMultiplier = frontLeftMultiplier + Math.abs(lr);
			frontRightMultiplier = frontRightMultiplier - Math.abs(lr);
			backLeftMultiplier = backLeftMultiplier - Math.abs(lr);
			backRightMultiplier = backRightMultiplier + Math.abs(lr);
		}
		else {
			frontLeftMultiplier = frontLeftMultiplier - Math.abs(lr);
			frontRightMultiplier = frontRightMultiplier + Math.abs(lr);
			backLeftMultiplier = backLeftMultiplier + Math.abs(lr);
			backRightMultiplier = backRightMultiplier - Math.abs(lr);	
		}

		if(isSmoothed) {
			flSpeed = (flSpeed * smoothFactor + frontLeftMultiplier) / (smoothFactor + 1); // Smooth out spikes and sudden movements by averaging speeds
			frSpeed = (frSpeed * smoothFactor + frontRightMultiplier) / (smoothFactor + 1);
			blSpeed = (blSpeed * smoothFactor + backLeftMultiplier) / (smoothFactor + 1);
			brSpeed = (brSpeed * smoothFactor + backRightMultiplier) / (smoothFactor + 1);
		}
		else {
			flSpeed = frontLeftMultiplier;
			frSpeed = frontRightMultiplier;
			blSpeed = backLeftMultiplier;
			brSpeed = backRightMultiplier;
		}

		Robot.frontLeft.set(ControlMode.PercentOutput, flSpeed * RobotMap.frontLeftInversion);
		Robot.frontRight.set(ControlMode.PercentOutput, frSpeed * RobotMap.frontRightInversion);
		Robot.backLeft.set(ControlMode.PercentOutput, blSpeed * RobotMap.backLeftInversion);
		Robot.backRight.set(ControlMode.PercentOutput, brSpeed * RobotMap.backRightInversion);
	}
}
