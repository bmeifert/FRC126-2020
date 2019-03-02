package org.usfirst.frc.team126.robot.subsystems;

import java.util.HashMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import org.usfirst.frc.team126.robot.Robot;
import org.usfirst.frc.team126.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Wrist extends Subsystem {

	static enum limitStates {
		bottom, down, up, top, ok
	}
	public static enum wristPos {
		fold, flat, up, down, free
	}
	static limitStates limitState = limitStates.up;
	static limitStates moveState = limitStates.up;
	static boolean antiDrift = false;
	static double currentPot, prevPot;
	static wristPos targetPos;
	static wristPos currentPos;
	static double autoInvert;
	static HashMap<wristPos, Double> encoderMap = new HashMap<wristPos, Double>();
	static boolean isZeroing = false;


	public void initDefaultCommand() {
	}
	public static void initWrist() {
		Robot.wristMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 100);
		Robot.wristMotor.setSelectedSensorPosition(0,0,100);
		encoderMap.clear(); // Create mappings with encoder positions
		encoderMap.put(wristPos.fold, RobotMap.foldWristPos);
		encoderMap.put(wristPos.flat, RobotMap.flatWristPos);
		encoderMap.put(wristPos.up, RobotMap.upWristPos);
		encoderMap.put(wristPos.down, RobotMap.downWristPos);
		encoderMap.get(wristPos.fold);
		targetPos = wristPos.free;
		currentPos = wristPos.free;
	}

	public static void zeroNeg() {
		Robot.wristMotor.setSelectedSensorPosition(0,0,100);
		Robot.wristMotor.set(ControlMode.PercentOutput, 0.3);
		isZeroing = true;
	}
	public static void zeroPos() {
		Robot.wristMotor.setSelectedSensorPosition(RobotMap.wristEncoderInversion * RobotMap.wristMax,0,100);
		Robot.wristMotor.set(ControlMode.PercentOutput, -0.3);
		isZeroing = true;
	}
	public static void setTargetPos(wristPos wPos) {
		if(wPos == wristPos.fold && Robot.liftBottomLimit.get() == true) {
			wPos = wristPos.free;
		}
		targetPos = wPos;
		currentPos = wristPos.free;
	}
	public static void actuateWrist(double speed) {
		if(speed >= 0) {
			moveState = limitStates.up;
		}
		else {
			moveState = limitStates.down;
		}
		if(Robot.wristMotor.getOutputCurrent() > 20) { // break for wrist motor
			if(moveState == limitStates.up) {
				limitState = limitStates.top;
				Log.print(1, "Wrist", "TOP LIMIT");
			}
			else {
				limitState = limitStates.bottom;
				Log.print(1, "Wrist", "BOTTOM LIMIT");
			}
		}
		else {
			limitState = limitStates.ok;
		}
		currentPot = Robot.wristMotor.getSelectedSensorPosition() * RobotMap.wristEncoderInversion;

		if(currentPot < 200) {
			if(speed > 0) {
				speed = 0;
			}
		} else if(currentPot > RobotMap.wristMax) {
			if(speed < 0) {
				speed = 0;
			}
		}
		if(limitState == limitStates.bottom) {
			if(speed < 0) {
				speed = 0;
			}
		}
		if(limitState == limitStates.top) {
			if(speed > 0) {
				speed = 0;
			}
		}
		/*
		if(currentPos != targetPos && targetPos != wristPos.free) {
			if(encoderMap.get(targetPos) > currentPot + 100) {
				autoInvert = -1;
				speed = 0.4 * autoInvert;
			} else if (encoderMap.get(targetPos) < currentPot - 100) {
				autoInvert = 1;
				speed = 0.7 * autoInvert;
			} else {
				autoInvert = 1;
				currentPos = targetPos;
				speed = 0;
			}
		} else if(targetPos == wristPos.free){
			currentPos = wristPos.free;
		}
		*/
		currentPos = wristPos.free;
		if(Math.abs(speed) < 0.05) {
			speed = RobotMap.wristIdle;
		}
		if(targetPos == wristPos.fold && Robot.liftBottomLimit.get() == true) {
			targetPos = wristPos.free;
		}
		if(isZeroing == false) {
			Robot.wristMotor.set(ControlMode.PercentOutput, speed);
		}
		isZeroing = false;
	}

}