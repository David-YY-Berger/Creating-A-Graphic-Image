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
        Vector v1 = pointOnSurface.subtract(center);
        return v1.normalize();
    }

    public Double getRadius() {
        return radius;
    }
    public Point getCenter() {
        return center;
    }
}
