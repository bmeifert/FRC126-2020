		
package org.usfirst.frc.team126.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;


import org.usfirst.frc.team126.robot.commands.AutoCenterToLeft;
import org.usfirst.frc.team126.robot.subsystems.MecanumDrivebase;
import org.usfirst.frc.team126.robot.RobotMap;

// import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Robot extends IterativeRobot {
	public static Command autonomous;
	public static MecanumDrivebase driveBase; // Init components
	public static OI oi;

	public static Talon frontLeft = new Talon(RobotMap.frontLeft);
	public static Talon frontRight = new Talon(RobotMap.frontRight);

	@SuppressWarnings("unchecked")
	@Override
	public void robotInit() { // Import components
		oi = new OI();
		driveBase = new MecanumDrivebase();
	}

	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}
	
	@Override
	public void autonomousInit() {
		autonomous = (Command) new AutoCenterToLeft();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		if(autonomous != null){
			autonomous.cancel();
		}
    }

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
	}
}
