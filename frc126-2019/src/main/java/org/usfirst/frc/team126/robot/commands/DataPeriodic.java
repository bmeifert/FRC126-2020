package org.usfirst.frc.team126.robot.commands;

import org.usfirst.frc.team126.robot.Robot;
import org.usfirst.frc.team126.robot.subsystems.InternalData;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class DataPeriodic extends Command {
	int count=0;

	public DataPeriodic() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.internalData);
	}

	// Run before command starts 1st iteration
	@Override
	protected void initialize() {
			
	}

	// Called every tick (20ms)
	@SuppressWarnings("static-access")
	@Override
	protected void execute() {
		SmartDashboard.putNumber("Match Time Left", InternalData.getMatchTime());
		SmartDashboard.putNumber("Voltage", InternalData.getVoltage());
		SmartDashboard.putBoolean("Teleop", InternalData.isTeleop());
		SmartDashboard.putBoolean("Autonomous", InternalData.isAuto());
		SmartDashboard.putBoolean("Enabled", InternalData.isEnabled());
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
