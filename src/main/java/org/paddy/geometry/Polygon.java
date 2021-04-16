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
            coordinates[0] = DegGradRad.degToRad(point.getLat());
            coordinates[1] = DegGradRad.degToRad(point.getLng());
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
            final Double au_0 = (Math.cos(a[0])) * (Math.cos(a[1])) * (b[0] - a[0]);
            final Double bv_0 = (-Math.sin(a[0])) * (Math.sin(a[1])) * (b[1] - a[1]);
            final Double au_1 = (Math.cos(a[0])) * (Math.cos(a[1])) * (c[0] - a[0]);
            final Double bv_1 = (-Math.sin(a[0])) * (Math.sin(a[1])) * (c[1] - a[1]);
            final Double x = (au_0 + bv_0) * (au_1 + bv_1);
            final Double cu_0 = (Math.cos(a[0])) * (Math.sin(a[1])) * (b[0] - a[0]);
            final Double dv_0 = (Math.sin(a[0])) * (Math.cos(a[1])) * (b[1] - a[1]);
            final Double cu_1 = (Math.cos(a[0])) * (Math.sin(a[1])) * (c[0] - a[0]);
            final Double dv_1 = (Math.sin(a[0])) * (Math.cos(a[1])) * (c[1] - a[1]);
            final Double y = (cu_0 + dv_0) * (cu_1 + dv_1);
            final Double z = (-Math.sin(a[0]) * (b[0] - a[0])) * (-Math.sin(a[0]) * (c[0] - a[0]));

            final Double[] aa = { Math.pow(au_0 + bv_0, 2), Math.pow(cu_0 + dv_0, 2),
                    Math.pow(-Math.sin(a[0]) * (b[0] - a[0]), 2) };
            final Double[] bb = { Math.pow(au_1 + bv_1, 2), Math.pow(cu_1 + dv_1, 2),
                    Math.pow(-Math.sin(a[0]) * (c[0] - a[0]), 2) };

            final Double normaa = Math.sqrt(aa[0] + aa[1] + aa[2]);
            final Double normbb = Math.sqrt(bb[0] + bb[1] + bb[2]);
            System.out.println("### Norm: " + normaa + "  " + normbb + " " + normaa * normbb);
            BigDecimal scalarProduct = new BigDecimal((x + y + z) / (normaa * normbb));
            if (scalarProduct.doubleValue() > 1) {
                System.out.println("### Scalar product too big: ### " + scalarProduct.doubleValue());
            }
            System.out.println("Scalar product: " + scalarProduct.doubleValue());
            scalarProductAngle = new BigDecimal(Math.acos(scalarProduct.doubleValue()));
            System.out.println("Angle: " + DegGradRad.radToDeg(scalarProductAngle.doubleValue()) + "°");
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
