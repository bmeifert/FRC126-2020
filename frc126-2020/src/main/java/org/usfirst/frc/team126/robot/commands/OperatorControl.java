package org.usfirst.frc.team126.robot.commands;

import org.usfirst.frc.team126.robot.Robot;
import org.usfirst.frc.team126.robot.subsystems.ColorSpinner;
import org.usfirst.frc.team126.robot.subsystems.Log;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.util.Color;
public class OperatorControl extends Command {	
	public static enum driveStates{drive, rotationControl, positionControl, chassis, targetSeek};
	public static driveStates currentState = driveStates.targetSeek;
	public static Color targetColor;
	public static double targetRotations;
	static double currentRotations;
	static boolean rotationFirstIteration = true;
	static boolean onTargetColor;
	int count=0;
	int targetLightCount=0;

	public OperatorControl() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.driveBase);
	}

	// Run before command starts 1st iteration
	@Override
	protected void initialize() {
		Log.print(0, "OI", "Operator control initialized.");
	}

	// Called every tick (20ms)
	@SuppressWarnings("static-access")
	@Override
	protected void execute() {
		// START CONTROL SETUP

		count++;

		// Get stick inputs
		JoystickWrapper driveJoystick = new JoystickWrapper(Robot.oi.driveController, 0.05);
		JoystickWrapper operatorJoystick = new JoystickWrapper(Robot.oi.operatorController, 0.05);

		// END CONTROLS SETUP
		//if(driveJoystick.isAButton()) {
		//	currentState = driveStates.drive;
		//	ColorSpinner.spin(0);
		//}

		//System.out.println("currentState: " + currentState);
		
		switch(currentState) {
			case targetSeek:
		    	if(!driveJoystick.isYButton() && !driveJoystick.isXButton()) {
					currentState = driveStates.drive;
					Robot.trackTarget= Robot.targetTypes.noTarget;
					Robot.robotTurn = 0;
					Robot.robotDrive = 0;
					Robot.limeLight.setLED(true);
				} else {
					if (driveJoystick.isYButton()) {
						Robot.trackTarget= Robot.targetTypes.ballTarget;
					} else {
						Robot.trackTarget= Robot.targetTypes.throwingTarget;
					}
                    System.out.println("Direction: " + Robot.robotTurn + " Drive: " + Robot.robotDrive );
			 	    Robot.driveBase.Drive(Robot.robotDrive,Robot.robotTurn);
		        }
			break;

			case rotationControl:
				if(rotationFirstIteration) {
					targetColor = ColorSpinner.getMatch();
					rotationFirstIteration = false;
					onTargetColor = false;
				} else {
					ColorSpinner.spin(0.5);
					if(ColorSpinner.getMatch() == targetColor) {
						if(!onTargetColor) {
							currentRotations += 0.5;
							onTargetColor = true;
						}
					} else {
						if(onTargetColor) {
							onTargetColor = false;
						}
					}
					if(currentRotations > targetRotations) {
						ColorSpinner.spin(0);
						currentState = driveStates.drive;
					}

				}

			break;

			case positionControl:
				if(ColorSpinner.getMatch() == targetColor) {
					ColorSpinner.spin(0);
					currentState = driveStates.drive;
				} else {
					ColorSpinner.spin(0.5);
				}
				Robot.driveBase.Drive(driveJoystick.getLeftStickY() / 2, driveJoystick.getRightStickX() / 2);
			break;

			case chassis:
				Robot.driveBase.Drive(driveJoystick.getTriggers() * -1, driveJoystick.getLeftStickX() / 2);
			break;

			default:
				if(driveJoystick.isXButton()) {
					Robot.driveBase.Drive(0,0);
					currentState = driveStates.targetSeek;
					Robot.trackTarget= Robot.targetTypes.throwingTarget;
					Robot.limeLight.setStreamMode(0);
				    // turn on the LEDs on the lime light
					Robot.limeLight.setLED(true);
					// Reset any previous motion
					Robot.robotTurn = 0;
					Robot.robotDrive = 0;
					return;
				}	
				
				if (driveJoystick.isYButton()) {
					Robot.driveBase.Drive(0,0);
					currentState = driveStates.targetSeek;
					Robot.trackTarget= Robot.targetTypes.ballTarget;
				    // turn off the LEDs on the lime light
					Robot.limeLight.setLED(false);
					// Reset any previous motion
					Robot.robotTurn = 0;
					Robot.robotDrive = 0;
					return;
				}

				Robot.driveBase.Drive(driveJoystick.getLeftStickY(), driveJoystick.getRightStickX() / 2);
				if (driveJoystick.isAButton()) {
					if (count > targetLightCount + 50) {
						Robot.tLight.toggleTargetLight();
						targetLightCount = count;
					}	
				}	
			break;
		}

		 // Drive with set values
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
