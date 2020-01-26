package org.usfirst.frc.team126.robot.util.config;

import java.awt.*;
import java.util.Properties;

/**
 * Class that allows user to interface with sensors.properties
 * config file with easy-to-access methods.
 * <p>
 * Note that this class should NOT be modified; if settings need to be
 * modified, edit the sensors.properties file.
 */
@Deprecated
public class SensorsSettings extends Config {
    private static Properties instance;

    public static void initialize() {
        instance = getInstance();
        loadProperties("sensors.properties");
    }
    
    /**
     * Gets the resolution of the camera's feed
     *
     * @return dimension of resolution
     */
    public static Dimension getCameraResolution() {
        String[] arr = parseArrayValue("camera.resolution", ",");
        int[] res = {320, 240};

        if (arr.length == 2) {
            res[0] = Integer.parseInt(arr[0]);
            res[1] = Integer.parseInt(arr[1]);
        }

        return new Dimension(res[0], res[1]);
    }

    /**
     * Gets the FOV of the camera
     *
     * @return double array containing both horizontal and vertical FOV
     */
    public static double[] getCameraFOV() {
        String[] arr = parseArrayValue("camera.FOV", ",");
        double[] fov = {61, 34.3};

        if (arr.length == 2) {
            fov[0] = Double.parseDouble(arr[0]);
            fov[1] = Double.parseDouble(arr[1]);
        }

        return fov;
    }

    /**
     * Gets the latency between the RIO and the Pi's CameraServer.
     *
     * @return latency in ms
     */
    public static long getCameraServerLatency() {
        String val = instance.getProperty("camera.latencyMS", "5000");

        return Long.parseLong(val);
    }
}
