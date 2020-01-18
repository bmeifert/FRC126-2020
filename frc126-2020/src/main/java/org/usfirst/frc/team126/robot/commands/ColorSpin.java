package org.usfirst.frc.team126.robot.commands;

import org.usfirst.frc.team126.robot.Robot;
import org.usfirst.frc.team126.robot.subsystems.ColorData;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.util.Color;

/**
 *
 */
public class ColorSpin extends Command {
	Color targetColor;
	Color currentColor;
    public ColorSpin(Color color) {
		targetColor = color;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
		currentColor = ColorData.getMatch();
		if(currentColor == ColorData.yellow) {
			Robot.driveBase.Drive(0, 0);
		} else if(currentColor == ColorData.green) {
			Robot.driveBase.Drive(0.15, 0);
		} else if(currentColor == ColorData.red) {
			Robot.driveBase.Drive(0.1, -0.25);
		} else if(currentColor == ColorData.blue) {
			Robot.driveBase.Drive(0.1, 0.25);
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