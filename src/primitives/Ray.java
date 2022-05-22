package primitives;

import geometries.Intersectable.GeoPoint;
import org.junit.jupiter.api.MethodOrderer;

import java.lang.invoke.ConstantCallSite;
import java.util.LinkedList;
import java.util.List;

/**
 * this class is holds a beginning point and a direction... used to color different points in our 3d plane
 */
public class Ray {

    protected Point p0; // beginning point
    protected Vector dirVector; //direction vector
    private static final double DELTA = 0.1; // moves the point "outside" of the shape.. see CTOR#2


    //CTOR #1 - w basic parameters
    public Ray(Point _point, Vector directionVec){
        p0 = _point;
        dirVector = directionVec.normalize();
    }

    /** CTOR #2 - builds Ray, moves p0 in direction of camera vector (to prevent issues of a geometry "shading itself"
     * @param point orig point.. the CTOR moves the point (w/ DELTA) in the direction of the light
     * @param origVector vector that "sees" the point....
     * @param normalVec used to calculate a double "nv" - to ascertain on which side the point is...
     //* @param finalDir assigned to the ray... in case we needed to calculate point, but want the final direction to point somewhere else
     */
    public Ray(Point point, Vector origVector, Vector normalVec) {

        // moves the point "outside" of the shape -
        //to ensure that the shape does not "shade" itself
        p0 = point.add(normalVec.scale(origVector.dotProduct(normalVec) >= 0 ? DELTA : -DELTA));
        dirVector = origVector;
    }

        @Override
    public boolean equals(Object obj) {
        //if same object
        if(this == obj) //checks by address...
            return true;
        else if(obj == null  //if different classes, reject...
                || obj.getClass()!= this.getClass())
            return false;

        // cast to vector to check properly
        Ray r = (Ray)obj;
        return (r.p0.equals(this.p0)
        && r.dirVector.equals(this.dirVector)); //returns bool val
    }
    @Override
    public String toString()
    {
        return "Ray, p0 = " + p0.toString() + " direction vector = " + dirVector.toString();
    }

    public Point getP0() {
        return p0;
    }
    public Vector getDirVector() {
        return dirVector;
    }

    /**
     * get a scalar and return the point on the ray at distance t from the beginning of the ray
     */
    public Point getPoint(double t){
        if(t == 0)
            return p0;
        try{
           return p0.add(dirVector.scale(t));

        }
        catch (IllegalArgumentException e) {
            return null;
        }
    }
    /** @return closest point to p0 (beginning of the ray)
     * @param lst contains diff points on the ray,
     */
    public Point findClosestPoint(List<Point> lst){

        if(lst == null)
            return  null;
        else if(lst.isEmpty())
            throw new IllegalArgumentException("list given as parameter is empty!");
        else
        {
            List<GeoPoint> listToSendAsParam = new LinkedList<>();
            for (Point point: lst
                 ) {
                listToSendAsParam.add(new GeoPoint(null, point));
            }
            return findClosestGeoPoint(listToSendAsParam).point;
        }


    }
    public GeoPoint findClosestGeoPoint(List<GeoPoint> lst){

        if(lst.isEmpty())
            throw new IllegalArgumentException("list given as parameter is empty!");

        double shortestDist = Double.POSITIVE_INFINITY; //any distance will be shorter, and res will be reset
        GeoPoint res = null;
        for (GeoPoint geoPoint: lst) {
                double dist = p0.distance(geoPoint.point);
                if (dist < shortestDist) {
                    shortestDist = dist;
                    res = geoPoint;
                }
        }
        return res;

    }

    /**
     * getRandomRays() returns list of randomly calculated rays,
     * each one starting from this p0, and landing in a target area (a circle around destPoint)...
     * @param destPoint center of target area (circle)
     * @param glossinessParam usually btw (0, .1) determines size of target area circle (how "glossy" the point is meant to be....for more blurry, target area is bigger, rays are "more" random)
     * @return list of the rays
     */
    public List<Ray> getRandomRays(Point destPoint, double glossinessParam){

        //for theory of function, see: 2nd answer at https://math.stackexchange.com/questions/4242584/picking-a-random-point-from-a-circle-in-3d
        //based on PARAMETRIC REPRESENTATION OF GENERIC POINT ON CIRCLE:
        //Point = Center +r*u*cos(t)+r*v*sin(t), t∈[0,2π). where u,v are perpendicular Vectors on circle, and r is radius

        final int NUM_RANDOM_RAYS = 100;

        if (NUM_RANDOM_RAYS == 1)
            return List.of(this);

        //assuming that glossinessParam is btw (0, 0.1)
        double ratio = 1000; //maybe should change this??
        double radiusOfTargArea =  ratio * glossinessParam; //example, if glossiness = .1, so radious = 10
        //double distance = p0.distance(destPoint);

        List<Ray> res = new LinkedList<>();
        Vector u = dirVector.perpendicVecNormalized(); //u is perpendicular to Ray's direction
        Vector v = dirVector.crossProduct(u); // v is perpendicular to both u and Ray's direction

        //delete this check:
        if(!Util.isZero(u.dotProduct(v))
        || !Util.isZero(u.dotProduct(dirVector))
        || !Util.isZero(dirVector.dotProduct(v)))
            throw new ArithmeticException();

        double t = 0;
        for (int i = 0; i < NUM_RANDOM_RAYS; i++) {
            t = Util.random(0, 2*Math.PI); // for any angle btw 0degrees and 360 degrees, in Radians...
            //pointOnCircle is for debugging... delete this variable..
            Point pointOnCircle = p0.add(dirVector.scale(ratio))
                    .add( u.scale(radiusOfTargArea).scale(Math.cos(t))) //add in the U direction
                    .add( v.scale(radiusOfTargArea).scale(Math.sin(t)));//add in the V direction
            res.add(new Ray(p0, pointOnCircle.subtract(p0))); //construct ray from p0 to random point on circle...

            //for debugging:
            //System.out.println("point on circle: " + pointOnCircle);

        }
        return res;
    }

}
