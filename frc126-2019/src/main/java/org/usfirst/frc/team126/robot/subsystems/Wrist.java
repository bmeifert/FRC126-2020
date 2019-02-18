package org.usfirst.frc.team126.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import org.usfirst.frc.team126.robot.Robot;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Wrist extends Subsystem {

	static enum limitStates {
		bottom, down, up, top, ok
	}
	static limitStates limitState = limitStates.up;
	static limitStates moveState = limitStates.up;
	static boolean antiDrift = false;
	static double currentPot, prevPot;

	public void initDefaultCommand() {
	}
	public static void initWrist() {
		Robot.wristMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 100);
		Robot.wristMotor.setSelectedSensorPosition(0,0,100);
	}
	public static void actuateWrist(double speed) {
		if(speed >= 0) {
			moveState = limitStates.up;
		}
		else {
			moveState = limitStates.down;
		}
		if(Robot.wristMotor.getOutputCurrent() > 28) { // break for wrist motor
			if(moveState == limitStates.up) {
				limitState = limitStates.top;
				Robot.log.print(1, "Wrist", "TOP LIMIT");
			}
			else {
				limitState = limitStates.bottom;
				Robot.log.print(1, "Wrist", "TOP LIMIT");
			}
		}
		else {
			limitState = limitStates.ok;
		}
		currentPot = Robot.wristMotor.getSelectedSensorPosition();
		if(currentPot < 0.75) {
			if(speed > 0) {
				speed = 0;
			}
		} else if(currentPot > 0.98) { // as speed gets more negative we go outwards, purposely inverted
			if(speed < 0) {
				speed = 0;
			}
		}
		if(Math.abs(speed) < 0.05) {
			if(antiDrift == false) {
				prevPot = currentPot;
				antiDrift = true;
			}

			if(currentPot > prevPot && currentPot > 0.8 && currentPot < 0.96) {
				speed = 0.2;
			}
		} else {
			antiDrift = false;
		}
		if(currentPot > 0.85) {
			if(speed < -0.05) {
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


		Robot.wristMotor.set(ControlMode.PercentOutput, speed);
	}

}