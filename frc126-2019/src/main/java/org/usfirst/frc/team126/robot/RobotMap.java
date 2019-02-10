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
	public static int left1 = 4;
	public static int left2 = 2;
	public static int right1 = 1;
	public static int right2 = 3;
	public static int liftMotor1 = 5;
	public static int liftMotor2 = 6;
	public static int liftMotor3 = 7;
	public static int liftMotor4 = 8;
	public static int wristMotor = 9;
	public static int intakeMotor = 10;

	
	//Motor Inversions
	public static int left1Inversion;
	public static int right1Inversion;
	public static int left2Inversion;
	public static int right2Inversion;
	public static int lift1Inversion;
	public static int lift2Inversion;
	public static int lift3Inversion;
	public static int lift4Inversion;
	public static int wristInversion;
	public static int intakeInversion;

	public static double firstStopPosition;
	public static double secondStopPosition;
	public static double thirdStopPosition;



	public static void setRobot(double robotID){
		if(robotID == 0){ // 2019 compbot
			left1Inversion = -1;
			right1Inversion = 1;
			left2Inversion = -1;
			right2Inversion = 1;
			lift1Inversion = 1;
			lift2Inversion = 1;
			lift3Inversion = 1;
			lift4Inversion = 1;
			intakeInversion = 1;
			wristInversion = 1;
			
			firstStopPosition = 5000;
			secondStopPosition = 10000;
			thirdStopPosition = 15000;
		}
		else if(robotID == 1){ // 2019 practicebot
			left1Inversion = -1;
			right1Inversion = 1;
			left2Inversion = -1;
			right2Inversion = 1;
			lift1Inversion = 1;
			lift2Inversion = 1;
			lift3Inversion = 1;
			lift4Inversion = 1;
			intakeInversion = 1;
			wristInversion = 1;
			
			firstStopPosition = 5000;
			secondStopPosition = 10000;
			thirdStopPosition = 15000;
		}
		else { // fallback (should be same as compbot)
			left1Inversion = -1;
			right1Inversion = 1;
			left2Inversion = -1;
			right2Inversion = 1;
			lift1Inversion = 1;
			lift2Inversion = 1;
			lift3Inversion = 1;
			lift4Inversion = 1;
			intakeInversion = 1;
			wristInversion = 1;
			
			firstStopPosition = 5000;
			secondStopPosition = 10000;
			thirdStopPosition = 15000;
		}


	}
}