package org.paddy.geometry;

import org.paddy.rest.dta.DrawObject;
import org.paddy.rest.dta.Point;

public class Polyline extends Shape{
    private final DrawObject drawObject;

    public Polyline(DrawObject drawObject) {
        this.drawObject = drawObject;
        System.out.println("Polyline");
        for (Point point : drawObject.getLatLngs()) {
            //System.out.println(point.getLat() + " " + point.getLng());
        }
    }

    public Double getDistance() {
        return Shape.haversineDistanceInMeters(this.drawObject.getLatLngs()[0], this.drawObject.getLatLngs()[1]);
    }
}
