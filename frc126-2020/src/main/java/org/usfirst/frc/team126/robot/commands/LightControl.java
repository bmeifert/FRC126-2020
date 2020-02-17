package org.usfirst.frc.team126.robot.commands;

import org.usfirst.frc.team126.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LightControl extends Command {
    public LightControl() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.tLight);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        Robot.tLight.setLight(1);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

}