package org.usfirst.frc.team126.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;

import org.usfirst.frc.team126.robot.Robot;
import org.usfirst.frc.team126.robot.commands.*;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DigitalSource;
import edu.wpi.first.wpilibj.command.Subsystem;

public class CargoHandler extends Subsystem {
    static double loadMotorSpeed = 0;
    static double throwerSpeed = 0;
    static double pickupSpeed = 0;
    static boolean readyToShoot = false;

    public CargoHandler () {
    }

    public void initDefaultCommand() {
        setDefaultCommand(new CargoWork());
    }
    public void runShooter() {
        if(throwerSpeed > 1) {
            throwerSpeed = 1;
            readyToShoot = true;
        } else {
            throwerSpeed += 0.1;
            readyToShoot = false;
        }
        Robot.throwerMotor1.set(ControlMode.PercentOutput, throwerSpeed);
        Robot.throwerMotor2.set(ControlMode.PercentOutput, 0-throwerSpeed);
    }
    public void stopShooter() {
        readyToShoot = false;
        throwerSpeed = 0;
        Robot.throwerMotor1.set(ControlMode.PercentOutput, throwerSpeed);
        Robot.throwerMotor2.set(ControlMode.PercentOutput, 0-throwerSpeed);
    }

    public void runPickup() {
        if(pickupSpeed > 1) {
            pickupSpeed = 1;
        } else {
            pickupSpeed += 0.1;
        }
        Robot.pickupMotor.set(0-pickupSpeed);
    }
    public void runPickupReverse() {
        if(pickupSpeed > 1) {
            pickupSpeed = 1;
        } else {
            pickupSpeed += 0.1;
        }
        Robot.pickupMotor.set(pickupSpeed);
    }

    public void stopPickup() {
        pickupSpeed = 0;
        Robot.pickupMotor.set(0-pickupSpeed);
    }

    public void runLoadMotor() {
        if(loadMotorSpeed > 0.5) {
            loadMotorSpeed = 0.5;
        } else {
            loadMotorSpeed += 0.1;
        }
        Robot.loadMotor.set(0-loadMotorSpeed);
    }
    public void runLoadMotorReverse() {
        if(loadMotorSpeed > 0.5) {
            loadMotorSpeed = 0.5;
        } else {
            loadMotorSpeed += 0.1;
        }
        Robot.loadMotor.set(loadMotorSpeed);
    }
    public void setLoadMotor(double uspeed) {
        if(uspeed > 0) {
            uspeed *= 0.5;
        }
        Robot.loadMotor.set(uspeed);
    }

    public void stopLoadMotor() {
        loadMotorSpeed = 0;
        Robot.loadMotor.set(0-loadMotorSpeed);
    }
}