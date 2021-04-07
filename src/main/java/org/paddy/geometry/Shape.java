package org.paddy.geometry;

import org.paddy.rest.dta.Point;

public class Shape {
    public static Double haversineDistanceInMeters(Point point0, Point point1) {
        final Double latDistance = DegGradRad.degToRad(point1.getLat() - point0.getLat());
        final Double lngDistance = DegGradRad.degToRad(point1.getLng() - point0.getLng());
        final Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(DegGradRad.degToRad(point0.getLat())) * Math.cos(DegGradRad.degToRad(point1.getLat()))
                        * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);
        final Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        final Double distance = Earth.RADIUS * c;
        return distance;
    }

    public static Double sphericalTriangleArea(Double[] angles) {
        final Double angleSum = angles[0] + angles[1] + angles[2] > Math.PI ? angles[0] + angles[1] + angles[2] - Math.PI : angles[0] + angles[1] + angles[2];
        System.out.println("Sum of angles: " + angleSum);
        return angleSum * Math.pow(Earth.RADIUS, 2);
    }
}
