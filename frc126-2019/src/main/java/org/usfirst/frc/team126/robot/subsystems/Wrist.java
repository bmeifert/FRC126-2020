package org.usfirst.frc.team126.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;

import org.usfirst.frc.team126.robot.Robot;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Wrist extends Subsystem {

	static enum limitStates {
		bottom, down, up, top, ok
	}
	static limitStates limitState = limitStates.up;
	static limitStates moveState = limitStates.up;
	public void initDefaultCommand() {
	}
	public static void actuateWrist(double speed) {
		if(speed >= 0) {
			moveState = limitStates.up;
		}
		else {
			moveState = limitStates.down;
		}
		if(Robot.wristMotor.getOutputCurrent() > 10) { // 10 amp break for wrist motor
			if(moveState == limitStates.up) {
				limitState = limitStates.top;
			}
			else {
				limitState = limitStates.bottom;
			}
		}
		else {
			limitState = limitStates.ok;
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