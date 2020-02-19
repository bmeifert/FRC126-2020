package org.usfirst.frc.team126.robot.commands;

import org.usfirst.frc.team126.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LimeLightWork extends Command {
    public static int iter=0;

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

        if (Robot.trackTarget == 2) {
            //Robot.limeLight.setLED(false);
            //System.out.println("limelift off");
        } else {
           // Robot.limeLight.setLED(true);
            //System.out.println("limelift off");
        }

        if ( Robot.trackTarget != 1) {
            //Robot.limeLight.setLED(false);
            return;
        }    
      
        Robot.limeLight.getCameraData();

        System.out.println("LimeLightWork: V: " + Robot.limeLight.getllTargetValid());
        if (Robot.limeLight.getllTargetValid()){
            // We found a valid vision target.
            iter=0;

            Robot.robotDrive=0;

            double area = Robot.limeLight.getllTargetArea();
            double threshold;

            System.out.println("LimeLightWork: X: " + Robot.limeLight.getllTargetX());

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
                Robot.robotTurn=-.25;
            } else if ( Robot.limeLight.getllTargetX() > threshold ) {
                // Target is to the left of the Robot, need to move right
                Robot.robotTurn=.25;
            } else {
                Robot.robotTurn=0;
            }
        } else {
            iter++;

            if (iter > 10 && iter < 350) {
                Robot.robotTurn= -0.3;
            } else {
                Robot.robotTurn=0;
            }    
            if (iter>150) { iter=0; }
            Robot.robotDrive=0;
        }

        System.out.println("LimeLightWork: RT: " + Robot.robotTurn);

    }          

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }
}