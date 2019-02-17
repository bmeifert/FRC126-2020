package org.usfirst.frc.team126.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import org.usfirst.frc.team126.robot.Robot;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;

public class LiftPotentiometer extends Subsystem {
	public static Potentiometer liftPot;
	public static AnalogInput ai;
	public void initDefaultCommand() {
	}
	public static void initPot() {
		ai = new AnalogInput(0);
		liftPot = new AnalogPotentiometer(ai, 1, -0.002);
	}
	public static double getPot() {
		return liftPot.get();
	}

}