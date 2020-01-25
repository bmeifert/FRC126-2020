package org.usfirst.frc.team126.robot.commands;


import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoTest extends CommandGroup {

    public AutoTest() {
		addSequential(new Rotate(90), 3);
		addSequential(new Pause(), 0.5);
		addSequential(new Rotate(-90), 3);
		addSequential(new Pause(), 0.5);
		addSequential(new Rotate(-180), 3);
		addSequential(new Pause(), 0.5);
		addSequential(new Rotate(180), 3);
		addSequential(new Pause(), 0.5);
    	}
}