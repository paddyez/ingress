package org.paddy.geometry;

import java.math.BigDecimal;

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

    public static BigDecimal sphericalTriangleArea(BigDecimal[] angles) {
        final BigDecimal angleSum = angles[0].add(angles[1]).add(angles[2]).subtract(new BigDecimal(Math.PI));
        System.out.println("Sum of angles: " + angleSum.doubleValue());
        return angleSum.multiply(new BigDecimal(Math.pow(Earth.RADIUS, 2)));
    }
}
