package org.usfirst.frc.team126.robot;

/**

\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	   _      ___      ____
	 /' \   /'___`\   /'___\
	/\_, \ /\_\ /\ \ /\ \__/
	\/_/\ \\/_/// /__\ \  _``\
	   \ \ \  // /_\ \\ \ \L\ \
	    \ \_\/\______/ \ \____/
		 \/_/\/_____/   \/___/

	~ Brought to you by:
	~ Keith M, Kyle C, and Alyssa L
	~ GO GET EM GAEL FORCE!!!

\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

**/
public class RobotMap {

	// Controls for Xbox 360 / Xbox One
	public static int lStickX = 0; // Left stick X
	public static int lStickY = 1; // Left stick Y
	public static int rStickX = 4; // Right stick X
	public static int rStickY = 5; // Right stick Y
	public static int Rtrigger = 3; // Right trigger
	public static int Ltrigger = 2; // Left trigger
	public static int xboxA = 1; // A
	public static int xboxB = 2; // B
	public static int xboxX = 3; // X
	public static int xboxY = 4; // Y
	public static int xboxLTrig = 5; // Left trigger button
	public static int xboxRTrig = 6; // Right trigger button
	public static int xboxBack = 7; // Back
	public static int xboxStart = 8; // Start
	public static int xboxLStick = 9; // Left stick button
	public static int xboxRStick = 10; // Right stick button
	
	// Motor IDs
	public static int left1 = 1;
	public static int left2 = 2;
	public static int right1 = 10;
	public static int right2 = 11;
	public static int leftLift1 = 3;
	public static int leftLift2 = 4;
	public static int rightLift1 = 8;
	public static int rightLift2 = 9;
	public static int wristMotor = 13;
	public static int intakeMotor = 5;

	
	//Motor Inversions
	public static int left1Inversion;
	public static int right1Inversion;
	public static int left2Inversion;
	public static int right2Inversion;
	public static int leftLift1Inversion;
	public static int leftLift2Inversion;
	public static int rightLift1Inversion;
	public static int rightLift2Inversion;
	public static int wristInversion;
	public static int intakeInversion;
	public static int hatchInversion;
	public static int wristEncoderInversion;
	public static int wristMax;

	public static double firstStopPosition;
	public static double secondStopPosition;
	public static double thirdStopPosition;
	public static double foldWristPos;
	public static double flatWristPos;
	public static double upWristPos;
	public static double downWristPos;
	public static double potOffset;
	public static double wristIdle;
	public static double liftTopLimit;
	public static double LiftPassiveComp;
	public static double LiftActiveComp;
	public static double startLiftSlowDown;
	public static double liftSlowDownFactor;



	public static void setRobot(double robotID){
		if(robotID == 0){ // 2019 compbot

			left1Inversion = -1; // Motor inversions
			right1Inversion = 1;
			left2Inversion = -1;
			right2Inversion = 1;
			leftLift1Inversion = -1;
			leftLift2Inversion = -1;
			rightLift1Inversion = 1;
			rightLift2Inversion = 1;
			intakeInversion = 1;
			wristInversion = 1;
			hatchInversion = 1;
			wristEncoderInversion = -1;

			wristIdle = 0.05;
			liftTopLimit = 60;
			wristMax = 15500;
			foldWristPos = 250; // Wrist stops
			flatWristPos = 11500;
			upWristPos = 9500;
			downWristPos = 15000;
			firstStopPosition = 9; // Lift stops
			secondStopPosition = 33;
			thirdStopPosition = 59;
			potOffset = 8; // Lift potentiometer offset (LEGACY)
			LiftPassiveComp = 0.1;
			LiftActiveComp = 0.3;
			startLiftSlowDown = 20;
			liftSlowDownFactor = 5;
		} else if(robotID == 1){ // 2019 pracbot

			left1Inversion = -1; // Motor inversions
			right1Inversion = 1;
			left2Inversion = -1;
			right2Inversion = 1;
			leftLift1Inversion = -1;
			leftLift2Inversion = -1;
			rightLift1Inversion = 1;
			rightLift2Inversion = 1;
			intakeInversion = 1;
			wristInversion = 1;
			hatchInversion = 1;
			wristEncoderInversion = -1;

			wristIdle = 0.05;
			liftTopLimit = 60;
			wristMax = 15500;
			foldWristPos = 250; // Wrist stops
			flatWristPos = 11500;
			upWristPos = 9500;
			downWristPos = 15000;
			firstStopPosition = 9; // Lift stops
			secondStopPosition = 33;
			thirdStopPosition = 59;
			potOffset = 8; // Lift potentiometer offset (LEGACY)
			LiftPassiveComp = 0.1;
			LiftActiveComp = 0.3;
			startLiftSlowDown = 20;
			liftSlowDownFactor = 5;
		}
	}
}