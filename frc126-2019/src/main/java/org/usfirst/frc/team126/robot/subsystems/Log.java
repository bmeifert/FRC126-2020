package org.usfirst.frc.team126.robot.subsystems;

import java.util.Arrays;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Log extends Subsystem {
	static String[] mutedSystems = new String[]{}; // MUTED SYSTEMS
	public void initDefaultCommand() {
	}
	public static void print(int category, String header, String content) {
		/*
		* (0) INFO - General logging, should not be spammed, eg. [INFO]
		* (1) WARN - Messages that should be looked at after a match (eg. Gyro inaccuracies, low voltage, etc.)
		* (2) CRIT - For problems that could affect robot operation (eg. high amperage, sensor failures, etc.)
		* (3) EMER - For problems that could damage the robot (eg. mechanical limit overruns, range extremes, etc.)
		*/
		String catheader;
		if(category == 0) {
			catheader = "  [INFO] ";
		} else if(category == 1) {
			catheader = " >[WARN] ";
		} else if(category == 2){
			catheader = ">>[CRIT] ";
		} else {
			catheader = ">>[EMER]";
			System.out.println(">>=================");
		}
		if(Arrays.asList(mutedSystems).contains(header) && category < 3) {

		} else {
			System.out.println(catheader + header + ": " + content);
		}

		if(category > 2) {
			System.out.println(">>=================");
		}
	}

}
