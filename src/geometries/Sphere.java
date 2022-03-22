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

    /** findIntersections():
     * @return list of intersection points btw this circle, and _ray
     * if there are no intersetion points, returns null
     */
    @Override
    public List<Point> findIntersections(Ray _ray) {

        // SEE README FILE FOR ILLUSTRATION! - Diagram 1.1
        //(1) find u [vector u goes from _ray's p0 --> Circle's center]
        Vector u = center.subtract(_ray.getP0());
        //(2) find projection of u onto Ray (to find vertex of right triangle)
        Vector tm = _ray.getDirVector().projection(u);
        //Vector d = Math.sqrt(u.lengthSquared() - tm.lengthSquared());

        // if no intersection points:
        return null;
        //create LinkedList
        //List<Point> res = new LinkedList<>();
    }
}
