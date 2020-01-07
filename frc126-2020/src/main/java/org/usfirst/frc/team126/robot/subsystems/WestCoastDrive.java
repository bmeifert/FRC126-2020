package org.usfirst.frc.team126.robot.subsystems;

import org.usfirst.frc.team126.robot.Robot;
import org.usfirst.frc.team126.robot.RobotMap;
import org.usfirst.frc.team126.robot.commands.OperatorControl;
import edu.wpi.first.wpilibj.command.Subsystem;
import com.ctre.phoenix.motorcontrol.ControlMode;

public class WestCoastDrive extends Subsystem {

	double leftMultiplier, rightMultiplier, leftSpeed, rightSpeed, fbSlowDown, rotSlowDown;
	public void initDefaultCommand() {
		setDefaultCommand(new OperatorControl());
		leftSpeed = 0;
		rightSpeed = 0;
	}

	public void Drive(double fb, double rot) { // Send power to the drive motors
		if(Math.abs(fb) < 0.1) {
			fb = 0;
		}
		if(Math.abs(rot) < 0.1) {
			rot = 0;
		}
		leftMultiplier = fb + (rot);
		rightMultiplier = fb - (rot);
		leftSpeed = leftMultiplier;
		rightSpeed = rightMultiplier;

		Robot.left1.set(ControlMode.PercentOutput, leftSpeed * RobotMap.left1Inversion);
		Robot.right1.set(ControlMode.PercentOutput, rightSpeed * RobotMap.right1Inversion);
		Robot.left2.set(ControlMode.PercentOutput, leftSpeed * RobotMap.left2Inversion);
		Robot.right2.set(ControlMode.PercentOutput, rightSpeed * RobotMap.right2Inversion);

	}
}
