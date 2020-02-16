package org.usfirst.frc.team126.robot.subsystems;
import org.usfirst.frc.team126.robot.Robot;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.revrobotics.ColorMatch;

public class Lift extends Subsystem {
	public static ColorMatch colorMatch = new ColorMatch();
	public static Color red = ColorMatch.makeColor(0.535, 0.340, 0.125);
	public static Color green = ColorMatch.makeColor(0.161, 0.590, 0.248);
	public static Color yellow = ColorMatch.makeColor(0.315, 0.568, 0.117);
	public static Color blue = ColorMatch.makeColor(0.117, 0.430, 0.453);

	public void initDefaultCommand() {
	}

	public static void Setup() {
		colorMatch.addColorMatch(red);
		colorMatch.addColorMatch(green);
		colorMatch.addColorMatch(yellow);
		colorMatch.addColorMatch(blue);
		colorMatch.setConfidenceThreshold(0.95);

	}
	public static Color getColor() {
		return Robot.colorDetector.getColor();
	}

	public static double getRed() {
		return Robot.colorDetector.getColor().red;
	}
	public static double getGreen() {
		return Robot.colorDetector.getColor().green;
	}
	public static double getBlue() {
		return Robot.colorDetector.getColor().blue;
	}
	public static Color getMatch() {
		return colorMatch.matchClosestColor(ColorMatch.makeColor(getRed(), getGreen(), getBlue())).color;
	}
	public static void spin(double speed) {
		Robot.spinnerMotor.set(ControlMode.PercentOutput, speed);
		SmartDashboard.putNumber("spinnerMotor", speed);
	}
}
