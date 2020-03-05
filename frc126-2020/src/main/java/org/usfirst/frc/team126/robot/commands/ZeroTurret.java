package org.usfirst.frc.team126.robot.commands;

import org.usfirst.frc.team126.robot.Robot;
import org.usfirst.frc.team126.robot.subsystems.InternalData;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ZeroTurret extends Command {
    double startPos = 20;
    boolean isDone = false;
    public ZeroTurret() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        Robot.driveBase.Drive(0, 0);
        Robot.turret.Rotate(0);
        Robot.turret.moveHood(0);
        Robot.turret.zeroRotator();
        Robot.turret.setRotatorEncoder(startPos);
        isDone = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        Robot.driveBase.Drive(0, 0);
        if(Robot.turret.getRotatorEncoder() > 0.1) {
            Robot.turret.Rotate(-0.1);
        } else if(Robot.turret.getRotatorEncoder() < -0.1) {
            Robot.turret.Rotate(0.1);
        } else {
            Robot.turret.Rotate(0);
            isDone = true;
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isDone;
    }


    // Called once after isFinished returns true
    protected void end() {
        Robot.driveBase.Drive(0, 0);
        Robot.turret.Rotate(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        Robot.driveBase.Drive(0, 0);
        Robot.turret.Rotate(0);
    }
}