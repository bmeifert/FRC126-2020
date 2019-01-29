package org.usfirst.frc.team126.robot.commands;

import org.usfirst.frc.team126.robot.Robot;
import org.usfirst.frc.team126.robot.RobotMap;
import edu.wpi.first.wpilibj.command.Command;

public class DriveWithJoysticks extends Command {
	double fb, lr, rot, tl, tr;
	int smoothFactor = 5;
	boolean xboxLTrig, xboxRTrig, xboxA, xboxB, xboxX, xboxY, xboxLStick, xboxRStick;
	boolean isCurved = true;
	boolean isSmoothed = true;
	public DriveWithJoysticks() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.driveBase);
	}

	// Run before command starts 1st iteration
	@Override
	protected void initialize() {
			
	}

	// Called every tick (20ms)
	@SuppressWarnings("static-access")
	@Override
	protected void execute() {
		fb = Robot.oi.driveController.getRawAxis(RobotMap.lStickY) * -1; // Forward and backward movement (Left stick Y)
		tl = Robot.oi.driveController.getRawAxis(RobotMap.Ltrigger); // Left trigger (for strafe L)
		tr = Robot.oi.driveController.getRawAxis(RobotMap.Rtrigger); // Right trigger (for strafe R)
		rot = Robot.oi.driveController.getRawAxis(RobotMap.rStickX); // Rotation (Right stick X)
		xboxA = Robot.oi.driveController.getRawButton(RobotMap.xboxA); // Xbox A button
		xboxB = Robot.oi.driveController.getRawButton(RobotMap.xboxB); // Xbox B button
		xboxX = Robot.oi.driveController.getRawButton(RobotMap.xboxX); // Xbox X button
		xboxY = Robot.oi.driveController.getRawButton(RobotMap.xboxY); // Xbox Y button
		xboxLTrig = Robot.oi.driveController.getRawButton(RobotMap.xboxLTrig); // Xbox L Shoulder button
		xboxRTrig = Robot.oi.driveController.getRawButton(RobotMap.xboxRTrig); // Xbox R Shoulder button
		xboxLStick = Robot.oi.driveController.getRawButton(RobotMap.xboxLStick); // Xbox L stick press down
		xboxRStick = Robot.oi.driveController.getRawButton(RobotMap.xboxRStick); // Xbox R stick press down
		
		if(xboxA) { // Set different drive spike smoothing factors (for testing)
			smoothFactor = 5;
		}
		if(xboxB) {
			smoothFactor = 10;
		}
		if(xboxX) {
			smoothFactor = 20;
		}
		if(xboxY) {
			smoothFactor = 40;
		}

		if(tr > 0) {
			lr = tr;
		}
		else {
			lr = tl * -1;
		}

		if(Math.abs(rot) < 0.05) { // Prevent rotation drifting
			rot = 0;
		}
		if(Math.abs(fb) < 0.05) {
			fb = 0;
		}
		Robot.driveBase.Drive(fb, rot, isCurved, isSmoothed, smoothFactor); // Drive with set values

		Robot.intake.setIntake(lr); // Set intake to triggers
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
