package org.usfirst.frc.team126.robot.subsystems;

import org.usfirst.frc.team126.robot.Robot;
import org.usfirst.frc.team126.robot.RobotMap;
import org.usfirst.frc.team126.robot.commands.DriveWithJoysticks;
import edu.wpi.first.wpilibj.command.Subsystem;

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

	public void Drive(double fb, double lr, double rot, boolean isCurved) { // Smooth drive
		if(isCurved) { // curve inputs for more prescision
			if(fb < 0) {
				fb = fb * fb; // if it's stupid but it works it's not stupid
			}
			else {
				fb = 0 - fb * fb;
			}
			if(lr < 0) {
				lr = lr * lr;
			}
			else {
				lr = 0 - lr * lr;
			}
			if(rot < 0) {
				rot = rot * rot;
			}
			else {
				rot = 0 - rot * rot;
			}
		}

		frontLeftMultiplier = fb + rot;
		frontRightMultiplier = fb - rot;
		backLeftMultiplier = fb + rot;
		backRightMultiplier = fb - rot;

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
		
		flSpeed = (flSpeed * 4 + frontLeftMultiplier) / 5; // Smooth out spikes and sudden movements by averaging speeds
		frSpeed = (frSpeed * 4 + frontRightMultiplier) / 5;
		blSpeed = (blSpeed * 4 + backLeftMultiplier) / 5;
		brSpeed = (brSpeed * 4 + backRightMultiplier) / 5;

		Robot.frontLeft.set(flSpeed * RobotMap.frontLeftInversion);
		Robot.frontRight.set(frSpeed * RobotMap.frontRightInversion);
		Robot.backLeft.set(blSpeed * RobotMap.frontRightInversion);
		Robot.backRight.set(brSpeed * RobotMap.backRightInversion);
	}
}
