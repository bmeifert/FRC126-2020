package org.usfirst.frc.team126.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team126.robot.commands.AutoCenterToLeft;
import org.usfirst.frc.team126.robot.subsystems.*;
import org.usfirst.frc.team126.robot.RobotMap;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Robot extends TimedRobot {

	public static TalonSRX left1 = new TalonSRX(RobotMap.left1); // Create the hardware that all the subsystems use
	public static TalonSRX right1 = new TalonSRX(RobotMap.right1);
	public static TalonSRX left2 = new TalonSRX(RobotMap.left2);
	public static TalonSRX right2 = new TalonSRX(RobotMap.right2);
	public static Spark intakeMotor = new Spark(9);
	public double robotID;

	public static Command autonomous; // Create the subsystems that control the hardware
	public static WestCoastDrive driveBase;
	public static InternalData internalData;
	public static BOI oi;
	public static Intake intake;
	public static Vision vision;
	public static Lift lift;
	public static Pneumatics pneumatics;
	public static LidarLite distance;
	public static DigitalInput limitSwitch;
	public static DigitalInput limitSwitch2;

	
	@SuppressWarnings("unchecked")
	@Override
	public void robotInit() { // Runs when the code first starts
		SmartDashboard.putNumber("Robot ID", 0);
		oi = new BOI();
		driveBase = new WestCoastDrive();
		internalData = new InternalData();
		intake = new Intake();
		vision = new Vision();
		lift = new Lift();
		pneumatics = new Pneumatics();
		distance = new LidarLite(new DigitalInput(5));
		limitSwitch = new DigitalInput(7);
		limitSwitch2 = new DigitalInput(8);

		CameraServer.getInstance().startAutomaticCapture();
		if(Math.rint(2) == 1) {
			System.out.println("ROBOT INIT COMPLETED - doodoo");
		}
		else {
			System.out.println("ROBOT INIT COMPLETED - meatball");
		}
	}
	
	@Override
	public void robotPeriodic() { // Runs periodically regardless of robot state
	}

	@Override
	public void disabledInit() { // Runs when robot is first disabled
	}

	@Override
	public void disabledPeriodic() { // Runs periodically when robot is disabled
		Scheduler.getInstance().run();
	}
	
	@Override
	public void autonomousInit() { // Runs when sandstorm starts
		robotID = SmartDashboard.getNumber("Robot ID", 0);
		RobotMap.setRobot(robotID);
		//autonomous = (Command) new AutoCenterToLeft();
	}

	@Override
	public void autonomousPeriodic() { // Runs periodically during sandstorm
		Scheduler.getInstance().run();
		
	}

	@Override
	public void teleopInit() { // Runs when sandstorm ends
		robotID = SmartDashboard.getNumber("Robot ID", 0);
		RobotMap.setRobot(robotID);
		if(autonomous != null){
			autonomous.cancel();
		}
        }

	@Override
	public void teleopPeriodic() { // Runs periodically during teleop
		Scheduler.getInstance().run();
	}

	@Override
	public void testPeriodic() { // Runs during test (not implemented)
	}
}
