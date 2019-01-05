package org.usfirst.frc.team126.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoCenterToLeft extends CommandGroup {

    public AutoCenterToLeft() {
    	
    	addSequential(new DriveForward(), 3);
    	
    }
}
