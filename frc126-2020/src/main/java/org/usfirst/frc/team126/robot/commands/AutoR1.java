package org.usfirst.frc.team126.robot.commands;


import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoR1 extends CommandGroup {

    public AutoR1() {
		addSequential(new Drive(-0.25, 0), 1.5);
		addSequential(new Rotate(-90), 3);
    }
}