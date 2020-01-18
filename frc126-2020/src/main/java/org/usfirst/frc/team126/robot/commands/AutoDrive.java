package org.usfirst.frc.team126.robot.commands;

import org.usfirst.frc.team126.robot.subsystems.ColorData;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoDrive extends CommandGroup {

    public AutoDrive() {
    	addSequential(new ColorSpin(ColorData.blue));
    	}
}