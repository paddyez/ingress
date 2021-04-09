package org.paddy.geometry;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.paddy.rest.dta.DrawObject;
import org.paddy.rest.dta.Point;

public class Polygon extends Shape {
    private final DrawObject drawObject;
    private final List<Double[]> allCartesianCoordinates = new ArrayList<>();

    public Polygon(DrawObject drawObject) {
        this.drawObject = drawObject;
        // System.out.println("Polygon");
        for (Point point : drawObject.getLatLngs()) {
            // System.out.println(point.getLat() + " " + point.getLng());
            final Double[] cartesianCoordinates = new Double[3];
            cartesianCoordinates[0] = Math.cos(DegGradRad.degToRad(point.getLat()))
                    * Math.cos(DegGradRad.degToRad(point.getLng()));
            cartesianCoordinates[1] = Math.cos(DegGradRad.degToRad(point.getLat()))
                    * Math.sin(DegGradRad.degToRad(point.getLng()));
            cartesianCoordinates[2] = Math.sin(DegGradRad.degToRad(point.getLat()));
            allCartesianCoordinates.add(cartesianCoordinates);
        }
    }

    public BigDecimal[] getAngles() {
        int size = allCartesianCoordinates.size();
        final BigDecimal[] scalarProductAngles = new BigDecimal[size];
        for (int i = 0; i < size; i++) {
            BigDecimal scalarProductAngle = new BigDecimal(0);
            Double[] a = allCartesianCoordinates.get(i);
            Double[] b = allCartesianCoordinates.get((i + 1) % size);
            Double[] c = allCartesianCoordinates.get((i + 2) % size);
            BigDecimal scalarProduct = new BigDecimal(((b[0] - a[0]) * (c[0] - a[0]) + (b[1] - a[1]) * (c[1] - a[1]) + (b[2] - a[2]) * (c[2] - a[2])) / 2);
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
