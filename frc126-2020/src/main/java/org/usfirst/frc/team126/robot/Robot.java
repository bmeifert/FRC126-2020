package org.usfirst.frc.team126.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSink;
import edu.wpi.cscore.VideoSource;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;

import org.usfirst.frc.team126.robot.subsystems.*;
import org.usfirst.frc.team126.robot.commands.*;
import org.usfirst.frc.team126.robot.RobotMap;
import com.revrobotics.ColorSensorV3;

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
	public static UsbCamera driveCam;
	public static VideoSink server;
	public static ColorSensorV3 colorDetector;

	Color detectedColor;

	int selectedAutoPosition;
	int selectedAutoFunction;
	
	@SuppressWarnings("rawtypes")
	SendableChooser autoPosition = new SendableChooser(); // Position chooser
	@SuppressWarnings("rawtypes")
	SendableChooser autoFunction = new SendableChooser(); // Priority chooser


	
	@Override
	public void robotInit() { // Runs when the code first starts

		RobotMap.setRobot(0); // ===== ROBOT ID: 0-2020COMPBOT, 1-2019TESTBED ===== //
		
		oi = new Controllers(); // Init subsystems
		log = new Log();
		driveBase = new WestCoastDrive();
		internalData = new InternalData();
		InternalData.initGyro();
		InternalData.resetGyro();
		colorDetector = new ColorSensorV3(Port.kOnboard);
		driveCam = CameraServer.getInstance().startAutomaticCapture(0);
		server = CameraServer.getInstance().getServer();
		driveCam.setConnectionStrategy(VideoSource.ConnectionStrategy.kKeepOpen);
		server.setSource(driveCam);
		ColorData.Setup();


		autoPosition.addOption("Left", 0);
		autoPosition.addOption("Right", 1);
		autoPosition.addOption("Center", 2);
		SmartDashboard.putData("AutoPosition", autoPosition);

		autoFunction.addOption("Function 0", 0);
		autoFunction.addOption("Function 1", 1);
		autoFunction.addOption("Function 2", 2);
		SmartDashboard.putData("AutoFunction", autoFunction);

		Log.print(0, "Robot", "Robot Init Completed");
	}
	
	@Override
	public void robotPeriodic() { // Runs periodically regardless of robot state
		SmartDashboard.putNumber("Match Time Left", InternalData.getMatchTime()); // Provide the drivers all the cool data
		SmartDashboard.putNumber("Voltage", InternalData.getVoltage());
		SmartDashboard.putBoolean("Enabled", InternalData.isEnabled());
	}

	@Override
	public void disabledInit() { // Runs when robot is first disabled
		Log.print(1, "Robot", "Robot Disabled");
	}

	@Override
	public void disabledPeriodic() { // Runs periodically when robot is disabled
		Scheduler.getInstance().run();
	}
	
	@Override
	public void autonomousInit() { // Runs when autonomous starts
		Log.print(1, "Robot", "Robot Enabled - Autonomous");
		selectedAutoPosition = (int) autoPosition.getSelected();
		selectedAutoFunction = (int) autoFunction.getSelected();
		switch(selectedAutoPosition) {
			case 0 :
				autonomous = (Command) new AutoDrive();
				break;
			case 1 :
				autonomous = (Command) new AutoWait();
				break;
			case 2 :
				autonomous = (Command) new AutoDrive();
				break;
			default :
				autonomous = (Command) new AutoWait();
				break;
		}
		if(autonomous != null){
			autonomous.start();
		}
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
		Log.print(1, "Robot", "Robot Enabled - Operator control");
    }

	@Override
	public void teleopPeriodic() { // Runs periodically during teleop
		Scheduler.getInstance().run();
		detectedColor = ColorData.getMatch();
		if(detectedColor == ColorData.red) {
			System.out.println("RED");
		}
		else if(detectedColor == ColorData.blue) {
			System.out.println("BLUE");
		}
		else if(detectedColor == ColorData.yellow) {
			System.out.println("YELLOW");
		}
		else if(detectedColor == ColorData.green) {
			System.out.println("GREEN");
		}

	}

	@Override
	public void testPeriodic() { // Runs during test (not implemented)
	}
}
