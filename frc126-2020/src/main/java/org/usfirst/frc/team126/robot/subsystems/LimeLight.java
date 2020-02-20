package org.usfirst.frc.team126.robot.subsystems;

import org.usfirst.frc.team126.robot.commands.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.*;

public class LimeLight extends Subsystem {

    private boolean llTargetValid;
    private double llTargetArea;
    private double llTargetX;
    private double llTargetY;
    private double turretTarget;

	/************************************************************************
	 ************************************************************************/

    public LimeLight() {
        llTargetValid=false;
        llTargetArea = 0.0;
        llTargetX = 0.0;
        llTargetY = 0.0;
        turretTarget = 0;
    }

	/************************************************************************
	 ************************************************************************/

    public void initDefaultCommand() {
		setDefaultCommand(new LimeLightWork());
	}

	/************************************************************************
	 ************************************************************************/

	public boolean getllTargetValid() {
       return llTargetValid;
    }   

	/************************************************************************
	 ************************************************************************/

	public double getllTargetArea() {
        return llTargetArea;
    }   
 
	/************************************************************************
	 ************************************************************************/

    public double getllTargetX() {
        return llTargetX;
    }   

	/************************************************************************
	 ************************************************************************/

    public double getllTargetY() {
        return llTargetY;
    }   

	/************************************************************************
	 ************************************************************************/

    public double getllTurretTarget() {
        return turretTarget;
     }   

	/************************************************************************
	 ************************************************************************/

    public void setllTurretTarget(double target) {
        turretTarget = target;
    }   

	/************************************************************************
	 ************************************************************************/

    public void setllTargetData(boolean isValid,
                                double targetArea,
                                double targetX,
                                double targetY) {
        llTargetValid = isValid;
        llTargetArea = targetArea;
        llTargetX = targetX;
        llTargetY = targetY;
    }    

	/************************************************************************
	 ************************************************************************/

    public void getCameraData() {
        double tv = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
        double tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
        double ty = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
        double ta = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0);
        
        if (tv < 1.0) {
            setllTargetData(false, 0, 0, 0);
        } else {
            setllTargetData(true, ta, tx, ty);
        }        
    }

	/************************************************************************
	 ************************************************************************/

    public void setLED(boolean onOff) {
        if (onOff) {
            NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(0);
        } else {
            NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1);
        }    
    }

	/************************************************************************
	 ************************************************************************/

    public void setCameraMode(boolean vision) {
        if (vision) {
            NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(0);
        } else {
            NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(1);
        }    
    }

	/************************************************************************
	 ************************************************************************/

    public void setPipeline(int pipeline) {
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(pipeline);
    }

	/************************************************************************
	 ************************************************************************/

    public void setStreamMode(int mode) {
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("stream").setNumber(mode);
    }

}