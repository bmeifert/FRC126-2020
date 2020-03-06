package org.usfirst.frc.team126.robot.commands;


import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoL2 extends CommandGroup {

    public AutoL2() {
      addSequential(new ZeroTurret(), 5);
      addSequential(new Drive(0.25, 0), 1.6);
    }
}