package org.usfirst.frc.team126.robot.subsystems;

import org.usfirst.frc.team126.robot.Robot;
import org.usfirst.frc.team126.robot.RobotMap;
import org.usfirst.frc.team126.robot.commands.DriveWithJoysticks;
import edu.wpi.first.wpilibj.command.Subsystem;
import com.ctre.phoenix.motorcontrol.ControlMode;

public class WestCoastDrive extends Subsystem {

	double leftMultiplier, rightMultiplier;
	double leftSpeed, rightSpeed;

	public void initDefaultCommand() {
		setDefaultCommand(new DriveWithJoysticks());
		leftSpeed = 0;
		rightSpeed = 0;
	}

	public void Drive(double fb, double rot, boolean isCurved, boolean isSmoothed, int smoothFactor) { // Smooth drive

		if(isCurved) { // curve inputs for more prescision
			if(fb > 0) {
				fb = fb * fb; // if it's stupid but it works it's not stupid
			}
			else {
				fb = 0 - fb * fb;
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

		leftMultiplier = fb + rot;
		rightMultiplier = fb - rot;

		if(isSmoothed) {
			leftSpeed = (leftSpeed * smoothFactor + leftMultiplier) / (smoothFactor + 1); // Smooth out spikes and sudden movements by averaging speeds
			rightSpeed = (rightSpeed * smoothFactor + rightMultiplier) / (smoothFactor + 1);
		}
		else {
			leftSpeed = leftMultiplier;
			rightSpeed = rightMultiplier;
		}

		Robot.left1.set(ControlMode.PercentOutput, leftSpeed * RobotMap.front1Inversion);
		Robot.right1.set(ControlMode.PercentOutput, rightSpeed * RobotMap.front2Inversion);
		Robot.left2.set(ControlMode.PercentOutput, leftSpeed * RobotMap.back1Inversion);
		Robot.right2.set(ControlMode.PercentOutput, rightSpeed * RobotMap.back2Inversion);
	}
}
