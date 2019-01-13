package org.usfirst.frc.team126.robot.commands;

import org.usfirst.frc.team126.robot.Robot;
import org.usfirst.frc.team126.robot.RobotMap;
import edu.wpi.first.wpilibj.command.Command;

public class DriveWithJoysticks extends Command {
	double fb, lr, rot, tl, tr;
	int smoothFactor = 5;
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
		xboxA = Robot.oi.driveController.getRawButton(RobotMap.xboxA);
		xboxB = Robot.oi.driveController.getRawButton(RobotMap.xboxB);
		xboxX = Robot.oi.driveController.getRawButton(RobotMap.xboxX);
		xboxY = Robot.oi.driveController.getRawButton(RobotMap.xboxY);
		xboxLTrig = Robot.oi.driveController.getRawButton(RobotMap.xboxLTrig);
		xboxRTrig = Robot.oi.driveController.getRawButton(RobotMap.xboxRTrig);
		xboxLStick = Robot.oi.driveController.getRawButton(RobotMap.xboxLStick);
		xboxRStick = Robot.oi.driveController.getRawButton(RobotMap.xboxRStick);
		
		if(xboxA) {
			smoothFactor = 5;
		}
		if(xboxB) {
			smoothFactor = 10;
		}
		if(xboxX) {
			smoothFactor = 20;
		}
		if(xboxY) {
			smoothFactor = 40;
		}

		if(tr < 0) {
			lr = tr;
		}
		else {
			lr = tl * -1;
		}

		if(Math.abs(rot) < 0.1) {
			rot = 0;
		}
		fb = 0;
		rot = 0;
		isCurved = false;
		isSmoothed = false;
		smoothFactor = 5;
		Robot.driveBase.Drive(fb, 0, rot, isCurved, isSmoothed, smoothFactor);
		Robot.intake.setIntake(lr);

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
