package org.usfirst.frc.team126.robot.subsystems;

import org.usfirst.frc.team126.robot.Robot;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import org.usfirst.frc.team126.robot.commands.LightControl;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.ControlMode;

public class TargetLight extends Subsystem {
	boolean targetLightOn=false;
	boolean targetLightDisabled = false;

	/************************************************************************
	 ************************************************************************/

	public void initDefaultCommand() {
		setDefaultCommand(new LightControl());
	}

	/************************************************************************
	 ************************************************************************/

	public void setLight() {
		if (targetLightOn) {
			if(targetLightDisabled) {
				Robot.victor1.set(ControlMode.PercentOutput, 0);
			} else {
				Robot.victor1.set(ControlMode.PercentOutput, 1);
			}

		} else {
			Robot.victor1.set(ControlMode.PercentOutput, 0);
		}
	}

	/************************************************************************
	 ************************************************************************/

    public void toggleTargetLight() {
       if ( targetLightOn ) {
		   	targetLightOn=false;
	   } else {
           	targetLightOn=true;
	   }
	}
	public void disableTargetLight() {
		targetLightDisabled = true;
	}
	public void enableTargetLight() {
		targetLightDisabled = false;
	}
}
