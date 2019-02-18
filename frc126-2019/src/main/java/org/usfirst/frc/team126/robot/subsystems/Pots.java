package org.usfirst.frc.team126.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import org.usfirst.frc.team126.robot.Robot;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;

public class Pots extends Subsystem {
	public static Potentiometer wristPot;
	public static AnalogInput ai1;
	public void initDefaultCommand() {
	}
	public static void initPot() {
		ai1 = new AnalogInput(0);
		wristPot = new AnalogPotentiometer(ai1, 1, -0.002);
	}
	public static double getWristPot() {
		return wristPot.get();
	}

}