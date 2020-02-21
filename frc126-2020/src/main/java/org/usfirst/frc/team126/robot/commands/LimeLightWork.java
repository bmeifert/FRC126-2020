package org.usfirst.frc.team126.robot.commands;

import org.usfirst.frc.team126.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LimeLightWork extends Command {
    public static int iter=0;
    int centeredCount=0;
    boolean shootNow=false;

	/************************************************************************
	 ************************************************************************/

    public LimeLightWork() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.limeLight);
    }

	/************************************************************************
	 ************************************************************************/

    // Called just before this Command runs the first time
    protected void initialize() {
    }

	/************************************************************************
	 ************************************************************************/

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        SmartDashboard.putBoolean("shootnow:", shootNow);

        if (Robot.trackTarget != Robot.targetTypes.throwingTarget) {
            // We are not tracking the ball, just return
            shootNow=false;
			return;
        }
        
        Robot.limeLight.getCameraData();


        if (Robot.limeLight.getllTargetValid()){
            // We found a valid vision target.
            iter=0;

            Robot.robotDrive=0;

            double area = Robot.limeLight.getllTargetArea();
            double threshold;

            System.out.println("LimeLightWork: X: " + Robot.limeLight.getllTargetX() + " Area: " + area );

            if (area < .2) {
                threshold = 2;
            } else if (area < 1) {
                threshold = 3;
            } else if (area < 2) {
                threshold = 4;
            } else {
                threshold = 5;
            }

            if ( Robot.limeLight.getllTargetX() < ( -1 * threshold ) ) {
                // Target is to the left of the Robot, need to move left
                Robot.robotTurn=-.3;
                centeredCount=0;
                shootNow=false;
            } else if ( Robot.limeLight.getllTargetX() > threshold ) {
                // Target is to the left of the Robot, need to move right
                Robot.robotTurn=.3;
                centeredCount=0;
                shootNow=false;
            } else {
                centeredCount++;
                if (centeredCount > 20) {
                    shootNow=true;
                } else {
                    shootNow=false;
                }
                Robot.robotTurn=0;
            }

            if ( Robot.limeLight.getllTargetX() < -.25 ) {
                Robot.limeLight.setllTurretTarget((int)(Robot.limeLight.getllTargetX() * 20));
            } else if ( Robot.limeLight.getllTargetX() > .25 ) {
                Robot.limeLight.setllTurretTarget((int)(Robot.limeLight.getllTargetX() * 20));
            } else {
                Robot.limeLight.setllTurretTarget(0);
            }
        } else {
            iter++;
            centeredCount=0;
            shootNow=false;
            Robot.limeLight.setllTurretTarget(Robot.turret.getEncoder());

            if (iter > 10 && iter < 350) {
                // Try turning until we pick up a target
                Robot.robotTurn= -0.3;
            } else {
                Robot.robotTurn=0;
            }    

            if ( iter > 500 ) { 
                iter=0; 
            }

            // Don't move forward or back
            Robot.robotDrive=0;
        }

        //System.out.println("LimeLightWork: RT: " + Robot.robotTurn);

    }          

	/************************************************************************
	 ************************************************************************/

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }
}