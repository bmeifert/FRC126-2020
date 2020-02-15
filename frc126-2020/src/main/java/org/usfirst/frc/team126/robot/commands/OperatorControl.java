package org.usfirst.frc.team126.robot.commands;

import org.usfirst.frc.team126.robot.Robot;
import org.usfirst.frc.team126.robot.subsystems.ColorSpinner;
import org.usfirst.frc.team126.robot.subsystems.Log;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
public class OperatorControl extends Command {	
	public static enum driveStates{drive, rotationControl, positionControl, chassis, demo};
	public static driveStates currentState;
	public static Color targetColor;
	public static double targetRotations;
	static double targetRPM, targetRPMdistance, currentTargetSpeed;
	static double currentRotations;
	static boolean rotationFirstIteration = true;
	static boolean onTargetColor;
	Solenoid s1 = new Solenoid(0);
	static boolean gearSwitchPress = false;
	static boolean gear = false;

	public OperatorControl() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.driveBase);
	}

	// Run before command starts 1st iteration
	@Override
	protected void initialize() {
		Log.print(0, "OI", "Operator control initialized.");
	}

	// Called every tick (20ms)
	@SuppressWarnings("static-access")
	@Override
	protected void execute() {
		// START CONTROL SETUP

		// Get stick inputs
		JoystickWrapper driveJoystick = new JoystickWrapper(Robot.oi.driveController, 0.05);
		JoystickWrapper operatorJoystick = new JoystickWrapper(Robot.oi.operatorController, 0.05);

		// END CONTROLS SETUP
		if(driveJoystick.isAButton()) {
			currentState = driveStates.drive;
			ColorSpinner.spin(0);
		}
		switch(currentState) {
			case rotationControl:
				if(rotationFirstIteration) {
					targetColor = ColorSpinner.getMatch();
					rotationFirstIteration = false;
					onTargetColor = false;
				} else {
					ColorSpinner.spin(0.5);
					if(ColorSpinner.getMatch() == targetColor) {
						if(!onTargetColor) {
							currentRotations += 0.5;
							onTargetColor = true;
						}
					} else {
						if(onTargetColor) {
							onTargetColor = false;
						}
					}
					if(currentRotations > targetRotations) {
						ColorSpinner.spin(0);
						currentState = driveStates.drive;
					}

				}

			break;

			case positionControl:
				if(ColorSpinner.getMatch() == targetColor) {
					ColorSpinner.spin(0);
					currentState = driveStates.drive;
				} else {
					ColorSpinner.spin(0.5);
				}
				Robot.driveBase.Drive(driveJoystick.getLeftStickY() / 2, driveJoystick.getRightStickX() / 2);
			break;

			case demo:
				if(driveJoystick.isRShoulderButton()) {
					if(gearSwitchPress) {

					} else {
						if(gear) {
							s1.set(false);
							gear = false;
						} else {
							s1.set(true);
							gear = true;
						}
						gearSwitchPress = true;
					}
				} else {
					gearSwitchPress = false;
				}
				targetRPM = 500 + driveJoystick.getRightTrigger() * 6000;
				targetRPMdistance = targetRPM - Robot.driveBase.getPeakRPM();
				SmartDashboard.putNumber("Peak RPM", Robot.driveBase.getPeakRPM());
				currentTargetSpeed += targetRPMdistance / 65000;
				if(currentTargetSpeed > 1) {
					currentTargetSpeed = 1;
				} else if(currentTargetSpeed < -1) {
					currentTargetSpeed = -1;
				}
			Robot.driveBase.Drive(currentTargetSpeed, 0.0);
			break;

			default:
				Robot.driveBase.Drive(driveJoystick.getLeftStickY(), driveJoystick.getRightStickX() / 2);
				if(driveJoystick.isXButton()) {
					currentState = driveStates.positionControl;
					TurretControl.currentState = TurretControl.turretStates.idle;
					targetColor = ColorSpinner.blue;
				}
				if(driveJoystick.isBButton()) {
					targetRotations = 2;
					TurretControl.currentState = TurretControl.turretStates.seek;
					currentState = driveStates.rotationControl;
				}
				if(driveJoystick.isYButton()) {
					currentState = driveStates.demo;
				}
				if(driveJoystick.isRShoulderButton()) {
					if(gearSwitchPress) {

					} else {
						if(gear) {
							s1.set(false);
							gear = false;
						} else {
							s1.set(true);
							gear = true;
						}
						gearSwitchPress = true;
					}
				} else {
					gearSwitchPress = false;
				}
			break;
		}

		 // Drive with set values
	}

	// Returns true if command finished
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {

	}

	// Called when another command tries to use this command's subsystem
	@Override
	protected void interrupted() {

	}
}
