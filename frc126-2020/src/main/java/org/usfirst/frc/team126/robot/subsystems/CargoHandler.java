package org.usfirst.frc.team126.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;

import org.usfirst.frc.team126.robot.Robot;
import org.usfirst.frc.team126.robot.commands.*;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DigitalSource;
import edu.wpi.first.wpilibj.command.Subsystem;

public class CargoHandler extends Subsystem {
    private Solenoid intekeSolenoid;
    static double loadMotorSpeed = 0;
    static double throwerSpeed = 0;
    static double pickupSpeed = 0;

    public CargoHandler () {
        intekeSolenoid = new Solenoid(2);
    }

    public void initDefaultCommand() {
        setDefaultCommand(new CargoWork());
    }


    public void runThrower() {
        if(throwerSpeed > 1) {
            throwerSpeed = 1;
        } else {
            throwerSpeed += 0.1;
        }
        Robot.throwerMotor1.set(ControlMode.PercentOutput, throwerSpeed);
        Robot.throwerMotor2.set(ControlMode.PercentOutput, throwerSpeed);
    }
    
    public void stopThrower() {
        throwerSpeed = 0;
        Robot.throwerMotor1.set(ControlMode.PercentOutput, throwerSpeed);
        Robot.throwerMotor2.set(ControlMode.PercentOutput, throwerSpeed);
    }

    public void extendIntake() {
        intekeSolenoid.set(true);
    }

    public void retractIntake() {
        intekeSolenoid.set(false);
    }

    public void runPickup() {
        if(pickupSpeed > 1) {
            pickupSpeed = 1;
        } else {
            pickupSpeed += 0.05;
        }
        Robot.pickupMotor.set(pickupSpeed);
    }

    public void stopPickup() {
        pickupSpeed = 0;
        Robot.pickupMotor.set(pickupSpeed);
    }

    public void runLoadMotor() {
        if(loadMotorSpeed > 1) {
            loadMotorSpeed = 1;
        } else {
            loadMotorSpeed += 0.05;
        }
        Robot.loadMotor.set(0 - loadMotorSpeed);
    }

    public void stopLoadMotor() {
        loadMotorSpeed = 0;
        Robot.loadMotor.set(loadMotorSpeed);
    }
}