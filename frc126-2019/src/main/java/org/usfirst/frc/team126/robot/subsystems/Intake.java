package org.usfirst.frc.team126.robot.subsystems;

import org.usfirst.frc.team126.robot.Robot;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Intake extends Subsystem {

	public void initDefaultCommand() {
	}

	public static void intakeOff() {
		Robot.intakeMotor.set(0);
	}
	public static void setIntake(double speed) {
		Robot.intakeMotor.set(speed);
	}

}
