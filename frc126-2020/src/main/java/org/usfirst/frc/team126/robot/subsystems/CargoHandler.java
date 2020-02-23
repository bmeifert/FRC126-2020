package org.usfirst.frc.team126.robot.subsystems;

import org.usfirst.frc.team126.robot.Robot;
import org.usfirst.frc.team126.robot.commands.*;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DigitalSource;
import edu.wpi.first.wpilibj.command.Subsystem;

public class CargoHandler extends Subsystem {
    private Solenoid intekeSolenoid;

    public CargoHandler () {
        intekeSolenoid = new Solenoid(2);
    }

    public void initDefaultCommand() {
        setDefaultCommand(new CargoWork());
    }


    public void runThrower() {
        //Robot.throwerMotor1.set(1);
        //Robot.throwerMotor2.set(1);
    }
    
    public void stopThrower() {
        //Robot.throwerMotor1.set(0);
        //Robot.throwerMotor2.set(0);
    }

    public void extendIntake() {
        intekeSolenoid.set(true);
    }

    public void retractIntake() {
        intekeSolenoid.set(false);
    }

    public void runPickup() {
        //Robot.pickupMotor.set(1);
    }

    public void stopPickup() {
        //Robot.pickupMotor.set(0);
    }

    public void runloadMotor() {
        //Robot.loadMotor.set(1);
    }

    public void stoploadMotor() {
        //Robot.loadMotor.set(0);
    }
}