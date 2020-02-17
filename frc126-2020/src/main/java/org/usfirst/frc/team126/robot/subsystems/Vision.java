package org.usfirst.frc.team126.robot.subsystems;

import org.usfirst.frc.team126.robot.commands.*;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team126.robot.subsystems.PixyPacket;

public class Vision extends Subsystem {

    public PixyI2C Pixy;
	public PixyPacket[] packetData;
	String print;

	int servoX, servoY;

    public Vision() {
		Pixy = new PixyI2C("pixy", new I2C(Port.kOnboard, 0x54));
		packetData = new PixyPacket[24];
		for (int x=0; x<24; x++) {	
			packetData[x] = new PixyPacket();
		}
		centerServo();
		setServo();
    }

	public int getServoX() {
       return servoX;
	}

	public int getServoY() {
		return servoY;
	}

    public void setServoX(int value) {
		servoX=value;
		if (servoX < 15) { servoX = 15; }
		if (servoX > 500 ) { servoX = 500; }
	}

	public void setServoY(int value) {
		servoY=value;
		if (servoY < 15) { servoY = 15; }
		if (servoY > 500 ) { servoY = 500; }
	}

	public void incrServoX(int value) {
		int tmp = servoX + value;
		setServoX(tmp);
	}

	public void incrServoY(int value) {
		int tmp = servoY + value;
		setServoY(tmp);
	}

	public void centerServo() {
		setServoX(250);
		setServoY(300);
	}

    public void initDefaultCommand() {
		setDefaultCommand(new CameraData());
	}

	public void setLED(int red, int green, int blue) {
		try {
			Pixy.setLED(red, green, blue);
		} catch (PixyException e) {
			SmartDashboard.putString("Pixy Error: ", "exception");
		}
	}

	public void setLamp(boolean upperOn, boolean lowerOn) {
		try {
			Pixy.setLamp(upperOn, lowerOn);
		} catch (PixyException e) {
			SmartDashboard.putString("Pixy Error: ", "exception");
		}
	}

	public void setServo() {
		try {
			Pixy.setServo(servoX, servoY);
		} catch (PixyException e) {
			SmartDashboard.putString("Pixy Error: ", "exception");
		}
	}

	public void getItems(int objectId, int maxBlocks) {
		try {
			Pixy.getBlocks(objectId, maxBlocks, packetData);
		} catch (PixyException e) {
			SmartDashboard.putString("Pixy Error: ", "exception");
		}	
	}
}