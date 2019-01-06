package org.usfirst.frc.team126.robot.commands;

import org.usfirst.frc.team126.robot.Robot;
import org.usfirst.frc.team126.robot.RobotMap;
import edu.wpi.first.wpilibj.command.Command;

public class DriveWithJoysticks extends Command {
	double rot;
	boolean xboxLT, xboxRT, xboxRS, xboxLS, camMode, enabled;
	public DriveWithJoysticks() {
		requires(Robot.driveBase);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
			
	}

	// Called repeatedly when this Command is scheduled to run
	@SuppressWarnings("static-access")
	@Override
	protected void execute() {
		rot = Robot.oi.driveController.getRawAxis(RobotMap.contLR); // Get and assign controller values
		if(Math.abs(rot) < 0.1) {
			rot = 0;
		}
		Robot.driveBase.Drive(0.0,rot,1); // Drive with standard inversion offsets

	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}
