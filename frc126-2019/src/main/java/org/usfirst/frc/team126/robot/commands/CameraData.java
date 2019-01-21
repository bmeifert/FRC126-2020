package org.usfirst.frc.team126.robot.commands;

import org.usfirst.frc.team126.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team126.robot.subsystems.Vision;

public class CameraData extends Command {
	public CameraData() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.vision);
	}

	// Run before command starts 1st iteration
	@Override
	protected void initialize() {
	}

	// Called every tick (20ms)
	@SuppressWarnings("static-access")
	@Override
	protected void execute() {
		Robot.vision.testPixy();
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
