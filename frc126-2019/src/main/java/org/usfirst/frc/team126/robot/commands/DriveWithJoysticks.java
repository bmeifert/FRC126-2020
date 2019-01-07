package org.usfirst.frc.team126.robot.commands;

import org.usfirst.frc.team126.robot.Robot;
import org.usfirst.frc.team126.robot.RobotMap;
import edu.wpi.first.wpilibj.command.Command;

public class DriveWithJoysticks extends Command {
	double fb, lr, rot, tl, tr;
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
		fb = Robot.oi.driveController.getRawAxis(RobotMap.lStickY); // Forward and backward movement (Left stick Y)
		tl = Robot.oi.driveController.getRawAxis(RobotMap.Ltrigger) * -1; // Left trigger (for strafe L)
		tr = Robot.oi.driveController.getRawAxis(RobotMap.Rtrigger) * -1; // Right trigger (for strafe R)
		rot = Robot.oi.driveController.getRawAxis(RobotMap.rStickX) * -1; // Rotation (Right stick X)

		if(tr < 0) {
			lr = tr;
		}
		else {
			lr = tl * -1;
		}
		if(Math.abs(rot) < 0.1) {
			rot = 0;
		}

		Robot.driveBase.Drive(fb, lr, rot, isCurved, isSmoothed);

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
