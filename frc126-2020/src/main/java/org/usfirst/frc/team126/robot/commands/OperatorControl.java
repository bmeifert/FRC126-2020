package org.usfirst.frc.team126.robot.commands;

import org.usfirst.frc.team126.robot.Robot;
import org.usfirst.frc.team126.robot.subsystems.ColorSpinner;
import org.usfirst.frc.team126.robot.subsystems.Log;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.util.Color;
public class OperatorControl extends Command {	
	public static enum driveStates{drive, rotationControl, positionControl, targetSeek};
	public static driveStates currentState = driveStates.drive;
	public static Color targetColor;
	public static double targetRotations;
	static double targetRPM, targetRPMdistance, currentTargetSpeed;
	static double currentRotations;
	static boolean rotationFirstIteration = true;
	static boolean onTargetColor;
	

	static boolean gearSwitchPress = false;
	static boolean gear = false;

  	static int count=0;
	static int targetLightCount=0;

	public OperatorControl() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.driveBase);
	}

	// Run before command starts 1st iteration
	@Override
	protected void initialize() {
		Log.print(0, "OI", "Operator control initialized.");
		Robot.limeLight.setStreamMode(0);
	}

	public static void resetDriveBase() {
		Robot.driveBase.Drive(0,0);
		Robot.robotTurn = 0;
		Robot.robotDrive = 0;
	}

	private double collisionAvoidance(double inSpeed) {
		/*
		if (Robot.distance.getDistanceAvg() < 10 && inSpeed > 0) {
			return 0;
		}	

		if (Robot.distance.getDistanceAvg() < 20 && inSpeed > .25) {
			return .25;
		}	

		if (Robot.distance.getDistanceAvg() < 50 && inSpeed > .50) {
			return .5;
		}	
		*/

		// TODO Enable this at some point

		return inSpeed;
	}

	// Called every tick (20ms)
	@SuppressWarnings("static-access")
	@Override
	protected void execute() {
		// START CONTROL SETUP
		// Get stick inputs
		JoystickWrapper driveJoystick = new JoystickWrapper(Robot.oi.driveController, 0.05);
		JoystickWrapper operatorJoystick = new JoystickWrapper(Robot.oi.operatorController, 0.05);
		// END CONTROLS SETUP

		//System.out.println("currentState: " + currentState);
		count++;
		if(driveJoystick.isAButton()) {
			currentState = driveStates.drive;
		}
		switch(currentState) {
			case targetSeek:
		    	if(!driveJoystick.isYButton() && !driveJoystick.isXButton() && !driveJoystick.isBButton()) {
					currentState = driveStates.drive;
					Robot.trackTarget= Robot.targetTypes.noTarget;
					resetDriveBase();
					Robot.limeLight.setLED(true);
				} else {
					if (Robot.trackTarget != Robot.targetTypes.turretOnly) {
						System.out.println("Direction: " + Robot.robotTurn + " Drive: " + Robot.robotDrive );
						if (Robot.robotDrive == 0 && Robot.robotTurn == 0) {
							Robot.driveBase.Drive(collisionAvoidance(driveJoystick.getLeftStickY()), 
							                      driveJoystick.getRightStickX() / 2);
						} else {
							Robot.driveBase.Drive(collisionAvoidance(Robot.robotDrive),Robot.robotTurn);
						}	 
					} else {
						Robot.driveBase.Drive(collisionAvoidance(driveJoystick.getLeftStickY()),
						                      driveJoystick.getRightStickX() / 2);
					}
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

			default:
				if(driveJoystick.isXButton()) {
					resetDriveBase();
					currentState = driveStates.targetSeek;
					Robot.trackTarget= Robot.targetTypes.throwingTarget;
				    // turn on the LEDs on the lime light
					Robot.limeLight.setLED(true);
					return;
				}	

				/*
				if (driveJoystick.isYButton()) {
					resetDriveBase();
					currentState = driveStates.targetSeek;
 
					boolean llball=false;
					if (!llball) {
						Robot.trackTarget= Robot.targetTypes.ballTarget;
				    	// turn off the LEDs on the lime light
						Robot.limeLight.setLED(false);
						// Reset any previous motion
					} else {
						Robot.trackTarget= Robot.targetTypes.ballLLTarget;
						Robot.limeLight.setLED(true);
					}	
					return;
				}
				*/

				if (driveJoystick.isBButton()) {
					resetDriveBase();
					currentState = driveStates.targetSeek;
					Robot.trackTarget= Robot.targetTypes.turretOnly;
					Robot.limeLight.setLED(true);
					return;
				}

				if(operatorJoystick.isRShoulderButton()) {
					Robot.solenoids.extendLoader();
					Robot.solenoids.foldLoader();
				}
				if(operatorJoystick.isLShoulderButton()) {
					Robot.solenoids.retractLoader();
					Robot.solenoids.unfoldLoader();
				}

				if(operatorJoystick.isLStickPressButton()) {
					Robot.turret.zeroHood();
				} else {
					Robot.turret.moveHood(operatorJoystick.getLeftStickY() / 4);
				}
				
				Robot.cargoHandler.setLoadMotor(0 - operatorJoystick.getTriggers());

				if(operatorJoystick.isAButton()) {
					Robot.cargoHandler.runShooter();
				} else {
					Robot.cargoHandler.stopShooter();
				}
				if(operatorJoystick.getPovUp()) {
					Robot.cargoHandler.runPickup();
				} else if(operatorJoystick.getPovDown()) {
					Robot.cargoHandler.runPickupReverse();
				} else {
					Robot.cargoHandler.stopPickup();
				}
        
				if(driveJoystick.isRShoulderButton()) {
					Robot.solenoids.upshift();
				}
				if(driveJoystick.isLShoulderButton()) {
					Robot.solenoids.downshift();
				}
				if(operatorJoystick.isRStickPressButton()) {
					Robot.turret.zeroRotator();
				} else {
					Robot.turret.Rotate(0 - operatorJoystick.getRightStickX() / 6);
				}
				

				Robot.driveBase.Drive(driveJoystick.getLeftStickY(), driveJoystick.getRightStickX() / 2);

				if (driveJoystick.isAButton()) {
				    // Toggle the target light
					if (count > targetLightCount + 25) {
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
