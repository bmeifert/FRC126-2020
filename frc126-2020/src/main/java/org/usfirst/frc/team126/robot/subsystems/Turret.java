package org.usfirst.frc.team126.robot.subsystems;

import org.usfirst.frc.team126.robot.Robot;
import org.usfirst.frc.team126.robot.commands.TurretControl;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

public class Turret extends Subsystem {
	public boolean zeroLeft=false;
	public boolean zeroRight=false;
	
	/************************************************************************
	 ************************************************************************/

	public void initDefaultCommand() {
		setDefaultCommand(new TurretControl());
	}

	/************************************************************************
	 ************************************************************************/

	public static void Setup() {
		Robot.turretRotator.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 100);
		Robot.turretRotator.setSelectedSensorPosition(0,0,100);
	}
	
	/************************************************************************
	 ************************************************************************/

	public double getEncoder(){
		return Robot.turretRotator.getSelectedSensorPosition();
	}

	/************************************************************************
	 ************************************************************************/

	public void setSpeed(double speed) {
		Robot.turretRotator.set(ControlMode.PercentOutput, speed);
	}

	/************************************************************************
	 ************************************************************************/

	public double getSpeedCurve(double distance) {
		double targetSpeed;
		targetSpeed = distance / 500;
		if(targetSpeed < 0.15) {
			targetSpeed = 0.15;
		}
		if(targetSpeed > 0.6) {
			targetSpeed = 0.6;
		}
		return targetSpeed;
	}

	/************************************************************************
	 ************************************************************************/

	public double getTargetPosition(double currentPosition, int objectID) {

		if (Robot.trackTarget == Robot.targetTypes.throwingTarget ||
			Robot.trackTarget == Robot.targetTypes.turretOnly ) {
			// We are tracking the throwing target
			System.out.println("Turret getTargetPosition: " + Robot.limeLight.getllTurretTarget());
			return Robot.limeLight.getllTurretTarget();
		}

		if (zeroLeft) {
			return currentPosition - 75;
		}

		if (zeroRight) {
			return currentPosition + 75;
		}

		return currentPosition;
	}
}
