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
	~ Keith M, Faria S, 
	~ Alyssa L, Kyle C, 
	~ Sophia S, and Alex T
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
	public static int front1 = 1;
	public static int front2 = 3;
	public static int back1 = 2;
	public static int back2 = 4;
	
	//Motor Inversions
	public static int front1Inversion;
	public static int back1Inversion;
	public static int front2Inversion;
	public static int back2Inversion;

	public static void setRobot(double robotID){

		if(robotID == 0){ // 2019 compbot (not implemented)
			front1Inversion = 1;
			back1Inversion = 1;
			front2Inversion = -1;
			back2Inversion = -1;
		}
		else if(robotID == 1){ // 2019 practicebot (not implemented)
			front1Inversion = 1;
			back1Inversion = 1;
			front2Inversion = -1;
			back2Inversion = -1;
		}
		else if(robotID == 2){ // 2018 compbot
			front1Inversion = 1;
			back1Inversion = 1;
			front2Inversion = -1;
			back2Inversion = -1;
		}
		
		else {
			throw new NullPointerException("No robot defined");
		}

	}
}