package org.usfirst.frc.team126.robot.subsystems;

import java.util.HashMap;

import com.ctre.phoenix.motorcontrol.ControlMode;

import org.usfirst.frc.team126.robot.Robot;
import org.usfirst.frc.team126.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Lift extends Subsystem {
	public static enum liftPos { 
		first, second, third, zero, free
	}
	public static enum liftStates {
		notzeroed, ready, moving, zeroing
	}
	public static enum limitStates {
		bottomLimit, topLimit, ok
	}

	public static HashMap<liftPos, Double> encoderMap = new HashMap<liftPos, Double>();

	public static liftPos targetPos = null;
	public static liftPos currentPos = null;
	public static liftStates lState = null;
	public static limitStates limitState = null;
	public static double encoderVal = 0;
	public static double liftSpeed = 0;
	public static double liftMultiplier = 0;
	public static double previousLiftSpeed = 0;
	public static double periodicDebugCounter = 0;
	public static double targetEncoder = 0;
	public static boolean antiSlide;

	public void initDefaultCommand() {

	}

	public static void resetLift() { // Set everything to default. Will require us to re-zero the lift.

		lState = liftStates.notzeroed;
		targetPos = liftPos.free;
		currentPos = liftPos.free;
		limitState = limitStates.ok;
		liftSpeed = 0;
		encoderVal = 0;

		encoderMap.clear(); // Create mappings with encoder positions
		encoderMap.put(liftPos.first, RobotMap.firstStopPosition);
		encoderMap.put(liftPos.second, RobotMap.secondStopPosition);
		encoderMap.put(liftPos.third, RobotMap.thirdStopPosition);

		System.out.println("INIT LIFT WITH POS "+ encoderMap.get(liftPos.first)+ " " + encoderMap.get(liftPos.second)+ " " + encoderMap.get(liftPos.third)+ " DONE" );
	}

	public static void setTargetPos(liftPos lPos, boolean doForceInterrupt) { // USE THIS to set lift target
		if(lPos == liftPos.zero) {
			lState = liftStates.zeroing;
			targetPos = liftPos.zero;
			currentPos = liftPos.free;
		}
		else if(lState == liftStates.ready) {
			targetPos = lPos;
			if(lPos == liftPos.free) { // When target, state, and current are set to free, you can pass the speed value to MoveLift
				currentPos = liftPos.free;
				lState = liftStates.ready;
			}
			else {
				lState = liftStates.moving; // Unless the lift is free, set it to moving so other functions don't interrupt it
			}

		}
		else if(lState == liftStates.notzeroed){ // if the lift is not zeroed than we aren't moving at all
			Robot.log.print(1, "Lift", "LIFT MOVE FAILED -- LIFT NOT ZEROED");
		}
		else if(doForceInterrupt == true) { // this is generally not the brightest idea unless the driver is directly controlling lift functions
			targetPos = lPos;
			if(lPos == liftPos.free) { // When target, state, and current are set to free, you can pass the speed value to MoveLift
				currentPos = liftPos.free;
			}
			else {
				lState = liftStates.moving; // Unless the lift is free, set it to moving so other functions don't interrupt it
			}
		}
		else { // if the lift is in any other state, e.g. already moving to another position
			Robot.log.print(1, "Lift", "LIFT TARGET FAILED -- LIFT BUSY"); 
		}

	}
	public static void moveLift(double optionalSpeed) { // **MUST** BE CALLED _EVERY_ ITERATION - i cannot stress this enough - schedule it to run *always* 

		if(Robot.liftBottomLimit.get() == false) { // The first thing we should always do is check if we're at a limit
			limitState = limitStates.bottomLimit;
		}
		else if(Robot.liftTopLimit.get() == false) { // TODO we might want to consider additional action when we're at the top
			limitState = limitStates.topLimit;
		}
		else {
			limitState = limitStates.ok; // If we're not at a limit than the limit state is OK
		}
		// TODO encoderVal = latest encoder value
		encoderVal = 0; // TODO CHANGE THIS ONCE WE HAVE A REAL POTENTIOMETER ON THE BOT

		if(targetPos == liftPos.zero) { // Handle lift movement for auto, fastest we should go down is -0.05 so we don't break anything
			setLiftSpeed(-0.05);
		}
		else if(targetPos == liftPos.free) { // Take lift out of auto for operator control -- NOTE: This is semi-dangerous
			if(Math.abs(optionalSpeed) < 0.05) { // Prevent motor drifting
				optionalSpeed = 0.15;
			}
			setLiftSpeed(optionalSpeed);
		}
		else if(currentPos != targetPos && targetPos == liftPos.first || targetPos == liftPos.second || targetPos == liftPos.third) {
			double distanceToTarget = Math.abs(encoderMap.get(targetPos) - encoderVal);
			if(encoderVal < encoderMap.get(targetPos) - 10) { // 2000 = margin of error to move up
				liftMultiplier = 1;
				setLiftSpeed(liftMultiplier);
				//setLiftSpeed(getCurve(distanceToTarget * liftMultiplier));
			}
			else if(encoderVal > encoderMap.get(targetPos) + 10) { // 2000 = margin of error to move down
				liftMultiplier = -1;
				setLiftSpeed(liftMultiplier);
				//setLiftSpeed(getCurve(distanceToTarget * liftMultiplier));

			}
			else { // within margin of error, set current pos to target and stop the lift
				currentPos = targetPos;
				targetPos = liftPos.free;
				lState = liftStates.ready;
				liftMultiplier = 0;
				setLiftSpeed(0);
			}
		}

	}

	public static void setLiftSpeed(double targetSpeed) { // NEVER call this function - it should only be called by moveLift

		if(limitState == limitStates.bottomLimit && targetSpeed < 0) { // Prevent movement below bottom limit
			targetSpeed = 0;
			previousLiftSpeed = 0;
			if(targetPos == liftPos.zero) { // If we're at the bottom limit we're zeroed
				targetPos = liftPos.free;
				currentPos = liftPos.free;
				lState = liftStates.ready;
			}
		}
		else if(limitState == limitStates.topLimit && targetSpeed > 0) { // Prevent movement above top limit
			targetSpeed = 0;
			previousLiftSpeed = 0; // Setting PreviousSpeed to zero prevents lift from jumping once it leaves the limit
		}
		else if(lState != liftStates.moving) { // Our state is not moving, but we might still be ok

			if(lState == liftStates.zeroing) { // If we're zeroing the lift, limit it to 20% speed so it doesn't break anything.
				if(targetSpeed > 0) {
					targetSpeed = 0;
				}
				else if(targetSpeed < 0) {
					targetSpeed = 0;
				}
			}
			/*
			else if(lState == liftStates.notzeroed) { // There are multiple failsafes for this for a good reason
				targetSpeed = 0;
				Robot.log.print(3, "Lift,", "Attempted to move whilst not zeroed");
			}
			*/
			else if(lState == liftStates.ready && currentPos == liftPos.free) {
				// PASS
				// If the operator is controlling the lift then pass through
			}
			else { // If it's saying we should move but we're not in an appropriate state disable just to make sure.
				targetSpeed = 0;
			}
			
		} // If none of the above functions are met, the lift is good for normal movement.


		periodicDebugCounter++;

		if(periodicDebugCounter > 50) {
			Robot.log.print(0, "Lift", "LIFT STATE: "+lState+" TARGET POS: " +targetPos+" CURRENT POS: "+currentPos+" ENCODER: "+encoderVal);
			periodicDebugCounter = 0;
		}

		if(limitState == limitStates.ok) {
			targetSpeed = (previousLiftSpeed * 9 + targetSpeed) / 10;
			previousLiftSpeed = targetSpeed;
		}

		/*
		if(Math.abs(targetSpeed) < 0.05 && lState != liftStates.zeroing && lState != liftStates.notzeroed && targetPos != liftPos.zero) { // If we're drifting down and not zeroing - IT'S BAD
			if(antiSlide = false) {
				antiSlide = true;
				targetEncoder = encoderVal; // stop this gravity madness
			}
			if(encoderVal < targetEncoder) { // fight the gravity - defeat the gravity
				targetSpeed = 0.2;
			}
		}
		else {
			antiSlide = false; // No longer necessary
		}
		*/
		if(targetSpeed > 0.5) {
			targetSpeed = 0.5;
		} else if(targetSpeed < -0.05) {
			targetSpeed = -0.05;
		}
		Robot.leftLift1.set(ControlMode.PercentOutput, targetSpeed * RobotMap.leftLift1Inversion);
		Robot.leftLift2.set(ControlMode.PercentOutput, targetSpeed * RobotMap.leftLift2Inversion);
		Robot.rightLift1.set(ControlMode.PercentOutput, targetSpeed * RobotMap.rightLift1Inversion);
		Robot.rightLift2.set(ControlMode.PercentOutput, targetSpeed * RobotMap.rightLift2Inversion);
	}

	public static double getCurve(double distanceToPosition) { // Curve inputs so we don't abruptly stop - we should only be doing that if we hit a limit switch
		double setSpeed = 0.1 + distanceToPosition / 200;
		if(setSpeed > 1) {
			setSpeed = 1;
		}
		return setSpeed;
	}
}
