package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

public class Triangle extends Plane{

    public Triangle(Point p1, Point p2, Point p3) {
        super(p1, p2, p3);
    }
    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }
    //uses Plane's functions...
}
