package org.usfirst.frc.team126.robot.commands;

import org.usfirst.frc.team126.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team126.robot.subsystems.LidarLite;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DigitalInput;

public class SensorsPeriodic extends Command {
	int count=0;

	public SensorsPeriodic() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.distance);
	}

	// Run before command starts 1st iteration
	@Override
	protected void initialize() {
	}

	// Called every tick (20ms)
	@SuppressWarnings("static-access")
	@Override
	protected void execute() {
		double ret = Robot.distance.getDistance();
        SmartDashboard.putNumber("Distance Sensor: ", ret);
		if (count++ % 10 == 0) {
			//System.out.println("Sensor Reading " + String.format("%5.2f", ret));
        }	   
    }

	// Returns true if command finished
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {

	}

	// Called when another command tries to use this command's subsystem
	@Override
	protected void interrupted() {

	}
}
