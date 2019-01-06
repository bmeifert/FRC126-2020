package org.usfirst.frc.team126.robot.commands;

import org.usfirst.frc.team126.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class Pause extends Command {

    public Pause() {
        // Use requires() here to declare subsystem dependencies
    	requires(Robot.driveBase);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called every tick (20ms)
    protected void execute() {
    	Robot.driveBase.Drive(0, 0,1);
    	
    }

    // Returns true if command finished
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command tries to use this command's subsystem
    protected void interrupted() {
    }
}
