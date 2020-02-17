package org.usfirst.frc.team126.robot.subsystems;

import org.usfirst.frc.team126.robot.commands.*;
import org.usfirst.frc.team126.robot.Robot;
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

	public double trackTargetPosition(int objectID) {
		double targetPosition;

		if (Robot.trackTarget) {
			// We are tracking the throwing target, not the ball
			return 0;
		}
		
		if ( !Robot.vision.packetData[objectID].isValid ) {
			Robot.robotTurn = 0;
			Robot.robotDrive = 0;
			return 0;
		}
		
		int y = Robot.vision.packetData[objectID].Y;
		int x = Robot.vision.packetData[objectID].X;
		int h = Robot.vision.packetData[objectID].Height;
		int w = Robot.vision.packetData[objectID].Width;
		int sx = Robot.vision.getServoX();
		int sy = Robot.vision.getServoY();
	
	    targetPosition = Turret.getTargetPosition(0,objectID);

		double area = h * w;
		if (objectID == 1) {
			if ( area < 2000 ) {
				Robot.robotDrive=.2;
			} else {
				Robot.robotDrive=0;
			}
		} else {
			Robot.robotDrive=0;
		}

		System.out.println("valid " + x + " tp:" + targetPosition 
				 + " cx:" + x + " sx" + sx + " h:" + h
				 + " w: " + w + " a:" + area);

		double turnFactor = .25;
	    if (Robot.robotDrive != 0) {
			turnFactor = .15;
		}
		if ( targetPosition < -200) {
			System.out.println("Move Left");
			Robot.robotTurn= turnFactor * -1;
		} else if ( targetPosition > 200) {
			System.out.println("Move Right");
			Robot.robotTurn=turnFactor;  
		} else {		 
			 System.out.println("Move Center");
			 Robot.robotTurn=0;
		}  		 

		return targetPosition;
	}
}