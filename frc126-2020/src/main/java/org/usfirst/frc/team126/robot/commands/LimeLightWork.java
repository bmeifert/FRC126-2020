package org.usfirst.frc.team126.robot.commands;

import org.usfirst.frc.team126.robot.Robot;
import org.usfirst.frc.team126.robot.subsystems.LimeLight;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.*;

public class LimeLightWork extends Command {
    public LimeLightWork() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.limeLight);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        Robot.limeLight.getCameraData();

        if ( Robot.trackTarget ) {
            if (Robot.limeLight.getllTargetValid()){
                // We found a valid vision target.

                if ( Robot.limeLight.getllTargetX() < -5 ) {
                    // Target is to the left of the Robot, need to move left
                    Robot.robotTurn=-.25;
                } else if ( Robot.limeLight.getllTargetX() > 5 ) {
                    // Target is to the left of the Robot, need to move right
                    Robot.robotTurn=.25;
                } else {
                    Robot.robotTurn=0;
                }

            }
        }     
    }    

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

}