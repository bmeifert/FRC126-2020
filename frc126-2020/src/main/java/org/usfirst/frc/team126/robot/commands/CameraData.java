package org.usfirst.frc.team126.robot.commands;

import org.usfirst.frc.team126.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CameraData extends Command {
    int loop_count=0;

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
		//Turn on the LED's on the PixyCam 
		//Robot.vision.setLamp(True,True);

		// Track Specified object ID
		// 1 - Power Cell
		// 2 - Throwing Target
		// 
		// TODO hook this up to a button or something so that we
		// track the power cell, or the throwing target
		int objectId=1;

		// Get the data for requested object from the camera
		Robot.vision.getItems(objectId,1);

		// Report the object data to the smart dashboard.
		SmartDashboard.putNumber("Vision X: ", Robot.vision.packetData[objectId].X);
		SmartDashboard.putNumber("Vision Y: ", Robot.vision.packetData[objectId].Y);
		SmartDashboard.putNumber("Vision H: ", Robot.vision.packetData[objectId].Height);
		SmartDashboard.putNumber("Vision W: ", Robot.vision.packetData[objectId].Width);
		SmartDashboard.putBoolean("Vision V: ", Robot.vision.packetData[objectId].isValid);
		SmartDashboard.putNumber("Servo X: ", Robot.vision.getServoX());
		SmartDashboard.putNumber("Servo Y: ", Robot.vision.getServoY());

	    if (Robot.vision.packetData[objectId].isValid) {
			// If the object is valid then turn on the LED and use the servos
			// to center the object in the camera view
			Robot.vision.setLED(0,255,0); 

			if (Robot.vision.packetData[objectId].Y < 80) {
				// if the object is below the center of the camera, move the
				// camera down
				Robot.vision.incrServoY(-15);
			}
			if (Robot.vision.packetData[objectId].Y > 120) {
				// if the object is above the center of the camera, move the
				// camera up
				Robot.vision.incrServoY(15);
			}
			if (Robot.vision.packetData[objectId].X < 145) {
				// if the object is to the left of the center of the camera, move the
				// camera left
				Robot.vision.incrServoX(15);
			}
			if (Robot.vision.packetData[objectId].X > 170) {
				// if the object is to the right of the center of the camera, move the
				// camera right
				Robot.vision.incrServoX(-15);
			}

			loop_count=0;
		} else {
			// Set the LED to signify object was not found.
			Robot.vision.setLED(255,0,0); 

			if (loop_count++ > 250) {
				// After 250 iterations of not seeing an object, recenter the camera.
				Robot.vision.centerServo();
			}
		}

		// Set the calculated servo position 
		Robot.vision.setServo();
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
