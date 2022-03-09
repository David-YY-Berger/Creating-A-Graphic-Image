package geometries;

import primitives.Point;
import primitives.Vector;

public class Sphere implements Geometry{

    Point center;
    Double radius;

    public Sphere(Point p, double r) {
        center = p;
        radius = r;
    }

    @Override
    public Vector getNormal(Point pointOnSurface) {
        return null;
    }

    public Double getRadius() {
        return radius;
    }
    public Point getCenter() {
        return center;
    }
}
