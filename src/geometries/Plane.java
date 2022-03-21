package geometries;

import primitives.Double3;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

public class Plane implements Geometry{

    Point p0;
    Vector normalVector;

    public Plane(Point p, Vector vec){
        normalVector = vec.normalize();
        p0 = p;
    }
    public Plane(Point p1, Point p2, Point p3){
        //throw EX if points are the same..
        if(p1.equals(p2) || p1.equals(p3) || p2.equals(p3))
            throw new IllegalArgumentException("2 of the 3 points are identical!");

        //throw EX if all 3 points are on the same line..
        //(1) create vectors
        Vector p1ToP2 = p2.subtract(p1);
        Vector p1ToP3 = p3.subtract(p1);
        Vector orthogonalVector;
        //(2) check if their cross product is 0 (if it is, then they are parallel...)
        try{
            orthogonalVector = p1ToP2.crossProduct(p1ToP3);
        }
        catch(IllegalArgumentException ex){
            throw new IllegalArgumentException("the three points are on the same line!");
        }
        //if no issues, CREATE PLANE:
        p0 = p1;// arbitrary choice
        normalVector = orthogonalVector.normalize();
    }

    //GETTERS:
    @Override
    public Vector getNormal(Point pointOnSurface) {
        //no need to check if point is on plane:
        return normalVector;
    }
    public Point getP0() {
        return p0;
    }
    public Vector getNormal() { return normalVector;}

    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }
}
