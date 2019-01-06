package org.usfirst.frc.team126.robot.commands;

import org.usfirst.frc.team126.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveForward extends Command {
    public DriveForward() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveBase);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        Robot.driveBase.Drive(0, 0, 1);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        Robot.driveBase.Drive(0.5, 0,1);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
            return false;

    }

    // Called once after isFinished returns true
    protected void end() {
		System.out.println("Drive Forward END");
        Robot.driveBase.Drive(0, 0, 1);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
		System.out.println("Drive Forward Interrupted");
        Robot.driveBase.Drive(0, 0, 1);
    }
}
