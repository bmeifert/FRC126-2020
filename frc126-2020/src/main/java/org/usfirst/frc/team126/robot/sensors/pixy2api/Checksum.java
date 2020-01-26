package org.usfirst.frc.team126.robot.sensors.pixy2api;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class Checksum {

    int cs = 0;

    public void updateChecksum(int b) {
        cs += b;
    }

    public int getChecksum() {
        return cs;
    }

    public void reset() {
        cs = 0;
    }

}