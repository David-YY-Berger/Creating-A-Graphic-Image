package primitives;

import geometries.Intersectable.GeoPoint;

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
        //calculate nv - to see if point is on same side of ray (and should be pulled closer),
        // if point is on different side of ray (and shouble be pushed farther)
        double nv = origVector.normalize().dotProduct(normalVec);
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

//            return lst == null || lst.isEmpty() ? null
//                    : findClosestGeoPoint(lst.stream().map(p -> new GeoPoint(null, p)).toList()).point;
//        }
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

}
