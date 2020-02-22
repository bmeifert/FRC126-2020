package org.usfirst.frc.team126.robot.commands;

import org.usfirst.frc.team126.robot.Robot;
import org.usfirst.frc.team126.robot.subsystems.Log;

import edu.wpi.first.wpilibj.command.Command;
public class LiftControl extends Command {	
	private boolean isClampButtonPressed = false;
	private boolean isClamped = false;

	public LiftControl() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.lift);
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
		JoystickWrapper liftJoystick = new JoystickWrapper(Robot.oi.liftController, 0.05);
		Robot.lift.liftArm(liftJoystick.getLeftStickY());
		Robot.lift.slideOnArm(liftJoystick.getLeftStickX());

		if (liftJoystick.isAButton()) {
			if (!isClampButtonPressed) {
				// toggle clamp or unclamped state
				isClamped = !isClamped;

				// clamp or unclamp now
				Robot.lift.clamp(isClamped);

				// don't keep toggling until clamp button released
				isClampButtonPressed = true;	
			}
		} else {
			isClampButtonPressed = false;
		}
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
