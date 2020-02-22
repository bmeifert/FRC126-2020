package org.usfirst.frc.team126.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;

import org.usfirst.frc.team126.robot.Robot;
import org.usfirst.frc.team126.robot.commands.LiftControl;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The lift performs the following functions:
 *  - lift the unit up
 *  - slide back and forth on the arm
 *  - turn on or off the clamp/piston solenoid
 */
public class Lift extends Subsystem {

	public void initDefaultCommand() {
		setDefaultCommand(new LiftControl());
	}

	// controller should handle going into "Climb mode"
	// and controller then can move to the left or right

	public void liftArm(double outputValue) {
		Robot.armPulleyMotor.set(ControlMode.PercentOutput, outputValue);
	}

	public void slideOnArm(double outputValue) {
		Robot.sideToSidePullyMotor.set(ControlMode.PercentOutput, outputValue);
	}

	public void clamp(boolean clamp) {
		Robot.armPiston.set(clamp);
	}
}
