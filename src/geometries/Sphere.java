package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

public class Sphere implements Geometry{

    Point center;
    Double radius;

    public Sphere(double _radius, Point _point) {
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

    /** @return list of intersection points btw this circle, and _ray
     * if there are no intersetion points, returns null
     * @param _ray
     */
    @Override
    public List<Point> findIntersections(Ray _ray) {

        // SEE README FILE FOR ILLUSTRATION!
        Vector u = center.subtract(_ray.getP0()); //u goes from _ray's p0 --> Circle's center

        //if no intersection points:
        return null;
        //create LinkedList
        //List<Point> res = new LinkedList<>();
    }
}
