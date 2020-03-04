package org.usfirst.frc.team126.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;


public class Solenoids extends Subsystem {
	DoubleSolenoid loaderFolder = new DoubleSolenoid(2,3);
	DoubleSolenoid loaderExtender = new DoubleSolenoid(4,5);
	DoubleSolenoid gearbox = new DoubleSolenoid(0,1);
	public void initDefaultCommand() {
	}
	public void extendLoader() {
		loaderExtender.set(DoubleSolenoid.Value.kReverse);
	}
	public void retractLoader() {
		loaderExtender.set(DoubleSolenoid.Value.kForward);
	}
	public void foldLoader() {
		loaderFolder.set(DoubleSolenoid.Value.kReverse);
	}
	public void unfoldLoader() {
		loaderFolder.set(DoubleSolenoid.Value.kForward);
	}
	public void upshift() {
		gearbox.set(DoubleSolenoid.Value.kReverse);

	}
	public void downshift() {
		gearbox.set(DoubleSolenoid.Value.kForward);
	}
}
