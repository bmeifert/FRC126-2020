package org.usfirst.frc.team126.robot.subsystems;

import org.usfirst.frc.team126.robot.Robot;
import org.usfirst.frc.team126.robot.RobotMap;
import org.usfirst.frc.team126.robot.commands.*;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Vision extends Subsystem {

    public PixyI2C Pixy;
    public PixyPacket[] packet1 = new PixyPacket[7];
    public PixyPacket[] packet2 = new PixyPacket[7];
    String print;

    public Vision() {
		Pixy = new PixyI2C("pixy", new I2C(Port.kOnboard, 0x54), packet1, new PixyException(print), new PixyPacket());
    }
    
    public void initDefaultCommand() {
	}

    public void testPixy() {
		for (int i = 0; i < packet1.length; i++)
			packet1[i] = null;
		SmartDashboard.putString("Pixy hello", "working");
		for (int i = 1; i < 8; i++) {
			try {
				packet1[i - 1] = Pixy.readPacket(i);
			} catch (PixyException e) {
				SmartDashboard.putString("Pixy Error: " + i, "exception");
			}
			if (packet1[i - 1] == null) {
				SmartDashboard.putString("Pixy Error: " + i, "True");
				continue;
			}
			SmartDashboard.putNumber("Pixy X Value: " + i, packet1[i - 1].X);
			SmartDashboard.putNumber("Pixy Y Value: " + i, packet1[i - 1].Y);
			SmartDashboard.putNumber("Pixy Width Value: " + i, packet1[i - 1].Width);
			SmartDashboard.putNumber("Pixy Height Value: " + i, packet1[i - 1].Height);
            SmartDashboard.putString("Pixy Error: " + i, "False");
            System.out.println("Pixy X Value: " + i + "=" + packet1[i - 1].X);
            System.out.println("Pixy Y Value: " + i + "=" + packet1[i - 1].Y);
            System.out.println("Pixy Width Value: " + i + "=" + packet1[i - 1].Width);
            System.out.println("Pixy Height Value: " + i + "=" + packet1[i - 1].Height);
        }
        
	}
}