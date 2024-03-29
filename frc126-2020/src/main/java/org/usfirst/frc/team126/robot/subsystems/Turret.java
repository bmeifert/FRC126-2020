package org.usfirst.frc.team126.robot.subsystems;

import org.usfirst.frc.team126.robot.Robot;
import org.usfirst.frc.team126.robot.commands.TurretControl;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.revrobotics.CANEncoder;
import com.revrobotics.EncoderType;

public class Turret extends Subsystem {
	public static boolean zeroed=false;
	static CANEncoder rotatorEncoder = new CANEncoder(Robot.turretMotor);
	static CANEncoder hoodEncoder = new CANEncoder(Robot.hoodMotor);
	static int shooterGrace = 0;
	double mDist = 0;
	static boolean shooterOn = false;
	
	/************************************************************************
	 ************************************************************************/

	public void initDefaultCommand() {
		setDefaultCommand(new TurretControl());
	}

	/************************************************************************
	 ************************************************************************/

	public static void Setup() {
		rotatorEncoder.setPosition(0);
		hoodEncoder.setPosition(0);
	}
	
	/************************************************************************
	 ************************************************************************/

	public double getRotatorEncoder(){
		return rotatorEncoder.getPosition();
	}
	public void setRotatorEncoder(double upos){
		rotatorEncoder.setPosition(upos);
	}
	public double getHoodEncoder() {
		return hoodEncoder.getPosition();
	}

	/************************************************************************
	 ************************************************************************/
	public void Rotate(double speed) {
		if(getRotatorEncoder() < -25 && speed < 0) {
			speed = 0;
		}
		if(getRotatorEncoder() > 25 && speed > 0) {
			speed = 0;
		}
		Robot.turretMotor.set(speed);
	}
	public void zeroRotator() {
		rotatorEncoder.setPosition(0);
	}
	public void moveHood(double speed) {
		//System.out.println("Hood moving: Encoder"+ getHoodEncoder());
		if(getHoodEncoder() < 0.5 && speed < 0) {
			speed = 0;
		}
		if(getHoodEncoder() > 8 && speed > 0) {
			speed = 0;
		}
		Robot.hoodMotor.set(speed);
	}
	public void zeroHood() {
		Robot.hoodMotor.set(-0.25);
		hoodEncoder.setPosition(0);
	}
	public void followHood() {
		/*
		* 6.1 @ 700cm
		* 5.1 @ 600cm
		* 4.6 @ 500cm
		* 4.2 @ 400cm
		* 3.6 @ 300cm
		*/
		mDist = (mDist * 9 + Robot.distance.measureDistance()) / 10;
		double targetHood = 1.70 + mDist * 0.0059;
		double speed = 0;
		if(Robot.turret.getHoodEncoder() > targetHood + 0.15) {
			speed = -0.15;
		} else if(Robot.turret.getHoodEncoder() < targetHood - 0.15) {
			speed = 0.15;
		} else {
			Robot.turret.moveHood(0);
		}
		if(getHoodEncoder() < 0.5 && speed < 0) {
			speed = 0;
		}
		if(getHoodEncoder() > 8 && speed > 0) {
			speed = 0;
		}
		Robot.hoodMotor.set(speed);
	}

	/************************************************************************
	 ************************************************************************/

	public double getSpeedCurve(double distance) {
		double targetSpeed;
		targetSpeed = distance / 1000;
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

		return currentPosition;
	}
}
