package org.usfirst.frc.team126.robot.subsystems;

import org.usfirst.frc.team126.robot.Robot;
import org.usfirst.frc.team126.robot.RobotMap;
import org.usfirst.frc.team126.robot.commands.OperatorControl;
import edu.wpi.first.wpilibj.command.Subsystem;

import java.util.Arrays;

import com.ctre.phoenix.motorcontrol.ControlMode;

public class WestCoastDrive extends Subsystem {

	double leftMultiplier, rightMultiplier, leftSpeed, rightSpeed, fbSlowDown, rotSlowDown, limiter, left1RPM, left2RPM, right1RPM, right2RPM;
	double previousLimiter = 1;
	public void initDefaultCommand() {
		setDefaultCommand(new OperatorControl());
		leftSpeed = 0;
		rightSpeed = 0;
	}
	public double getMeanRPM() {
		left1RPM = Math.abs(Robot.left1.getSelectedSensorVelocity() / 3.41);
		left2RPM = Math.abs(Robot.left2.getSelectedSensorVelocity() / 3.41);
		right1RPM = Math.abs(Robot.right1.getSelectedSensorVelocity() / 3.41);
		right2RPM = Math.abs(Robot.right2.getSelectedSensorVelocity() / 3.41);
		return((left1RPM + left2RPM + right1RPM + right2RPM) / 4);
	}
	public double getStallRPM() {
		double[] stallrpms = {0,0,0,0};
		left1RPM = Math.abs(Robot.left1.getSelectedSensorVelocity() / 3.41);
		left2RPM = Math.abs(Robot.left2.getSelectedSensorVelocity() / 3.41);
		right1RPM = Math.abs(Robot.right1.getSelectedSensorVelocity() / 3.41);
		right2RPM = Math.abs(Robot.right2.getSelectedSensorVelocity() / 3.41);
		stallrpms[0] = left1RPM;
		stallrpms[1] = left2RPM;
		stallrpms[2] = right1RPM;
		stallrpms[3] = right2RPM;
		Arrays.sort(stallrpms);
		return(stallrpms[0]);
	}
	public double getPeakRPM() {
		double[] peakrpms = {0,0,0,0};
		left1RPM = Math.abs(Robot.left1.getSelectedSensorVelocity() / 3.41);
		left2RPM = Math.abs(Robot.left2.getSelectedSensorVelocity() / 3.41);
		right1RPM = Math.abs(Robot.right1.getSelectedSensorVelocity() / 3.41);
		right2RPM = Math.abs(Robot.right2.getSelectedSensorVelocity() / 3.41);
		peakrpms[0] = left1RPM;
		peakrpms[1] = left2RPM;
		peakrpms[2] = right1RPM;
		peakrpms[3] = right2RPM;
		Arrays.sort(peakrpms);
		return(peakrpms[peakrpms.length - 1]);
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
		Robot.right1.set(ControlMode.PercentOutput, rightSpeed * RobotMap.right1Inversion);
		Robot.left2.set(ControlMode.PercentOutput, leftSpeed * RobotMap.left2Inversion);
		Robot.right2.set(ControlMode.PercentOutput, rightSpeed * RobotMap.right2Inversion);
	}
}
