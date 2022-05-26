package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * class Box - used for Boundary Value Hierarchy - a feature added to improve runtime efficiency
 * The idea:
 * each geometry is put inside a "Box" - and rays only trace color if they come in contact with a box..
 * so that the iteration over the geometries collection in a scene can be much quicker
 */
public class Box extends Geometry{

    //vertices of the box:
    public Point[] vertices = new Point[8]; //for 6 sided box..

    public Box(Sphere sphere) {
        vertices[0] = sphere.center.add(new Vector(sphere.radius, sphere.radius, sphere.radius));
        vertices[1] = sphere.center.add(new Vector(sphere.radius, sphere.radius, -sphere.radius));
        vertices[2] = sphere.center.add(new Vector(sphere.radius, -sphere.radius, sphere.radius));
        vertices[3] = sphere.center.add(new Vector(sphere.radius, -sphere.radius, -sphere.radius));
        vertices[4] = sphere.center.add(new Vector(-sphere.radius, sphere.radius, sphere.radius));
        vertices[5] = sphere.center.add(new Vector(-sphere.radius, sphere.radius, -sphere.radius));
        vertices[6] = sphere.center.add(new Vector(-sphere.radius, -sphere.radius, sphere.radius));
        vertices[7] = sphere.center.add(new Vector(-sphere.radius, -sphere.radius, -sphere.radius));
    }



    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray _ray) {

        //must implement
        return null;
    }

    @Override
    public Vector getNormal(Point pointOnSurface) {
        return null;
    }
}
