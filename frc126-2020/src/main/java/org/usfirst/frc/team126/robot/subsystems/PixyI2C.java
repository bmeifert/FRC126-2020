package org.usfirst.frc.team126.robot.subsystems;

import edu.wpi.first.wpilibj.I2C;

public class PixyI2C {
	String name;
	PixyPacket values;
	I2C pixy;
	PixyPacket[] packets;
	PixyException pExc;
	String print;
	boolean lastNoData=false;

	public PixyI2C(String id, I2C argPixy, PixyPacket[] argPixyPacket, PixyException argPixyException,
			PixyPacket argValues) {
		pixy = argPixy;
		packets = argPixyPacket;
		pExc = argPixyException;
		values = argValues;
		name = "Pixy_" + id;
		lastNoData=false;
	}

	// Convert the raw bytes in integers with endian conversion
	public int cvt(byte upper, byte lower) {
		return (((int) upper & 0xff) << 8) | ((int) lower & 0xff);
	}

	public byte getByte() {
		byte[] rawData = new byte[1];

		//SmartDashboard.putString("Pixy getPacketData", "getByte");

		try {
			//SmartDashboard.putString("Pixy getPacketData", "readOnly");
			pixy.readOnly(rawData, 1);
		} catch (RuntimeException e) {
			//SmartDashboard.putString(name + "Status", e.toString());
			//System.out.println("getByte RuntimeException " + e.toString());
			return 0;
		}
		if (rawData.length < (1)) {
			//SmartDashboard.putString(name + "Status", "raw data length " + rawData.length);
			//System.out.println("getByte rawData.length " + rawData.length);
			return 0;
		}
		//System.out.println("getByte returning " + String.format("0x%02X", rawData[0]));
		return (rawData[0]);
	}

<<<<<<< Updated upstream
	public boolean findStart() {
		boolean frameStart=false, firstSync=false;
		int tries=512;
		byte b1, b2, zeroCount=0;
				
		//SmartDashboard.putString("Pixy getPacketData", "findStart");

		while (!frameStart && tries-- > 0) {
			b1 = getByte();
			//SmartDashboard.putString("Pixy b1", String.format("0x%02X", b1));
			//if (firstSync) {
				//System.out.println("firstSync true, b1 = " + String.format("0x%02X", b1));
			//}	
			if (b1 == (byte)0x55) {
				zeroCount=0;
				//System.out.println("Found b1 = " + String.format("0x%02X", b1));
				b2 = getByte();
			} else {
				if (b1 == (byte)0x00) {
					if (zeroCount++ > 24) {
						if (!lastNoData) {
							  System.out.println("No data");
						}
						lastNoData=true;	  
						return(false);
					}	
				} else {
					zeroCount=0;
				}
				continue;
			}

			zeroCount=0;
			if (b2 == (byte)0xAA && b1 == (byte)0x55) {
				//System.out.println("Found b1 = 0xaa, b2 = 0x55, firstSync:" + firstSync);
				if (firstSync == true) {
					frameStart = true;
					break;
				} else {
					firstSync = true;	
				}
			} else {
				firstSync = false;
			}
		}

		lastNoData=false;
		return frameStart;
=======
    private int readResponse() {
		byte b1, b2;
		byte[] rawData = new byte[256];

		b1 = getByte();
		b2 = getByte();

		// Verify Packet Signature 
		if ( b2 != (byte)0xc1 || b1 != (byte)0xaf) {
			System.out.println(String.format("Did not find start of frame, b1: 0x%02X b2 0x%02X", b1,b2));
			return -1;
		}	
		if (getByte() != (byte)1) {
			System.out.println("Did not find good packet type");
			return -1;
		}		

		int len = (int)getByte();
		// Read the rest of the response
		if ( len > 0 ) {
		     pixy.readOnly(rawData,len);
		}
		
 		return 0;
>>>>>>> Stashed changes
	}

	public int readPacket(boolean firstPacket) {

		//SmartDashboard.putString("Pixy getPacketData", "readPacket (" + firstPacket + ")");

		byte[] rawData = new byte[14];
		int Checksum, Signature, keyWord, offset = 0, readSize = 14;
		
		if (firstPacket) { readSize = 12; }

		try {
	    	pixy.readOnly(rawData,readSize);
	    } catch (RuntimeException e) {
	    	//SmartDashboard.putString(name + "Status", e.toString());
	    	System.out.println(name + "  " + e);
	    	return -1;
	    }
	    if (rawData.length < readSize) {
	    	//SmartDashboard.putString(name + "Status", "raw data length " + rawData.length);
	    	System.out.println("byte array length is broken length=" + rawData.length);
	    	return -1;
		}
		
		if (!firstPacket) {
			keyWord = cvt(rawData[1], rawData[0]);
			if (keyWord != 0xaa55) { 
				//System.out.println("1.) Found bad keyword: " + String.format("0x%04X", keyWord));
				return -1;	
			}
			offset = 2;
		}
		
		// This next block parses the rest of the data
		Checksum = cvt(rawData[offset+1], rawData[offset+0]);

		if (Checksum == 0xaa55 || Checksum == 0xaa56) {
			// Found the start of new frame, stop 
			//System.out.println("Found the start of new frame, stop: " + String.format("0x%04X", Checksum));
			return -1; 
		}
		Signature = cvt(rawData[offset+3], rawData[offset+2]);
		if (Signature <= 0 || Signature > packets.length) {
			// Bad Signature, return
			return -1;
		}

		int x,y,w,h;

		x = cvt(rawData[offset+5], rawData[offset+4]);
		y = cvt(rawData[offset+7], rawData[offset+6]);
		w = cvt(rawData[offset+9], rawData[offset+8]);
		h = cvt(rawData[offset+11], rawData[offset+10]);

		if (Checksum != Signature + x + y + w + h) {
			return -1;
		}

		packets[Signature - 1].Signature = Signature;
		packets[Signature - 1].X = x;
		packets[Signature - 1].Y = y;
		packets[Signature - 1].Width = w;
		packets[Signature - 1].Height = h;
		packets[Signature - 1].isValid = true;

	    return 0;
	}

	public void readPackets() throws PixyException {
		int ret=0,count=32;

		//SmartDashboard.putString("Pixy getPacketData", "readPackets");

		if (!findStart()) { return; }

		ret = readPacket(true);

		while (ret == 0 && count-- > 0) {
			//SmartDashboard.putString("Pixy getPacketData", "readPackets count " + count);
			ret=readPacket(false);
		}
	}
}