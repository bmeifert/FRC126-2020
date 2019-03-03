package org.usfirst.frc.team126.robot.subsystems;

import java.util.HashMap;
import org.usfirst.frc.team126.robot.subsystems.Log;

import com.ctre.phoenix.motorcontrol.ControlMode;

import org.usfirst.frc.team126.robot.Robot;
import org.usfirst.frc.team126.robot.RobotMap;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;

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
	public static double rawEncoder = 0;
	public static double liftSpeed = 0;
	public static double liftMultiplier = 0;
	public static double previousLiftSpeed = 0;
	public static double periodicDebugCounter = 0;
	public static double targetEncoder = 0;
	public static boolean antiSlide;
	public static double encoderOffset;
	public static Potentiometer liftPot;
	public static AnalogInput ai = new AnalogInput(0);
	static boolean antiDrift = false;
	static double driftVal = 0;
	static boolean forceAntiDriftOff = false;

	public void initDefaultCommand() {

	}

	public static void resetLift() { // Set everything to default. Will require us to re-zero the lift.

		lState = liftStates.zeroing;
		targetPos = liftPos.zero;
		currentPos = liftPos.free;
		limitState = limitStates.ok;
		liftSpeed = 0;
		encoderVal = 0;

		try {
			liftPot = new AnalogPotentiometer(ai, 100, 0);
		} catch(Exception e){
			Log.print(2, "Lift", "LIFT POTENTIOMETER INIT FAILED");
			liftPot = null;
		}

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
		} else if(lState == liftStates.ready) {
			targetPos = lPos;
			if(lPos == liftPos.free) { // When target, state, and current are set to free, you can pass the speed value to MoveLift
				currentPos = liftPos.free;
				lState = liftStates.ready;
			} else {
				lState = liftStates.moving; // Unless the lift is free, set it to moving so other functions don't interrupt it
			}
		} else if(lState == liftStates.notzeroed){ // if the lift is not zeroed than we aren't moving at all
			Log.print(1, "Lift", "LIFT MOVE FAILED -- LIFT NOT ZEROED");
		} else if(doForceInterrupt == true) { // this is generally not the brightest idea unless the driver is directly controlling lift functions
			targetPos = lPos;
			if(lPos == liftPos.free) { // When target, state, and current are set to free, you can pass the speed value to MoveLift
				currentPos = liftPos.free;
				lState = liftStates.ready;
			} else {
				lState = liftStates.moving; // Unless the lift is free, set it to moving so other functions don't interrupt it
			}
		} else { // if the lift is in any other state, e.g. already moving to another position
			Log.print(1, "Lift", "LIFT TARGET FAILED -- LIFT BUSY"); 
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
		if(liftPot != null) {
			rawEncoder = 100 - liftPot.get();
			encoderVal = 100 - liftPot.get() - encoderOffset;
		} else {
			encoderVal = 0;
			rawEncoder = 0;
		}
		if(encoderVal > RobotMap.liftTopLimit) {
			limitState = limitStates.topLimit;
		}
		if(targetPos == liftPos.zero) { // Handle lift movement for auto, fastest we should go down is -0.05 so we don't break anything
			setLiftSpeed(-0.05);
			forceAntiDriftOff = true;
		} else if(targetPos == liftPos.free) { // Take lift out of auto for operator control -- NOTE: This is semi-dangerous
			if(optionalSpeed < 0) {
				optionalSpeed = 0.05 + optionalSpeed * 0.15;
				forceAntiDriftOff = true;
			}
			setLiftSpeed(optionalSpeed);
		} else if(currentPos != targetPos && targetPos != liftPos.zero) {
			if(encoderVal < encoderMap.get(targetPos) - 0.1) { // 1/100 = margin of error to move up
				liftMultiplier = 0.5;
				setLiftSpeed(liftMultiplier);
				//setLiftSpeed(getCurve(distanceToTarget * liftMultiplier));
			} else if(encoderVal > encoderMap.get(targetPos) + 0.1) { // 1/100 = margin of error to move down
				liftMultiplier = -0.05;
				setLiftSpeed(liftMultiplier);
				//setLiftSpeed(getCurve(distanceToTarget * liftMultiplier));

			} else { // within margin of error, set current pos to target and stop the lift
				currentPos = liftPos.free;
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
			encoderOffset = rawEncoder;
			if(encoderOffset > 15) {
				Log.print(3, "Lift", "POTENTIOMETER DRIFT OFFSET TOO HIGH - MECHANICAL RE-ZERO ASAP | OFFSET: "+encoderOffset+", MAX: ~25");
			} else if(encoderOffset < 4) {
				Log.print(3, "Lift", "POTENTIOMETER DRIFT OFFSET TOO LOW - MECHANICAL RE-ZERO ASAP | OFFSET: "+encoderOffset+", MIN: ~2");
			}
			if(targetPos == liftPos.zero) { // If we're at the bottom limit we're zeroed
				targetPos = liftPos.free;
				currentPos = liftPos.free;
				lState = liftStates.ready;
			}
		} else if(limitState == limitStates.topLimit && targetSpeed > 0) { // Prevent movement above top limit
			if(targetSpeed > RobotMap.LiftPassiveComp) {
				targetSpeed = RobotMap.LiftPassiveComp;
				previousLiftSpeed = RobotMap.LiftPassiveComp; // Setting PreviousSpeed to zero prevents lift from jumping once it leaves the limit
			}

		}

		if(lState != liftStates.moving) { // Our state is not moving, but we might still be ok
			if(lState == liftStates.zeroing) { // If we're zeroing the lift, limit it to 20% speed so it doesn't break anything.
				if(targetSpeed > 0) {
					targetSpeed = 0;
				}
				else if(targetSpeed < -0.01) {
					targetSpeed = -0.01;
				}
			} else if(lState == liftStates.ready && currentPos == liftPos.free) {
				// PASS
				// If the operator is controlling the lift then pass through
			} else { // If it's saying we should move but we're not in an appropriate state disable just to make sure.
				targetSpeed = 0;
			}
			
		} // If none of the above functions are met, the lift is good for normal movement.

		if(limitState == limitStates.ok && targetPos != liftPos.zero) {
			targetSpeed = (previousLiftSpeed * 4 + targetSpeed) / 5;
			previousLiftSpeed = targetSpeed;
		}

		if(targetSpeed > 0.6) {
			targetSpeed = 0.4;
		} else if(targetSpeed < -0.05) {
			targetSpeed = -0.05;
		}

		periodicDebugCounter++;
		if(periodicDebugCounter > 100) {
			//Log.print(0, "Lift", "State: "+lState+" Tpos: " +targetPos+" Cpos: "+currentPos+" CPot: "+encoderVal+" RPot:"+rawEncoder+" Speed:" + targetSpeed);
			periodicDebugCounter = 0;
		}
		if(targetSpeed > 0 && targetSpeed < 0.1 && limitState != limitStates.bottomLimit) {
			if(antiDrift == false) {
				antiDrift = true;
				driftVal = encoderVal;
				if(encoderVal > RobotMap.firstStopPosition - 3 && encoderVal < RobotMap.firstStopPosition + 3) {
					driftVal = RobotMap.firstStopPosition;
				}
				if(encoderVal > RobotMap.secondStopPosition - 3 && encoderVal < RobotMap.secondStopPosition + 3) {
					driftVal = RobotMap.secondStopPosition;
				}
				if(encoderVal > RobotMap.thirdStopPosition - 3 && encoderVal < RobotMap.thirdStopPosition + 3) {
					driftVal = RobotMap.secondStopPosition;
				}
			} else {
				if(encoderVal < driftVal && limitState != limitStates.bottomLimit && forceAntiDriftOff == false) {
					targetSpeed = RobotMap.LiftActiveComp;
				} else {
					targetSpeed = RobotMap.LiftPassiveComp;
				}
			}
		}
		forceAntiDriftOff = false;
		Robot.leftLift1.set(ControlMode.PercentOutput, targetSpeed * RobotMap.leftLift1Inversion);
		Robot.leftLift2.set(ControlMode.PercentOutput, targetSpeed * RobotMap.leftLift2Inversion);
		Robot.rightLift1.set(ControlMode.PercentOutput, targetSpeed * RobotMap.rightLift1Inversion);
		Robot.rightLift2.set(ControlMode.PercentOutput, targetSpeed * RobotMap.rightLift2Inversion);
	}

}
