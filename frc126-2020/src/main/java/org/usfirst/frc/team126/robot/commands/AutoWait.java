package org.usfirst.frc.team126.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoWait extends CommandGroup {

    public AutoWait() {
    	addSequential(new Drive(0.25,0), 5);
    	}
}