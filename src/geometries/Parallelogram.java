package geometries;

import primitives.*;

import java.util.List;

/**
 * Parallelgram used for Boundary Value Hierarchy - see "Bounding-Box"
 * automatically set as transparent
 */
public class Parallelogram extends Geometry{

    private Color colorOfParrellogram = new Color(0, 50, 0);
    private double transparency = .9;


    public Triangle tri1;
    public Triangle tri2;
    public Point[] vertices = new Point[4];

    public Parallelogram(Point p1, Point p2, Point p3){

        Point p4 = p1.add(p2.subtract(p1)).add(p3.subtract(p1));
        tri1 = new Triangle(p1, p2, p3);
        tri2 = new Triangle(p2, p3, p4);

        tri1.setEmission(colorOfParrellogram).setMaterial(new Material().setKt(transparency));
        tri2.setEmission(colorOfParrellogram).setMaterial(new Material().setKt(transparency));

        vertices[0] = p1;
        vertices[1] = p2;
        vertices[2] = p3;
        vertices[3] = p4;
    }


    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        List<GeoPoint> res = tri1.findGeoIntersectionsHelper(ray);
        if(res == null)
            return tri2.findGeoIntersectionsHelper(ray);
        else
            if(res.isEmpty())
            {
                int d = 5;
            }
            return res;
    }

    public boolean isPointOnFace(Point p){
        //based on https://stackoverflow.com/questions/8812073/ray-and-square-rectangle-intersection-in-3d
        Point p0 = vertices[0];
        Vector side1 = vertices[1].subtract(p0);
        Vector side2 = vertices[2].subtract(p0);
        Vector p0_to_p = p.subtract(p0);
        if(0 > (p0_to_p.projection(side1)).length() ||
            (p0_to_p.projection(side1)).length() > side1.length() ||
                0 > (p0_to_p.projection(side2)).length() ||
                (p0_to_p.projection(side2)).length() > side2.length()
        )
            return false;
        else
            return true;
    }

    @Override
    public Vector getNormal(Point pointOnSurface) {
        return tri1.getNormal(pointOnSurface); //same normal ray, regardless of where the point falls..
    }
}
