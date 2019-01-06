		
package org.usfirst.frc.team126.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;


import org.usfirst.frc.team126.robot.commands.AutoCenterToLeft;
import org.usfirst.frc.team126.robot.subsystems.MecanumDrivebase;
import org.usfirst.frc.team126.robot.RobotMap;

public class Robot extends TimedRobot {
	public static Command autonomous; // Init devices and commands
	public static MecanumDrivebase driveBase;
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

	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void testPeriodic() {
	}
}
