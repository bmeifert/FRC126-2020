package org.usfirst.frc.team126.robot.subsystems;

import org.usfirst.frc.team126.robot.Robot;
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
		}
		else if(category == 1) {
			catheader = " >[WARN] ";
		}
		else {
			catheader = ">>[CRIT] ";
		}
		System.out.println(catheader + header + ": " + content);
	}

}
