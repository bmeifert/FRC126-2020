package org.usfirst.frc.team126.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Log extends Subsystem {

	public void initDefaultCommand() {
	}

	public static void print(int category, String header, String content) { // category: 0-2 INFO - WARN - CRIT
		/*
		* INFO - General logging, should not be spammed, eg. [INFO]
		*
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
		System.out.println(catheader + header + ": " + content);
		if(category == 3) {
			System.out.println(">>=================");
		}
	}

}
