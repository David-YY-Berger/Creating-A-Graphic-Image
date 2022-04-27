package geometries;

import primitives.Double3;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public class Plane extends Geometry{

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


    //DELEETE THIS!
//    /**
//     *Calculate and return the intersection point/s between the plane and the ray got by parameter
//     * return null if there are no points
//     */
//    @Override
//    public List<Point> findIntersections(Ray ray) {
//
//        // We will use the formula t = (n * (Q - p0)) / (n * v)
//        // When n = normal of the plane
//        //      Q = the point on the plane (from Constructor)
//        //      p0 = beginning point of the ray
//        //      v = the direction of the ray
//        //      t = the distance between the beginning of the ray to the intersection point on the plane
//
//        Vector p0_Q;
//
//        try {
//            p0_Q = p0.subtract(ray.getP0()); //built a vector from the beginning of ray to the point on plane
//        }
//        catch (IllegalArgumentException e){
//            return null; //no intersection
//        }
//
//        double denominator = normalVector.dotProduct(ray.getDirVector());
//
//        if (isZero(denominator)){
//            return null; // the ary is parallel to the plane - no intersection
//        }
//
//        double t = alignZero(normalVector.dotProduct(p0_Q) / denominator);
//
//        if(t < 0){
//            return  null; //the direction is opposite to the plane - no intersection
//        }
//        if (t ==0) {
//            return null; // the ray starts on the plane
//        }
//        else{
//            return List.of(ray.getPoint(t));
//        }
//    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        // We will use the formula t = (n * (Q - p0)) / (n * v)
        // Where n = normal of the plane
        //      Q = the point on the plane (from Constructor)
        //      p0 = beginning point of the ray
        //      v = the direction of the ray
        //      t = the distance between the beginning of the ray to the intersection point on the plane

        Vector p0_Q;

        try {
            p0_Q = p0.subtract(ray.getP0()); //built a vector from the beginning of ray to the point on plane
        }
        catch (IllegalArgumentException e){
            return null; //no intersection
        }

        double denominator = normalVector.dotProduct(ray.getDirVector());

        if (isZero(denominator)){
            return null; // the ary is parallel to the plane - no intersection
        }

        double t = alignZero(normalVector.dotProduct(p0_Q) / denominator);

        if(t < 0){
            return  null; //the direction is opposite to the plane - no intersection
        }
        if (t ==0) {
            return null; // the ray starts on the plane
        }
        else{
            return List.of(new GeoPoint(this, ray.getPoint(t)));
        }
    }
}
