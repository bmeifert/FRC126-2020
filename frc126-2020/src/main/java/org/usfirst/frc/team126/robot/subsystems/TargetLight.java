package org.usfirst.frc.team126.robot.subsystems;

import org.usfirst.frc.team126.robot.Robot;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import org.usfirst.frc.team126.robot.commands.LightControl;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.ControlMode;

public class TargetLight extends Subsystem {

	public void initDefaultCommand() {
		setDefaultCommand(new LightControl());
	}

    public void setLight(double brightness) {
		Robot.victor1.set(ControlMode.PercentOutput, brightness);
		SmartDashboard.putNumber("targetLight", brightness);
	}

}
