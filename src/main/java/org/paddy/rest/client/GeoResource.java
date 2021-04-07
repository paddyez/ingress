package org.paddy.rest.client;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.paddy.geometry.Polygon;
import org.paddy.geometry.Polyline;
import org.paddy.geometry.Shape;
import org.paddy.rest.dta.DrawObject;
import org.paddy.rest.dta.Point;

@Path("/ingress/geo")
public class GeoResource {
    public static final String HELLO = "{\"Hello\": \"RESTEasy\"}";

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String hello() {
        return HELLO;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String post(DrawObject[] drawObjects) {
        for (DrawObject drawObject : drawObjects) {
            if (drawObject.getType().equals("polyline")) {
                final Polyline polyline = new Polyline(drawObject);
                final Double distance = polyline.getDistance();
                System.out.println("The distance between the two points is: " + distance + " m.");
            } else if (drawObject.getType().equals("polygon")) {
                final Polygon polygon = new Polygon(drawObject);
                final Double[] distances = polygon.getDistances();
                for(Double distance : distances) {
                    //System.out.println("Distance: " + distance);
                }
                Double[] angles = polygon.getAngles();
                Double sphericalTriangleArea = Shape.sphericalTriangleArea(angles);
                System.out.println("Spherical triangle area:\t" + sphericalTriangleArea + " m².");
                System.out.println("Area:\t\t\t\t" + polygon.getAreaTriangle() + " m².");
            } else {

            }
        }
        return HELLO;
    }
}