package org.usfirst.frc.team126.robot.subsystems;

import org.usfirst.frc.team126.robot.Robot;
import org.usfirst.frc.team126.robot.commands.TurretControl;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

public class Turret extends Subsystem {


	public void initDefaultCommand() {
		setDefaultCommand(new TurretControl());
	}

	public static void Setup() {
		Robot.turretRotator.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 100);
		Robot.turretRotator.setSelectedSensorPosition(0,0,100);
		
	}
	public static double getEncoder(){
		return Robot.turretRotator.getSelectedSensorPosition();
	}
	public static void setSpeed(double speed) {
		Robot.turretRotator.set(ControlMode.PercentOutput, speed);
	}
	public static double getSpeedCurve(double distance) {
		double targetSpeed;
		targetSpeed = distance / 1000;
		if(targetSpeed < 0.1) {
			targetSpeed = 0.1;
		}
		if(targetSpeed > 0.5) {
			targetSpeed = 0.5;
		}
		return targetSpeed;
	}
	public static double getTargetPosition(double currentPosition) {
		double targetPosition;
		//double visionX = Robot.vision.getX();
		double visionX = 150;
		System.out.println(visionX);
		if(visionX == -1) {
			
			return currentPosition;
		}
		//double visionX = SmartDashboard.getNumber("targetEncoder", 150);
		if(visionX > 165) {
			targetPosition = currentPosition + (visionX - 165) * 10;
		} else if(visionX < 135) {
			targetPosition = currentPosition + (visionX - 135) * 10;
		} else {
			targetPosition = currentPosition;
		}
		return targetPosition;


	}
}
