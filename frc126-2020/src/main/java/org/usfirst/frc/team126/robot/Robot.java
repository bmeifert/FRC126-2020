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
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;

import org.usfirst.frc.team126.robot.subsystems.*;
import org.usfirst.frc.team126.robot.commands.*;
import org.usfirst.frc.team126.robot.commands.OperatorControl.driveStates;
import org.usfirst.frc.team126.robot.RobotMap;
import com.revrobotics.ColorSensorV3;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

public class Robot extends TimedRobot {
	public static TalonFX left1 = new TalonFX(RobotMap.left1); // Create the hardware that all the subsystems use
	public static TalonFX right1 = new TalonFX(RobotMap.right1);
	public static TalonFX left2 = new TalonFX(RobotMap.left2);
	public static TalonFX right2 = new TalonFX(RobotMap.right2);
	//public static TalonSRX spinnerMotor = new TalonSRX(RobotMap.spinnerMotor);
	public static TalonFX throwerMotor1 = new TalonFX(RobotMap.throwerMotor1);
	public static TalonFX throwerMotor2 = new TalonFX(RobotMap.throwerMotor2);
	public static CANSparkMax pickupMotor = new CANSparkMax(RobotMap.pickupMotor, CANSparkMaxLowLevel.MotorType.kBrushless);
	public static CANSparkMax loadMotor = new CANSparkMax(RobotMap.loadMotor, CANSparkMaxLowLevel.MotorType.kBrushless);
	public static CANSparkMax turretMotor = new CANSparkMax(RobotMap.turretMotor, CANSparkMaxLowLevel.MotorType.kBrushless);
	public static CANSparkMax hoodMotor = new CANSparkMax(RobotMap.hoodMotor, CANSparkMaxLowLevel.MotorType.kBrushless);
	
	public static Compressor compressor;
	public static VictorSPX victor1 = new VictorSPX(50);
	public static enum targetTypes{noTarget, throwingTarget, ballTarget, turretOnly, ballLLTarget};

	public double robotID;

	public static int objectId=1;
	public static targetTypes trackTarget = Robot.targetTypes.noTarget;
	public static double robotTurn = 0;
	public static double robotDrive = 0;
	public static boolean isAutoTransmission = false;
	
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
	public static LidarLite distance;
	public static TargetLight tLight;
	public static LimeLight limeLight;
	public static CargoHandler cargoHandler;
	public static Solenoids solenoids;
	public static SupplyCurrentLimitConfiguration currentConfig;

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
		cargoHandler = new CargoHandler();
		compressor = new Compressor();
		solenoids = new Solenoids();
		internalData = new InternalData();
		colorDetector = new ColorSensorV3(Port.kOnboard);
		//vision = new Vision();

		driveCam = CameraServer.getInstance().startAutomaticCapture(0);
		server = CameraServer.getInstance().getServer();
		driveCam.setConnectionStrategy(VideoSource.ConnectionStrategy.kKeepOpen);
		server.setSource(driveCam);
		distance = new LidarLite(new DigitalInput(5));
		tLight = new TargetLight();
		limeLight = new LimeLight();
		
		// Drivebase current limiter
		currentConfig = new SupplyCurrentLimitConfiguration();
		currentConfig.currentLimit = 40;
		currentConfig.enable = true;
		currentConfig.triggerThresholdCurrent = 60;
		currentConfig.triggerThresholdTime = 0.1;
		left1.configSupplyCurrentLimit(currentConfig);
		left2.configSupplyCurrentLimit(currentConfig);
		right1.configSupplyCurrentLimit(currentConfig);
		right2.configSupplyCurrentLimit(currentConfig);
		left1.configOpenloopRamp(0.5);
		left2.configOpenloopRamp(0.5);
		right1.configOpenloopRamp(0.5);
		right2.configOpenloopRamp(0.5);
		left1.setNeutralMode(NeutralMode.Brake);
		left2.setNeutralMode(NeutralMode.Brake);
		right1.setNeutralMode(NeutralMode.Brake);
		right2.setNeutralMode(NeutralMode.Brake);
		throwerMotor1.setNeutralMode(NeutralMode.Coast);
		throwerMotor2.setNeutralMode(NeutralMode.Coast);

		pickupMotor.setControlFramePeriodMs(0);
		loadMotor.setControlFramePeriodMs(0);
		turretMotor.setControlFramePeriodMs(0);
		hoodMotor.setControlFramePeriodMs(0);
		pickupMotor.setSmartCurrentLimit(80);
		loadMotor.setSmartCurrentLimit(80);
		turretMotor.setSmartCurrentLimit(80);
		hoodMotor.setSmartCurrentLimit(80);
	
		InternalData.initGyro();
		InternalData.resetGyro();
		ColorSpinner.Setup();
		Turret.Setup();

		voltageThreshold = 10;
		
		OperatorControl.currentState = driveStates.drive;
		//OperatorControl.currentState = driveStates.targetSeek;

		SmartDashboard.putNumber("Voltage Threshold", voltageThreshold);
		autoPosition.addOption("Default (Center)", 0);
		autoPosition.addOption("Left", 1);
		autoPosition.addOption("Right", 2);
		autoPosition.addOption("Center", 3);
		SmartDashboard.putData("AutoPosition", autoPosition);
		autoFunction.addOption("Default (Move)", 0);
		autoFunction.addOption("Move", 1);
		autoFunction.addOption("Full", 2);
		SmartDashboard.putData("AutoFunction", autoFunction);

		SmartDashboard.putBoolean("Automatic Transmission", false);
		Log.print(0, "Robot", "Robot Init Completed");
	}
	
	@Override
	public void robotPeriodic() { // Runs periodically regardless of robot state
		SmartDashboard.putNumber("Turret Encoder", turret.getRotatorEncoder());
		SmartDashboard.putNumber("Drivebase RPM", driveBase.getMeanRPM());
		isAutoTransmission = SmartDashboard.getBoolean("Automatic Transmission", false);
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
			case 1 :
				switch(selectedAutoFunction) {
					case 2 :
						autonomous = (Command) new AutoL2();
					break;

					default:
						autonomous = (Command) new AutoL1();
					break;
				}
				break;
			case 2 :
				switch(selectedAutoFunction) {
					case 2 :
						autonomous = (Command) new AutoR2();
					break;

					default:
						autonomous = (Command) new AutoR1();
					break;
				}
			break;
			case 3 :
				switch(selectedAutoFunction) {
					case 2 :
						autonomous = (Command) new AutoC2();
					break;

					default:
						autonomous = (Command) new AutoC1();
					break;
				}
			break;
			default :
				autonomous = (Command) new AutoC1();
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
		//OperatorControl.currentState = driveStates.targetSeek;

		Log.print(1, "Robot", "Robot Enabled - Operator control");
		
    }

	@Override
	public void teleopPeriodic() { // Runs periodically during teleop
		Scheduler.getInstance().run();
		SmartDashboard.putString("Drive State", OperatorControl.currentState.toString());
		SmartDashboard.putString("Track Target", Robot.trackTarget.toString());
	}

	@Override
	public void testPeriodic() { // Runs during test (not implemented)
	}
}
