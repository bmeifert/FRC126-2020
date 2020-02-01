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
import org.usfirst.frc.team126.robot.commands.OperatorControl.driveStates;
import org.usfirst.frc.team126.robot.RobotMap;
import com.revrobotics.ColorSensorV3;

import java.lang.reflect.ParameterizedType;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Robot extends TimedRobot {

	public static TalonSRX left1 = new TalonSRX(RobotMap.left1); // Create the hardware that all the subsystems use
	public static TalonSRX right1 = new TalonSRX(RobotMap.right1);
	public static TalonSRX left2 = new TalonSRX(RobotMap.left2);
	public static TalonSRX right2 = new TalonSRX(RobotMap.right2);
	public static TalonSRX turretRotator = new TalonSRX(RobotMap.turretRotator);
	public static TalonSRX turretShooter = new TalonSRX(RobotMap.turretShooter);
	public static TalonSRX spinnerMotor = new TalonSRX(RobotMap.spinnerMotor);

	public double robotID;

	public static Command autonomous; // Create the subsystems that control the hardware
	public static WestCoastDrive driveBase;
	public static InternalData internalData;
	public static Controllers oi;
	public static Log log;
	public static Turret turret;
	public static UsbCamera driveCam;
	public static VideoSink server;
	public static ColorSensorV3 colorDetector;
	public static double voltageThreshold;
	public static Vision vision;


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
		turret = new Turret();
		internalData = new InternalData();
		colorDetector = new ColorSensorV3(Port.kOnboard);
		vision = new Vision();

		driveCam = CameraServer.getInstance().startAutomaticCapture(0);
		server = CameraServer.getInstance().getServer();
		driveCam.setConnectionStrategy(VideoSource.ConnectionStrategy.kKeepOpen);
		server.setSource(driveCam);

		InternalData.initGyro();
		InternalData.resetGyro();
		ColorSpinner.Setup();
		Turret.Setup();

		voltageThreshold = 10;
		OperatorControl.currentState = driveStates.drive;

		SmartDashboard.putNumber("Voltage Threshold", voltageThreshold);
		autoPosition.addOption("Default", 0);
		autoPosition.addOption("Left", 1);
		autoPosition.addOption("Right", 2);
		autoPosition.addOption("Center", 3);
		SmartDashboard.putData("AutoPosition", autoPosition);
		autoFunction.addOption("Default", 0);
		autoFunction.addOption("Function 0", 1);
		autoFunction.addOption("Function 1", 2);
		autoFunction.addOption("Function 2", 3);
		SmartDashboard.putData("AutoFunction", autoFunction);

		Log.print(0, "Robot", "Robot Init Completed");
	}
	
	@Override
	public void robotPeriodic() { // Runs periodically regardless of robot state
		SmartDashboard.putNumber("Match Time Left", InternalData.getMatchTime()); // Provide the drivers all the cool data
		SmartDashboard.putNumber("Voltage", InternalData.getVoltage());
		SmartDashboard.putBoolean("Enabled", InternalData.isEnabled());
		voltageThreshold = SmartDashboard.getNumber("Voltage Threshold", voltageThreshold);
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
		try {
			selectedAutoPosition = (int) autoPosition.getSelected();
		} catch(NullPointerException e) {
			selectedAutoPosition = 0;
		}
		try {
			selectedAutoFunction = (int) autoFunction.getSelected();
		} catch(NullPointerException e) {
			selectedAutoFunction = 0;
		}
		switch(selectedAutoPosition) {
			case 0 :
				autonomous = (Command) new AutoDrive();
				break;
			case 1 :
				autonomous = (Command) new AutoTest();
				break;
			case 2 :
				autonomous = (Command) new AutoWait();
				break;
			default :
				autonomous = (Command) new AutoDrive();
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
		OperatorControl.currentState = driveStates.drive;
		Log.print(1, "Robot", "Robot Enabled - Operator control");
    }

	@Override
	public void teleopPeriodic() { // Runs periodically during teleop
		Scheduler.getInstance().run();
		SmartDashboard.putString("Drive State", OperatorControl.currentState.toString());
	}

	@Override
	public void testPeriodic() { // Runs during test (not implemented)
	}
}
