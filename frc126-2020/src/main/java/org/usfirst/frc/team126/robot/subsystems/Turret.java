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
		SmartDashboard.putNumber("turretRotator", speed);
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

		if (!Robot.vision.packetData[1].isValid) {
			System.out.println("Target is not valid");
			return currentPosition;
		}

		int y = Robot.vision.packetData[1].Y;
		int x = Robot.vision.packetData[1].X;
		int sx = Robot.vision.getServoX();
		int sy = Robot.vision.getServoY();

		System.out.println("target valid " + x + " servo " + sx);
		
		int servoRatio = 3;

		if (x < 80 ) {
			targetPosition = (x * - servoRatio) + ((sx - 256) * servoRatio);
		} else if (x > 120) {
			targetPosition = (((x-110) * servoRatio) - ((sx - 255) * servoRatio));
		} else if (sx < 200) {
			targetPosition = ( (sx - 255) * servoRatio);	
		} else if (sx > 300) {
			targetPosition = ( (sx - 255) * servoRatio);
		} else {	
			targetPosition = currentPosition;
		}

		return targetPosition;
	}
}
