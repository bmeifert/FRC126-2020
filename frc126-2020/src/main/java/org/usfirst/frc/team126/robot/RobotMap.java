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

\\      Team 126 2020 Code       \\
	~ Brought to you by:
	~ Keith Meifert
	Go get em gaels!

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
	public static int right1 = 3;
	public static int right2 = 4;
	public static int turretRotator = 5;
	public static int turretShooter = 6;
	public static int spinnerMotor = 7;

	//Motor Inversions
	public static int left1Inversion;
	public static int right1Inversion;
	public static int left2Inversion;
	public static int right2Inversion;
	public static int turretRotatorInversion;
	public static int turretShooterInversion;
	public static int spinnerMotorInversion;

	//Position Calibrations

	public static void setRobot(double robotID){
		if(robotID == 0){ // 2020 compbot
			left1Inversion = -1; // Motor inversions
			right1Inversion = 1;
			left2Inversion = -1;
			right2Inversion = 1;
			turretRotatorInversion = 1;
			turretShooterInversion = 1;
			spinnerMotorInversion = 1;

		} else if(robotID == 1){ // 2019 testbed
			left1Inversion = -1; // Motor inversions
			right1Inversion = 1;
			left2Inversion = -1;
			right2Inversion = 1;
			turretRotatorInversion = 1;
			turretShooterInversion = 1;
			spinnerMotorInversion = -1;

		}
	}
}