package org.usfirst.frc.team126.robot.subsystems;

import org.usfirst.frc.team126.robot.commands.*;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalSource;
import edu.wpi.first.wpilibj.command.Subsystem;

public class LidarLite extends Subsystem {
/*
 * Adjust the Calibration Offset to compensate for differences in each unit.
 * You can also use the offset to zero out the distance between the sensor and edge of the robot.
 */
private static final int CALIBRATION_OFFSET = -5;
private double[] dataSeries;

private Counter counter;
private int printedWarningCount = 5;

/**
 * Create an object for a LIDAR-Lite attached to some digital input on the roboRIO
 * 
 * @param source The DigitalInput or DigitalSource where the LIDAR-Lite is attached (ex: new DigitalInput(9))
 */
public LidarLite (DigitalSource source) {
	counter = new Counter(source);
    counter.setMaxPeriod(1.0);
    // Configure for measuring rising to falling pulses
    counter.setSemiPeriodMode(true);
    counter.reset();
    dataSeries = new double[10];
}

public void initDefaultCommand() {
    setDefaultCommand(new DistanceMeasure());
}

/**
 * Take a measurement and return the distance in cm
 * 
 * @return Distance in cm
 */
public double getDistance() {
	double cm;
	/* If we haven't seen the first rising to falling pulse, then we have no measurement.
	 * This happens when there is no LIDAR-Lite plugged in, btw.
	 */
	if (counter.get() < 1) {
		if (printedWarningCount-- > 0) {
			System.out.println("LidarLite: waiting for distance measurement");
		}
		return 0;
	}
	/* getPeriod returns time in seconds. The hardware resolution is microseconds.
	 * The LIDAR-Lite unit sends a high signal for 10 microseconds per cm of distance.
	 */
    cm = (counter.getPeriod() * 1000000.0 / 10.0) + CALIBRATION_OFFSET;
    
    for (int x=1; x<10; x=x+1) {
        dataSeries[x-1] = dataSeries[x];
    }
    dataSeries[9] = cm;
    
    double sum=0;
    double count=0;
    for (int x=0; x<10; x=x+1) {
        if (dataSeries[x] != 0) {
            count++;
            sum += dataSeries[x];
        } 
    }
        
    cm = sum / count;

	return cm;
}
}