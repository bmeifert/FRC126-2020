package org.usfirst.frc.team126.robot.commands;

import org.usfirst.frc.team126.robot.subsystems.ColorSpinner;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoDrive extends CommandGroup {

    public AutoDrive() {
    	addSequential(new ColorSpin(ColorSpinner.blue));
    	}
}