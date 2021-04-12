package org.paddy.geometry;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.paddy.rest.dta.DrawObject;
import org.paddy.rest.dta.Point;

public class Polygon extends Shape {
    private final DrawObject drawObject;
    private final List<Double[]> allCoordinates = new ArrayList<>();

    public Polygon(DrawObject drawObject) {
        this.drawObject = drawObject;
        // System.out.println("Polygon");
        for (Point point : drawObject.getLatLngs()) {
            // System.out.println(point.getLat() + " " + point.getLng());
            final Double[] coordinates = new Double[2];
            coordinates[0] = DegGradRad.degToRad(point.getLng());
            coordinates[1] = DegGradRad.degToRad(point.getLat());
            allCoordinates.add(coordinates);
        }
    }

    public BigDecimal[] getAngles() {
        int size = allCoordinates.size();
        final BigDecimal[] scalarProductAngles = new BigDecimal[size];
        for (int i = 0; i < size; i++) {
            BigDecimal scalarProductAngle = new BigDecimal(0);
            Double[] a = allCoordinates.get(i);
            Double[] b = allCoordinates.get((i + 1) % size);
            Double[] c = allCoordinates.get((i + 2) % size);
            Double one   = (Math.cos(a[0]))*(Math.cos(a[1]))*(b[0] - a[0]);
            Double two   = (Math.sin(a[0]))*(Math.sin(a[1]))*(b[1] - a[1]);
            Double three = (Math.cos(a[0]))*(Math.cos(a[1]))*(c[0] - a[0]);
            Double four  = (Math.sin(a[0]))*(Math.sin(a[1]))*(c[1] - a[1]);
            final Double x =  (one + two) * (three + four);
            one   = (Math.cos(a[0]))*(Math.sin(a[1]))*(b[0] - a[0]);
            two   = (Math.sin(a[0]))*(Math.cos(a[1]))*(b[1] - a[1]);
            three = (Math.cos(a[0]))*(Math.sin(a[1]))*(c[0] - a[0]);
            four  = (Math.sin(a[0]))*(Math.cos(a[1]))*(c[1] - a[1]);
            final Double y =  (one + two) * (three + four);
            final Double z = (-Math.sin(a[0])*(b[0] - a[0])) * (-Math.sin(a[0])*(c[0] - a[0]));
            BigDecimal scalarProduct = new BigDecimal(x + y + z / 2);
            if(scalarProduct.doubleValue() > 1) {
                System.out.println("Resetting scalar: " + scalarProduct.doubleValue());
                scalarProduct = new BigDecimal(0);
            }
            System.out.println("Scalar product: " + scalarProduct.doubleValue());
            scalarProductAngle = new BigDecimal(Math.acos(scalarProduct.doubleValue()));
            System.out.println("Angle: " + scalarProductAngle.doubleValue());
            scalarProductAngles[i] = scalarProductAngle;
        }
        return scalarProductAngles;
    }

    public Double[] getDistances() {
        final int length = drawObject.getLatLngs().length;
        final Double[] distances = new Double[length];
        for (int i = 0; i < length; i++) {
            distances[i] = Shape.haversineDistanceInMeters(this.drawObject.getLatLngs()[i],
                    this.drawObject.getLatLngs()[(i + 1) % length]);
        }
        return distances;
    }

    public double getAreaTriangle() {
        final Double[] distances = getDistances();
        final Double halfCircumference = (distances[0] + distances[1] + distances[2]) / 2;
        final Double area = Math.sqrt(halfCircumference * (halfCircumference - distances[0])
                * (halfCircumference - distances[1]) * (halfCircumference - distances[2]));
        return area;
    }
}
