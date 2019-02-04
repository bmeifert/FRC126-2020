package org.usfirst.frc.team126.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Lift extends Subsystem {
	public static enum liftPos { 
		first, second, third, zero, operator
	}
	public static enum liftStates {
		nothomed, ready, moving, bottomlimit, toplimit, estop
	}
	public static liftPos targetPos = null;
	public static liftPos currentPos = null;
	public static liftStates lState = null;
	public double encoderVal = 0;

	public void initDefaultCommand() {
	}
	public static void setTargetPos(liftPos lPos) {
		if(lState == liftStates.ready) {
			targetPos = lPos;
			lState = liftStates.moving;
		}
		else if(lState == liftStates.estop || lState == liftStates.nothomed){ // if the lift is having a major issue (not zeroed or emergency stopped)
			System.out.println("LIFT MOVE FAILED -- LIFT CRITICAL ERROR");
		}
		else { // if the lift is in any other state, e.g. already moving to another position
			System.out.println("LIFT TARGET FAILED -- LIFT BUSY"); 
		}

	}
	public static void moveLift(double newPos) {
		//TODO do some neat stuff here
	}
}
