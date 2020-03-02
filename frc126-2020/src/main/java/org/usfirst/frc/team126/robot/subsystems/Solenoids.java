package org.usfirst.frc.team126.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;


public class Solenoids extends Subsystem {
	Solenoid loaderLeft = new Solenoid(2);
	Solenoid loaderRight = new Solenoid(3);
	DoubleSolenoid gearbox = new DoubleSolenoid(0,1);
	public void initDefaultCommand() {
	}
	public void extendLoader() {
		loaderLeft.set(true);
		loaderRight.set(true);
	}
	public void retractLoader() {
		loaderLeft.set(false);
		loaderRight.set(false);
	}
	public void upshift() {
		gearbox.set(DoubleSolenoid.Value.kReverse);

	}
	public void downshift() {
		gearbox.set(DoubleSolenoid.Value.kForward);
	}
}
