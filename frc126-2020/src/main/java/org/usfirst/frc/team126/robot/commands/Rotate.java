package org.usfirst.frc.team126.robot.commands;

import org.usfirst.frc.team126.robot.Robot;
import org.usfirst.frc.team126.robot.subsystems.InternalData;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Rotate extends Command {
    double degrees;
    double initialAngle;
    double targetAngle;
    public Rotate(double deg) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.driveBase);
        degrees = deg;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        initialAngle = InternalData.getGyroAngle();
        targetAngle = initialAngle + degrees;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if(targetAngle - InternalData.getGyroAngle() > 1) {
            Robot.driveBase.Drive(0, 0.25);
        } else if(targetAngle - InternalData.getGyroAngle() < -1) {
            Robot.driveBase.Drive(0, -0.25);
        } else {
            Robot.driveBase.Drive(0,0);
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if(Math.abs(targetAngle - InternalData.getGyroAngle()) < 1 ) {
            return true;
        } else {
            return false;
        }
    }

    // Called once after isFinished returns true
    protected void end() {
        Robot.driveBase.Drive(0, 0);

    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        Robot.driveBase.Drive(0, 0);
    }
}