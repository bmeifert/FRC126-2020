package org.usfirst.frc.team126.robot.commands;


import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoL1 extends CommandGroup {

    public AutoL1() {
      addSequential(new ZeroTurret(), 5);
      addSequential(new Drive(0.25, 0), 1.6);
    }
}