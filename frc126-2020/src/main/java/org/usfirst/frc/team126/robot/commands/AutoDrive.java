package org.usfirst.frc.team126.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoDrive extends CommandGroup {

    public AutoDrive() {
    	addSequential(new DriveForwards(), 3);
    	}
}