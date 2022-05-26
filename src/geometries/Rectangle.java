//package geometries;
//
//import primitives.Point;
//import primitives.Ray;
//import primitives.Util;
//import primitives.Vector;
//
//import java.util.List;
//
///**
// * used by Box, for Boundary Value Hierarchy - see javadox there..
// * @p0 point at corner of rectangle
// * @side1 side 1 of rectangle
// * @side2 side 2 of rectangle
// * @normalVec normal vector to rectangle
// */
//public class Rectangle extends Geometry{
//
//    public Point p0;
//    public Vector side1;
//    public Vector side2;
//    public Vector normalVec;
//
//    /*
//    theory based on: https://stackoverflow.com/questions/8812073/ray-and-square-rectangle-intersection-in-3d
//     */
//    public Rectangle(Point p0, Vector side1, Vector side2)
//    {
//        this.p0 = p0;
//        this.side1 = side1;
//        this.side2 = side2;
//        this.normalVec = side1.crossProduct(side2);
//    }
//    public Rectangle(Point p1, Point p2, Point p3){
//        this(p1, p2.subtract(p1), p3.subtract(p1));
//    }
//
//    @Override
//    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
//
//        //(1) check that Ray is not parrallel to rectangle:
//        if (Util.isZero(ray.getDirVector().dotProduct(normalVec)))
//            return null;
//
//        //intersectionPoint = ray.p0 + x * ray.getDirection()
//        //(2) find vector from rec.p0 tp
//
//        //a = ((P0 - R0).N) / (D.N).
//        double a = (p0.subtract(ray.getP0()).dotProduct(normalVec))/(ray.getDirVector().dotProduct(normalVec));
//        Vector fromRectP0TointersectPt =
//
//        return null;
//    }
//
//    @Override
//    public Vector getNormal(Point pointOnSurface) {
//        return normalVec;
//    }
//}
