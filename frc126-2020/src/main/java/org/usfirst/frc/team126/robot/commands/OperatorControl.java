package org.usfirst.frc.team126.robot.commands;

import org.usfirst.frc.team126.robot.Robot;
import org.usfirst.frc.team126.robot.RobotMap;
import org.usfirst.frc.team126.robot.subsystems.ColorSpinner;
import org.usfirst.frc.team126.robot.subsystems.Log;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.util.Color;
public class OperatorControl extends Command {
	double lx, ly, rx, ry, tl, tr; // Control values for drive controller
	double lx2, ly2, rx2, ry2, tl2, tr2, pov1, pov2; // Control values for operator controller
	double camX, camY, camV, maxSpeed, trigs1, trigs2; // Misc control values
	boolean xboxLTrig, xboxRTrig, xboxA, xboxB, xboxX, xboxY, xboxLStick, xboxRStick; // Button values for drive controller
	boolean xboxLTrig2, xboxRTrig2, xboxA2, xboxB2, xboxX2, xboxY2, xboxLStick2, xboxRStick2; // Button values for operator controller
	
	public static enum driveStates{drive, rotationControl, positionControl, chassis};
	public static driveStates currentState;
	public static Color targetColor;
	public static double targetRotations;
	static double currentRotations;
	static boolean rotationFirstIteration = true;
	static boolean onTargetColor;

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

		// Get stick inputs -- this does not need to be modified year to year
		ly = Robot.oi.driveController.getRawAxis(RobotMap.lStickY) * -1; // Left stick Y
		lx = Robot.oi.driveController.getRawAxis(RobotMap.lStickX); // Left stick X
		tl = Robot.oi.driveController.getRawAxis(RobotMap.Ltrigger); // Left trigger
		tr = Robot.oi.driveController.getRawAxis(RobotMap.Rtrigger); // Right trigger
		ry = Robot.oi.driveController.getRawAxis(RobotMap.rStickY) * -1; // Right stick Y
		rx = Robot.oi.driveController.getRawAxis(RobotMap.rStickX); // Right stick X
		xboxA = Robot.oi.driveController.getRawButton(RobotMap.xboxA); // Xbox A button
		xboxB = Robot.oi.driveController.getRawButton(RobotMap.xboxB); // Xbox B button
		xboxX = Robot.oi.driveController.getRawButton(RobotMap.xboxX); // Xbox X button
		xboxY = Robot.oi.driveController.getRawButton(RobotMap.xboxY); // Xbox Y button
		xboxLTrig = Robot.oi.driveController.getRawButton(RobotMap.xboxLTrig); // Xbox L Shoulder button
		xboxRTrig = Robot.oi.driveController.getRawButton(RobotMap.xboxRTrig); // Xbox R Shoulder button
		xboxLStick = Robot.oi.driveController.getRawButton(RobotMap.xboxLStick); // Xbox L stick press down
		xboxRStick = Robot.oi.driveController.getRawButton(RobotMap.xboxRStick); // Xbox R stick press down
		ly2 = Robot.oi.operatorController.getRawAxis(RobotMap.lStickY) * -1; // Same but for operator controller
		lx2 = Robot.oi.operatorController.getRawAxis(RobotMap.lStickX);
		tl2 = Robot.oi.operatorController.getRawAxis(RobotMap.Ltrigger);
		tr2 = Robot.oi.operatorController.getRawAxis(RobotMap.Rtrigger);
		ry2 = Robot.oi.operatorController.getRawAxis(RobotMap.rStickY) * -1; // Y's have to be inverted
		rx2 = Robot.oi.operatorController.getRawAxis(RobotMap.rStickX);
		xboxA2 = Robot.oi.operatorController.getRawButton(RobotMap.xboxA);
		xboxB2 = Robot.oi.operatorController.getRawButton(RobotMap.xboxB);
		xboxX2 = Robot.oi.operatorController.getRawButton(RobotMap.xboxX);
		xboxY2 = Robot.oi.operatorController.getRawButton(RobotMap.xboxY);
		xboxLTrig2 = Robot.oi.operatorController.getRawButton(RobotMap.xboxLTrig);
		xboxRTrig2 = Robot.oi.operatorController.getRawButton(RobotMap.xboxRTrig);
		xboxLStick2 = Robot.oi.operatorController.getRawButton(RobotMap.xboxLStick);
		xboxRStick2 = Robot.oi.operatorController.getRawButton(RobotMap.xboxRStick);
		pov1 = Robot.oi.driveController.getPOV();
		pov2 = Robot.oi.operatorController.getPOV();

		if(Math.abs(lx) < 0.05) { // Prevent control drifting (driver controller)
			lx = 0;
		}
		if(Math.abs(ly) < 0.05) {
			ly = 0;
		}
		if(Math.abs(rx) < 0.05) {
			rx = 0;
		}
		if(Math.abs(ry) < 0.05) {
			ry = 0;
		}
		if(Math.abs(tl) < 0.05) {
			tl = 0;
		}
		if(Math.abs(tr) < 0.05) {
			tr = 0;
		}
		if(Math.abs(lx2) < 0.05) { // Prevent control drifting (operator controller)
			lx2 = 0;
		}
		if(Math.abs(ly2) < 0.05) {
			ly2 = 0;
		}
		if(Math.abs(rx2) < 0.05) {
			rx2 = 0;
		}
		if(Math.abs(ry2) < 0.05) {
			ry2 = 0;
		}
		if(Math.abs(tl2) < 0.05) {
			tl2 = 0;
		}
		if(Math.abs(tr2) < 0.05) {
			tr2 = 0;
		}

		if(tr > 0) {
			trigs1 = tr;
		} else {
			trigs1 = tl * -1;
		}

		if(tr2 > 0) {
			trigs2 = tr2;
		} else {
			trigs2 = tl2 * -1;
		}
		// END CONTROLS SETUP
		if(xboxA) {
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
				Robot.driveBase.Drive(ly / 2, rx / 2);
			break;

			case chassis:
				Robot.driveBase.Drive(trigs1 * -1, lx / 2);
			break;

			default:
				Robot.driveBase.Drive(ly, rx / 2);
				if(xboxX) {
					currentState = driveStates.positionControl;
					TurretControl.currentState = TurretControl.turretStates.idle;
					targetColor = ColorSpinner.blue;
				}
				if(xboxY) {
					targetRotations = 2;
					TurretControl.currentState = TurretControl.turretStates.seek;
					currentState = driveStates.rotationControl;
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
