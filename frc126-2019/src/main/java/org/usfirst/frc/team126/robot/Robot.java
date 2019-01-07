		
package org.usfirst.frc.team126.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import org.usfirst.frc.team126.robot.commands.AutoCenterToLeft;
import org.usfirst.frc.team126.robot.subsystems.MecanumDrivebase;
import org.usfirst.frc.team126.robot.RobotMap;

public class Robot extends TimedRobot {

	public static Command autonomous; // Create commands
	public static MecanumDrivebase driveBase;
	public static OI oi;

	public static Talon frontLeft = new Talon(RobotMap.frontLeft); // Create devices
	public static Talon frontRight = new Talon(RobotMap.frontRight);
	public static Talon backLeft = new Talon(RobotMap.backLeft);
	public static Talon backRight = new Talon(RobotMap.backRight);

	@SuppressWarnings("unchecked")
	@Override
	public void robotInit() { // Init components
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
