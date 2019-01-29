package org.usfirst.frc.team126.robot.subsystems;

import org.usfirst.frc.team126.robot.commands.*;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Vision extends Subsystem {

    public PixyI2C Pixy;
    public PixyPacket[] packetData = new PixyPacket[8];
    String print;

    public Vision() {
		Pixy = new PixyI2C("pixy", new I2C(Port.kOnboard, 0x54), packetData, new PixyException(print), new PixyPacket());
    }
    
    public void initDefaultCommand() {
		setDefaultCommand(new CameraData());
		for (int i = 0; i < packetData.length; i++) {
			packetData[i] = new PixyPacket();
		}
    }

    public void getPacketData() {
		// Clear out the old data in the packets
		for (int i = 0; i < packetData.length; i++) {
			if (packetData[i] == null) {
				System.out.println("Packetdata[" + (i) + "] == NULL");
			} else {
				packetData[i].isValid=false;
			}	
		}

		SmartDashboard.putString("Pixy getPacketData", "Retrieving Data");

		// Try and read the packets from the Pixy 
		try {
			Pixy.readPackets();
		} catch (PixyException e) {
			SmartDashboard.putString("Pixy Error: ", "exception");
			for (int i = 0; i < packetData.length; i++) {
				packetData[i].isValid=false;
			}
		}

		for (int i = 0; i < packetData.length; i++) {
			if (!packetData[i].isValid) {
				// If we hit an error, mark the packet as invalid
				SmartDashboard.putString("Pixy Error: " + (i+1), "True");
				SmartDashboard.putNumber("Pixy X Value: " + (i+1), 99999999);
				SmartDashboard.putNumber("Pixy Y Value: " + (i+1), 99999999);
				SmartDashboard.putNumber("Pixy Width Value: " + (i+1), 99999999);
				SmartDashboard.putNumber("Pixy Height Value: " + (i+1), 99999999);
			} else {
				int x,y,h,w;
				x=packetData[i].X;
				y=packetData[i].Y;
				w=packetData[i].Width;
				h=packetData[i].Height;
				String xmove,ymove,distance;

				// If the read was good, mark the packet valid 
	        	SmartDashboard.putString("Pixy Error: " + (i+1), "False");
				SmartDashboard.putNumber("Pixy X Value: " + (i+1), x);
				SmartDashboard.putNumber("Pixy Y Value: " + (i+1), y);
				SmartDashboard.putNumber("Pixy Width Value: " + (i+1), w);
				SmartDashboard.putNumber("Pixy Height Value: " + (i+1), h);

				if (x < 135) { 
					xmove = "Move Left"; 
				} else if (x > 165) { 
					xmove = "Move Right"; 
				} else {
					 xmove = "X-Centered"; 
				} 

				if (y < 80) { 
					ymove = "Move Up"; 
				} else if (y > 120) { 
					ymove = "Move Down"; 
				} else {
					 ymove = "Y-Centered"; 
				} 

				System.out.println("Packet " + (i+1) + " " + xmove + " " + ymove + " = " + packetData[i].toString());
			}
		}	

		SmartDashboard.putString("Pixy getPacketData", "done");
	}
}