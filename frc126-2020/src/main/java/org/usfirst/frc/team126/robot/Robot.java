package org.usfirst.frc.team126.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSink;
import edu.wpi.cscore.VideoSource;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team126.robot.subsystems.*;
import org.usfirst.frc.team126.robot.RobotMap;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Robot extends TimedRobot {

	public static TalonSRX left1 = new TalonSRX(RobotMap.left1); // Create the hardware that all the subsystems use
	public static TalonSRX right1 = new TalonSRX(RobotMap.right1);
	public static TalonSRX left2 = new TalonSRX(RobotMap.left2);
	public static TalonSRX right2 = new TalonSRX(RobotMap.right2);

	public double robotID;

	public static Command autonomous; // Create the subsystems that control the hardware
	public static WestCoastDrive driveBase;
	public static InternalData internalData;
	public static Controllers oi;
	public static Log log;
	public static UsbCamera locam;
	public static VideoSink server;


	
	@Override
	public void robotInit() { // Runs when the code first starts

		RobotMap.setRobot(0); // ===== ROBOT ID: 0-2020COMPBOT, 1-2019TESTBED ===== //
		
		oi = new Controllers(); // Init subsystems
		log = new Log();
		driveBase = new WestCoastDrive();
		internalData = new InternalData();
		InternalData.initGyro();
		InternalData.resetGyro();
		locam = CameraServer.getInstance().startAutomaticCapture(0);
		server = CameraServer.getInstance().getServer();
		locam.setConnectionStrategy(VideoSource.ConnectionStrategy.kKeepOpen);
		server.setSource(locam);
		Log.print(0, "Robot", "=== ROBOT INIT COMPLETED ===");
	}
	
	@Override
	public void robotPeriodic() { // Runs periodically regardless of robot state
		SmartDashboard.putNumber("Match Time Left", InternalData.getMatchTime()); // Provide the drivers all the cool data
		SmartDashboard.putNumber("Voltage", InternalData.getVoltage());
		SmartDashboard.putBoolean("Enabled", InternalData.isEnabled());
	}

	@Override
	public void disabledInit() { // Runs when robot is first disabled
		Log.print(1, "Robot", "ROBOT DISABLED");
	}

	@Override
	public void disabledPeriodic() { // Runs periodically when robot is disabled
		Scheduler.getInstance().run();
	}
	
	@Override
	public void autonomousInit() { // Runs when autonomous starts
		Log.print(1, "Robot", "ROBOT ENABLED - AUTONOMOUS");
	}

	@Override
	public void autonomousPeriodic() { // Runs periodically during autonomous
		Scheduler.getInstance().run();
		
	}

	@Override
	public void teleopInit() { // Runs when teleop begins
		if(autonomous != null){
			autonomous.cancel();
		}
		Log.print(1, "Robot", "ROBOT ENABLED - OPERATOR");
    }

	@Override
	public void teleopPeriodic() { // Runs periodically during teleop
		Scheduler.getInstance().run();

	}

	@Override
	public void testPeriodic() { // Runs during test (not implemented)
	}
}
