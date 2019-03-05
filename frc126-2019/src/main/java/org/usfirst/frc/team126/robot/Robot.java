package org.usfirst.frc.team126.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.DigitalInput;
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
	public static TalonSRX leftLift1 = new TalonSRX(RobotMap.leftLift1); // Create the hardware that all the subsystems use
	public static TalonSRX leftLift2 = new TalonSRX(RobotMap.leftLift2);
	public static TalonSRX rightLift1 = new TalonSRX(RobotMap.rightLift1);
	public static TalonSRX rightLift2 = new TalonSRX(RobotMap.rightLift2);
	public static TalonSRX intakeMotor = new TalonSRX(RobotMap.intakeMotor);
	public static TalonSRX wristMotor = new TalonSRX(RobotMap.wristMotor);


	public double robotID;

	public static Command autonomous; // Create the subsystems that control the hardware
	public static WestCoastDrive driveBase;
	public static InternalData internalData;
	public static Controllers oi;
	public static Intake intake;
	public static Vision vision;
	public static Lift lift;
	public static Wrist wrist;
	public static Pneumatics pneumatics;
	public static LidarLite distance;
	public static DigitalInput liftBottomLimit;
	public static DigitalInput liftTopLimit;
	public static Log log;
	public static double currentDraw;
	public static double prevDraw = 0;

	
	@Override
	public void robotInit() { // Runs when the code first starts
		RobotMap.setRobot(1); // ===== ROBOT ID: 0-COMPBOT, 1-PRACTICEBOT ===== //
		oi = new Controllers(); // Init subsystems
		log = new Log();
		driveBase = new WestCoastDrive();
		internalData = new InternalData();
		intake = new Intake();
		vision = new Vision();
		lift = new Lift();
		pneumatics = new Pneumatics();
		distance = new LidarLite(new DigitalInput(5));
		liftBottomLimit = new DigitalInput(0);
		liftTopLimit = new DigitalInput(1);
		CameraServer.getInstance().startAutomaticCapture();
		Wrist.initWrist();
		Lift.resetLift();
		Log.print(0, "Robot", "=== ROBOT INIT COMPLETED ===");
	}
	
	@Override
	public void robotPeriodic() { // Runs periodically regardless of robot state

		SmartDashboard.putNumber("Match Time Left", InternalData.getMatchTime()); // Provide the drivers all the cool data
		SmartDashboard.putNumber("Voltage", InternalData.getVoltage());
		SmartDashboard.putBoolean("Enabled", InternalData.isEnabled());

		currentDraw = Robot.left1.getOutputCurrent() + Robot.left2.getOutputCurrent() + // Find current amperage
		Robot.right1.getOutputCurrent() + Robot.right2.getOutputCurrent() + 
		Robot.leftLift1.getOutputCurrent() + Robot.leftLift2.getOutputCurrent() +
		Robot.rightLift1.getOutputCurrent() + Robot.rightLift2.getOutputCurrent() +
		Robot.wristMotor.getOutputCurrent() + Robot.intakeMotor.getOutputCurrent();
		prevDraw = (prevDraw * 99 + currentDraw) / 100; // Find smoothed average amperage

		SmartDashboard.putNumber("Current", currentDraw);
		SmartDashboard.putNumber("Battery Load", prevDraw);
		SmartDashboard.putNumber("Linear LPOS", Lift.encoderVal);
		if(Lift.encoderVal > RobotMap.firstStopPosition - 2 && Lift.encoderVal < RobotMap.firstStopPosition + 2) {
			SmartDashboard.putBoolean("1st LPOS", true);
		} else {
			SmartDashboard.putBoolean("1st LPOS", false);
		}
		if(Lift.encoderVal > RobotMap.secondStopPosition - 2 && Lift.encoderVal < RobotMap.secondStopPosition + 2) {
			SmartDashboard.putBoolean("2nd LPOS", true);
		} else {
			SmartDashboard.putBoolean("2nd LPOS", false);
		}
		if(Lift.encoderVal > RobotMap.thirdStopPosition - 2 && Lift.encoderVal < RobotMap.thirdStopPosition + 2) {
			SmartDashboard.putBoolean("3rd LPOS", true);
		} else {
			SmartDashboard.putBoolean("3rd LPOS", false);
		}
		SmartDashboard.putNumber("Lift Pot Offset", Lift.encoderOffset);
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
	public void autonomousInit() { // Runs when sandstorm starts
		Wrist.initWrist();
		Log.print(1, "Robot", "ROBOT ENABLED - SANDSTORM");
	}

	@Override
	public void autonomousPeriodic() { // Runs periodically during sandstorm
		Scheduler.getInstance().run();
		
	}

	@Override
	public void teleopInit() { // Runs when sandstorm ends
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
