package org.paddy.geometry;

public class DegGradRad {
    public static Double degToRad(Double angle) {
        return angle * Math.PI / 180;
    }

    public static Double radToDeg(Double angle) {
        return angle * 180 / Math.PI;
    }
}
