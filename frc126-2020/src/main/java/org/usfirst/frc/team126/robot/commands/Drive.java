package org.usfirst.frc.team126.robot.commands;

import org.usfirst.frc.team126.robot.Robot;
import org.usfirst.frc.team126.robot.subsystems.InternalData;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Drive extends Command {
    double driveFb;
    double driveLr;
    double targetAngle;
    public Drive(double fb, double lr) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.driveBase);
        driveFb = fb;
        driveLr = lr;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        targetAngle = InternalData.getGyroAngle();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if(driveLr == 0) {
            if(InternalData.getGyroAngle() - targetAngle > 1) {
                Robot.driveBase.Drive(driveFb, -0.1);
            }
            else if(InternalData.getGyroAngle() - targetAngle < -1) {
                Robot.driveBase.Drive(driveFb, 0.1);
            } else {
                Robot.driveBase.Drive(driveFb, 0);
            }
        } else {
            Robot.driveBase.Drive(driveFb, driveLr);
            
        }
 
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
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