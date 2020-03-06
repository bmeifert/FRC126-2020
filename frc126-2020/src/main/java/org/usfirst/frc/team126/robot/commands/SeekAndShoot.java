package org.usfirst.frc.team126.robot.commands;

import org.usfirst.frc.team126.robot.Robot;
import org.usfirst.frc.team126.robot.subsystems.InternalData;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SeekAndShoot extends Command {
    public enum seekStates{seek, spinup, shoot, done};
    public seekStates seekState = seekStates.seek;
    public int counter = 0;
    public double targetRotator;
    boolean isDone = false;
    boolean turretLocked = false;
    public SeekAndShoot(double tr) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        targetRotator = tr;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        Robot.driveBase.Drive(0, 0);
        Robot.turret.Rotate(0);
        Robot.turret.moveHood(0);
        counter = 0;
        isDone = false;
        turretLocked = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        switch(seekState) {
            case seek:
                if(!turretLocked) {
                    Robot.driveBase.Drive(0, 0);
                    if(Robot.turret.getRotatorEncoder() > targetRotator + 0.1) {
                        Robot.turret.Rotate(-0.1);
                    } else if(Robot.turret.getRotatorEncoder() < targetRotator - 0.1) {
                        Robot.turret.Rotate(0.1);
                    } else {
                        Robot.turret.Rotate(0);
                        turretLocked = true;
                    }
                }
                if(turretLocked) {
                    seekState = seekStates.spinup;
                }
            break;
            case spinup:
                Robot.driveBase.Drive(0, 0);
                Robot.turret.Rotate(0);
                Robot.tLight.disableTargetLight();
                Robot.turret.followHood();
                Robot.cargoHandler.runShooter();
                counter++;
                if(counter > 50) {
                    counter = 0;
                    seekState = seekStates.shoot;
                }
            break;
            case shoot:
                Robot.driveBase.Drive(0, 0);
                Robot.turret.Rotate(0);
                Robot.turret.moveHood(0);
                Robot.cargoHandler.runShooter();
                Robot.cargoHandler.runLoadMotor();
                Robot.tLight.enableTargetLight();
                counter++;
                if(counter > 100) {
                    Robot.cargoHandler.runPickup();
                    if(counter > 200) {
                        counter = 0;
                        seekState = seekStates.done;
                    }
                }
            break;
            case done:
                Robot.driveBase.Drive(0, 0);
                Robot.cargoHandler.stopLoadMotor();
                Robot.cargoHandler.stopPickup();
                Robot.cargoHandler.stopShooter();
                isDone = true;
                Robot.solenoids.extendLoader();
                Robot.solenoids.foldLoader();
            break;
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isDone;
    }


    // Called once after isFinished returns true
    protected void end() {
        Robot.driveBase.Drive(0, 0);
        Robot.turret.Rotate(0);
        Robot.turret.moveHood(0);
        Robot.cargoHandler.stopLoadMotor();
        Robot.cargoHandler.stopPickup();
        Robot.cargoHandler.stopShooter();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        Robot.driveBase.Drive(0, 0);
        Robot.turret.Rotate(0);
        Robot.turret.moveHood(0);
        Robot.cargoHandler.stopLoadMotor();
        Robot.cargoHandler.stopPickup();
        Robot.cargoHandler.stopShooter();
    }
}