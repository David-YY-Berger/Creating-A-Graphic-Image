package geometries;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * abstract class - bec we want this each child class to hold a field,
 * and also implement the general functions of getNormal()...
 */
public abstract class Geometry extends Intersectable {

    protected Color emission = Color.BLACK;
    public Color getEmission() {
        return emission;
    }
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    public abstract Vector getNormal(Point pointOnSurface);


}
