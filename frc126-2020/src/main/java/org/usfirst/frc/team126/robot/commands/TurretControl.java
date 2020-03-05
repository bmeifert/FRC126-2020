package org.usfirst.frc.team126.robot.commands;

import org.usfirst.frc.team126.robot.Robot;
import org.usfirst.frc.team126.robot.subsystems.Turret;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TurretControl extends Command {
	public static enum turretStates{zero, seek, lock, idle};
    public static turretStates currentState;
    public static turretStates targetState;
    public static double targetEncoder;
    public static double currentEncoder;
    public static double encoderDistance;

	/************************************************************************
	 ************************************************************************/

    public TurretControl() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.turret);
        targetEncoder = 0;
        SmartDashboard.putNumber("targetEncoder", targetEncoder);
    }

	/************************************************************************
	 ************************************************************************/

    // Called just before this Command runs the first time
    protected void initialize() {
        currentState = turretStates.idle;
    }

	/************************************************************************
	 ************************************************************************/

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        currentEncoder = Robot.turret.getRotatorEncoder();
    
        targetEncoder = Robot.turret.getTargetPosition(currentEncoder, Robot.objectId);
  
        SmartDashboard.putNumber("turretTarget", targetEncoder);
    
        encoderDistance = Math.abs(targetEncoder - currentEncoder);

        if ( targetEncoder > currentEncoder + 3 || targetEncoder < currentEncoder - 3) {
            currentState = turretStates.seek;
        }
        int fudgeFactor=25;
        switch(currentState) {
            case idle:
            //Robot.turret.Rotate(0);
            break;
            case seek:
                if(currentEncoder < targetEncoder - fudgeFactor) {
                    Robot.turret.Rotate(Robot.turret.getSpeedCurve(encoderDistance));
                } else if(currentEncoder > targetEncoder + fudgeFactor) {
                    Robot.turret.Rotate(0 - Robot.turret.getSpeedCurve(encoderDistance));
                } else {
                    Robot.turret.Rotate(0);
                    currentState = turretStates.lock;
                }
            break;
            case lock:
                if(currentEncoder < targetEncoder - fudgeFactor) {
                    Robot.turret.Rotate(Robot.turret.getSpeedCurve(encoderDistance));
                    currentState = turretStates.seek;
                } else if(currentEncoder > targetEncoder + fudgeFactor) {
                    Robot.turret.Rotate(0 - Robot.turret.getSpeedCurve(encoderDistance));
                    currentState = turretStates.seek;
                } else {
                    if(currentEncoder < targetEncoder - fudgeFactor) {
                        Robot.turret.Rotate(Robot.turret.getSpeedCurve(encoderDistance));
                    } else if(currentEncoder > targetEncoder + fudgeFactor) {
                        Robot.turret.Rotate(0 - Robot.turret.getSpeedCurve(encoderDistance));
                    } else {
                        Robot.turret.Rotate(0);
                    }
                }
            break;
            default:
                currentState = turretStates.idle;
            break;
        }
    }

	/************************************************************************
	 ************************************************************************/

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

	/************************************************************************
	 ************************************************************************/

    // Called once after isFinished returns true
    protected void end() {
        Robot.turret.Rotate(0);
    }

	/************************************************************************
	 ************************************************************************/

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        Robot.turret.Rotate(0);
    }
}