package org.usfirst.frc.team126.robot.commands;

import org.usfirst.frc.team126.robot.Robot;
import org.usfirst.frc.team126.robot.RobotMap;
import org.usfirst.frc.team126.robot.subsystems.Lift.liftPos;
import edu.wpi.first.wpilibj.command.Command;
public class OperatorControl extends Command {
	double lx, ly, rx, ry, tl, tr; // Control values for drive controller
	double lx2, ly2, rx2, ry2, tl2, tr2; // Control values for operator controller
	double camX, camY, camV, maxSpeed, turnco; // Misc control values
	boolean xboxLTrig, xboxRTrig, xboxA, xboxB, xboxX, xboxY, xboxLStick, xboxRStick; // Button values for drive controller
	boolean xboxLTrig2, xboxRTrig2, xboxA2, xboxB2, xboxX2, xboxY2, xboxLStick2, xboxRStick2; // Button values for operator controller

	boolean isCurved = true;
	boolean isSmoothed = true;
	int smoothFactor = 5;
	public OperatorControl() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.driveBase);
	}

	// Run before command starts 1st iteration
	@Override
	protected void initialize() {
		Robot.lift.resetLift();
		Robot.lift.setTargetPos(liftPos.free, true);
	}

	// Called every tick (20ms)
	@SuppressWarnings("static-access")
	@Override
	protected void execute() {
		ly = Robot.oi.driveController.getRawAxis(RobotMap.lStickY) * -1; // Left stick Y
		lx = Robot.oi.driveController.getRawAxis(RobotMap.lStickY); // Left stick X
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
		ry2 = Robot.oi.operatorController.getRawAxis(RobotMap.rStickY);
		rx2 = Robot.oi.operatorController.getRawAxis(RobotMap.rStickX);
		xboxA2 = Robot.oi.operatorController.getRawButton(RobotMap.xboxA);
		xboxB2 = Robot.oi.operatorController.getRawButton(RobotMap.xboxB);
		xboxX2 = Robot.oi.operatorController.getRawButton(RobotMap.xboxX);
		xboxY2 = Robot.oi.operatorController.getRawButton(RobotMap.xboxY);
		xboxLTrig2 = Robot.oi.operatorController.getRawButton(RobotMap.xboxLTrig);
		xboxRTrig2 = Robot.oi.operatorController.getRawButton(RobotMap.xboxRTrig);
		xboxLStick2 = Robot.oi.operatorController.getRawButton(RobotMap.xboxLStick);
		xboxRStick2 = Robot.oi.operatorController.getRawButton(RobotMap.xboxRStick);

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
		/*
		if(xboxA2) { // Set different lift values (for testing)
			Robot.lift.setTargetPos(liftPos.free, true);
		}
		if(xboxB2) {
			Robot.lift.setTargetPos(liftPos.zero, false);
		}
		if(xboxX2) {
			Robot.lift.setTargetPos(liftPos.first, false);
		}
		if(xboxY2) {
			Robot.lift.setTargetPos(liftPos.second, false);
		}
		*/
		if(xboxX) {
			Robot.intake.setIntake(1, true);
		}
		if(xboxY) {
			Robot.intake.setIntake(-1, true);
		}
		else {
			Robot.intake.setIntake(0, false);
		}

		if(tr > 0) {
			turnco = tr;
		}
		else {
			turnco = tl * -1;
		}


		if(xboxLTrig) {
			camX = Robot.vision.getPacketData(1, "x");
			camY = Robot.vision.getPacketData(1, "y");
			camV = Robot.vision.getPacketData(1, "v");
			if(camV == 1) {
				if(camX > 165) {
					Robot.driveBase.Drive(0, 0.05 + (camX - 165) / 300, false, true, 5);
				}
				else if(camX < 135) {
					Robot.driveBase.Drive(0, -0.05 + (camX - 135) / 300, false, true, 5);
				}
				else {
					Robot.driveBase.Drive(0, 0, false, true, 5);
				}
				Robot.log.print(0, "OperatorControl", "ASSIST TAKEOVER");
			}
			else {
				Robot.log.print(1, "OperatorControl", "ASSIST FAIL");
				Robot.driveBase.Drive(0, 0, false, true, 5);
			}

		}
		else {
			Robot.driveBase.Drive(ly, rx, isCurved, isSmoothed, smoothFactor); // Drive with set values
		}
		Robot.driveBase.moveLift(turnco); // Move lift (must be called every iteration)
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