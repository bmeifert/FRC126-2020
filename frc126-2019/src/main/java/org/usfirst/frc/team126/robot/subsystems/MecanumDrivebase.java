package org.usfirst.frc.team126.robot.subsystems;

import org.usfirst.frc.team126.robot.Robot;
import org.usfirst.frc.team126.robot.RobotMap;
import org.usfirst.frc.team126.robot.commands.DriveWithJoysticks;

// import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * climberMotor.set(ControlMode.PercentOutput,0.0); 
 */
public class MecanumDrivebase extends Subsystem {

	double frontLeftMultiplier;
	double frontRightMultiplier;
	public void initDefaultCommand() {
		setDefaultCommand(new DriveWithJoysticks());
	}
	public void Drive(double fb, double rot, double genmult) { /** Coefficient Drive Base by Keith Meifert **/
		fb = fb * RobotMap.fbinversion;
		rot = rot * RobotMap.rotinversion;
		frontLeftMultiplier = fb + rot;
		frontRightMultiplier = fb - rot;
		frontLeftMultiplier = frontLeftMultiplier * genmult; // Set general speed multiplier
		frontRightMultiplier = frontRightMultiplier * genmult;
		
		// Robot.frontLeft.set(ControlMode.PercentOutput,(frontLeftMultiplier * RobotMap.flinversion)); // Set motors with correct inversions
		// Robot.frontRight.set(ControlMode.PercentOutput,(frontRightMultiplier * RobotMap.frinversion));
		Robot.frontLeft.set(frontLeftMultiplier * RobotMap.flinversion);
		Robot.frontRight.set(frontRightMultiplier * RobotMap.frinversion);
	}
}
