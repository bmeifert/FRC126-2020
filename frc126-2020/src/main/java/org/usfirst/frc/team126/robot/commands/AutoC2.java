package org.usfirst.frc.team126.robot.commands;


import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoC2 extends CommandGroup {

    public AutoC2() {
        addSequential(new ZeroTurret(), 5);
        addSequential(new SeekAndShoot(10), 8);
        addSequential(new Drive(0.25, 0), 1.6);
    }
}