package geometries;

import primitives.Point;
import primitives.Vector;

public class Sphere implements Geometry{

    Point center;
    Double radius;

    public Sphere(Point _point, double _radius) {
        center = _point;
        radius = _radius;
    }

    @Override
    public Vector getNormal(Point pointOnSurface) {
        //THROW EXCEPTIONS if point is not on surface (illegal paramter)
        //if inside the sphere:

        //if outside the sphere"
        return null;
    }

    public Double getRadius() {
        return radius;
    }
    public Point getCenter() {
        return center;
    }
}
