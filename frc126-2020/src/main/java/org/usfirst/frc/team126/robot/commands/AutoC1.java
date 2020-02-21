package org.usfirst.frc.team126.robot.commands;


import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoC1 extends CommandGroup {

    public AutoC1() {
		addSequential(new Drive(-0.25, 0), 1.5);
    }
}