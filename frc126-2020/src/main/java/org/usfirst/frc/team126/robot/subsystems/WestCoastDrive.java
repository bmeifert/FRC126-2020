package org.usfirst.frc.team126.robot.subsystems;

import org.usfirst.frc.team126.robot.Robot;
import org.usfirst.frc.team126.robot.RobotMap;
import org.usfirst.frc.team126.robot.commands.OperatorControl;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.ControlMode;

public class WestCoastDrive extends Subsystem {

	double leftMultiplier, rightMultiplier, leftSpeed, rightSpeed, fbSlowDown, rotSlowDown, limiter;
	double previousLimiter = 1;
	public void initDefaultCommand() {
		setDefaultCommand(new OperatorControl());
		leftSpeed = 0;
		rightSpeed = 0;
	}

	public void Drive(double fb, double rot) { // Send power to the drive motors
		leftMultiplier = fb + (rot);
		rightMultiplier = fb - (rot);
		leftSpeed = leftMultiplier;
		rightSpeed = rightMultiplier;

		limiter = 1 + (1 * (InternalData.getVoltage() - Robot.voltageThreshold));
		if(limiter < 0) {
			limiter = 0;
		} else if(limiter > 1) {
			limiter = 1;
		}
		previousLimiter = (4 * previousLimiter + limiter) / 5;
		if(InternalData.getVoltage() < Robot.voltageThreshold) {
			leftSpeed *= previousLimiter;
			rightSpeed *= previousLimiter;
		}
		Robot.left1.set(ControlMode.PercentOutput, leftSpeed * RobotMap.left1Inversion);
		SmartDashboard.putNumber("left1", leftSpeed * RobotMap.left1Inversion);
		Robot.right1.set(ControlMode.PercentOutput, rightSpeed * RobotMap.right1Inversion);
		SmartDashboard.putNumber("right1", rightSpeed * RobotMap.right1Inversion);
		Robot.left2.set(ControlMode.PercentOutput, leftSpeed * RobotMap.left2Inversion);
		SmartDashboard.putNumber("left2", leftSpeed * RobotMap.left2Inversion);
		Robot.right2.set(ControlMode.PercentOutput, rightSpeed * RobotMap.right2Inversion);
		SmartDashboard.putNumber("right2", rightSpeed * RobotMap.right2Inversion);
	}
}
