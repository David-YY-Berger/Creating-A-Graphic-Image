package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

public class Sphere extends Geometry{

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

    //DELETE THIS
//    /** findIntersections():
//     * @return list of intersection points btw this circle, and _ray
//     * if there are no intersetion points, returns null
//     */
//    @Override
//    public List<Point> findIntersections(Ray _ray) {
//
//        /** SEE README FILE FOR ILLUSTRATION! - Diagram 1.1
//         * names of variables based on the diagram..
//         * note - "u" is stored as a vector, not as a ray...
//         * good explanation on this website - https://www.scratchapixel.com/lessons/3d-basic-rendering/minimal-ray-tracer-rendering-simple-shapes/ray-sphere-intersection#:~:text=Intersecting%20a%20ray%20with%20a,simplicity)%20to%20be%20very%20fast.
//         */
//
//         //(1) find u [vector u goes from _ray's p0 --> Circle's center]
//        if(_ray.getP0().equals(center))
//        {
//            //if ray begins at circle's center...
//            List<Point> res =  new LinkedList<>();
//            res.add(_ray.getP0().add(_ray.getDirVector().scale(radius)));
//            return res;
//        }
//        //else:
//        Vector u = center.subtract(_ray.getP0());
//
//        //(2) find scalar projection (tm) of u onto Ray (to find vertex of right triangle)
//        double tm = _ray.getDirVector().dotProduct(u) ; // tm holds the scalar product
//
//        //(3) find d (shared side of both right triangles) used Pythagoras
//        double d = Math.sqrt(u.lengthSquared() - tm*tm);
//        if(d >= radius) //if ray's intersection points are on or beyond the sphere's boundary
//            return null;//new LinkedList<Point>(); //if we return null, the test fails...
//
//        /**
//         * Really, if d == radius, there is one intersection point and the ray is tangent...
//         * but in our project, we will not include intersection points with the surface of a shape
//         */
//
//        //(4) find "th" - base of each right triangle
//        double th = Math.sqrt(radius*radius - d*d );
//        double t1 = tm + th;
//        double t2 = tm - th;
//
//        if(t1 <= 0 && t2 <= 0) //if t1 or t2 ==0, then intersection point at p0 of the ray
//            return null;
//
//        //(5) calculate both intersection points
//        List<Point> res = new LinkedList<>();
//        if(t1>0)
//            res.add(_ray.getPoint(t1));
//            //res.add(_ray.getP0().add(_ray.getDirVector().scale(t1)));
//
//        if(t2>0)
//            res.add(_ray.getPoint(t2));
//            //res.add(_ray.getP0().add(_ray.getDirVector().scale(t2)));
//
//        return res;
//    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        /** SEE README FILE FOR ILLUSTRATION! - Diagram 1.1
         * names of variables based on the diagram..
         * note - "u" is stored as a vector, not as a ray...
         * good explanation on this website - https://www.scratchapixel.com/lessons/3d-basic-rendering/minimal-ray-tracer-rendering-simple-shapes/ray-sphere-intersection#:~:text=Intersecting%20a%20ray%20with%20a,simplicity)%20to%20be%20very%20fast.
         */

        //(1) find u [vector u goes from _ray's p0 --> Circle's center]
        if (ray.getP0().equals(center)) {
            //if ray begins at circle's center...
            List<GeoPoint> res = new LinkedList<>();
            res.add(new GeoPoint(this,
                    ray.getP0().add(ray.getDirVector().scale(radius))));
            return res;
        }
        //else:
        Vector u = center.subtract(ray.getP0());

        //(2) find scalar projection (tm) of u onto Ray (to find vertex of right triangle)
        double tm = ray.getDirVector().dotProduct(u); // tm holds the scalar product

        //(3) find d (shared side of both right triangles) used Pythagoras
        double d = Util.alignZero(Math.sqrt(u.lengthSquared() - tm * tm));
        if (d >= radius) //if ray's intersection points are on or beyond the sphere's boundary
            return null;//new LinkedList<Point>(); //if we return null, the test fails...

        /**
         * Really, if d == radius, there is one intersection point and the ray is tangent...
         * but in our project, we will not include intersection points with the surface of a shape
         */

        //(4) find "th" - base of each right triangle
        double th = Util.alignZero(Math.sqrt(radius * radius - d * d));
        double t1 = tm + th;
        double t2 = tm - th;

        if (t1 <= 0 && t2 <= 0) //if t1 or t2 ==0, then intersection point at p0 of the ray
            return null;

        //(5) calculate both intersection points
        List<GeoPoint> res = new LinkedList<>();
        if (t1 > 0)
            res.add(new GeoPoint(this, ray.getPoint(t1)));
        //res.add(_ray.getP0().add(_ray.getDirVector().scale(t1)));

        if (t2 > 0)
            res.add(new GeoPoint(this, ray.getPoint(t2)));
        //res.add(_ray.getP0().add(_ray.getDirVector().scale(t2)));

        return res;
    }
}
