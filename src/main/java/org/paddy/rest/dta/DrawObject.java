package org.paddy.rest.dta;

import lombok.Data;

@Data
public class DrawObject {
    private String color;
    private Point[] latLngs;
    private String type;
}
