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

	public static double getTargetPosition(double currentPosition, int objectID) {
		double targetPosition;

		if (Robot.trackTarget) {
			// We are tracking the throwing target, not the ball
			return currentPosition;
		}
		
		if ( !Robot.vision.packetData[objectID].isValid ) {
			Robot.robotTurn = 0;
			Robot.robotDrive = 0;
			return currentPosition;
		}
		
		int y = Robot.vision.packetData[objectID].Y;
		int x = Robot.vision.packetData[objectID].X;
		int h = Robot.vision.packetData[objectID].Height;
		int w = Robot.vision.packetData[objectID].Width;
		int sx = Robot.vision.getServoX();
		int sy = Robot.vision.getServoY();
	
		double servoRatio = 1.7;

		// 4800 close
		// 100 - far
		
		servoRatio += (h * w) / 4000.0;

		double area = h * w;

		if (objectID == 1) {
			if ( area < 2000 ) {
				Robot.robotDrive=.2;
			} else {
				Robot.robotDrive=0;
			}
		} else {
			Robot.robotDrive=0;
		}

		if (sx < 200) {
			targetPosition = ( (sx - 255) * servoRatio *-1);	
			if ( x < 80 ) {
				targetPosition -= ( (80 - x) * servoRatio); 
			}
			if ( x > 120 ) {
				targetPosition += ( (x-120) * servoRatio); 
			}
		} else if (sx > 300) {
			targetPosition = ( (sx - 255) * servoRatio *-1);
			if ( x < 80 ) {
				targetPosition -= ( (80 - x) * servoRatio); 
			}
			if ( x > 120 ) {
				targetPosition += ( (x-120) * servoRatio); 
			}
		} else if (x < 80 ) {
			targetPosition = (x * -1 * servoRatio);
		} else if (x > 120) {
			targetPosition = ((x-110) * servoRatio);
		} else {	
			targetPosition = currentPosition;
		}

		System.out.println("valid " + x + " tp:" + targetPosition 
				 + " cx:" + x + " sx" + sx + " h:" + h
				 + " w: " + w + " a:" + area);

		double turnFactor = .25;
	    if (Robot.robotDrive != 0) {
			turnFactor = .15;
		}
		if ( targetPosition < -200) {
			System.out.println("Move Left");
			Robot.robotTurn= turnFactor * -1;
		} else if ( targetPosition > 200) {
			System.out.println("Move Right");
			Robot.robotTurn=turnFactor;  
		} else {		 
			 System.out.println("Move Center");
			 Robot.robotTurn=0;
		}  		 

		return targetPosition;
	}
}
