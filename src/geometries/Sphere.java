package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

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
    @Override
    public List<Point> findIntsersections(Ray ray) {
        return null;
    }
}
