package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

/**
 * class Box - used for Boundary Value Hierarchy - a feature added to improve runtime efficiency
 * The idea:
 * each geometry is put inside a "Box" - and rays only trace color if they come in contact with a box..
 * so that the iteration over the geometries collection in a scene can be much quicker
 */
public class BoundingBox extends Geometry{

    //for 6 sided box..
    public Parallelogram[] faces = new Parallelogram[6];

    /**
     * CTOR 1 of 3
     */
    public BoundingBox(Sphere sphere) {

        //vertices of the box:
        Point[] vertices = new Point[8];
        vertices[0] = sphere.center.add(new Vector(sphere.radius, sphere.radius, sphere.radius));
        vertices[1] = sphere.center.add(new Vector(sphere.radius, sphere.radius, -sphere.radius));
        vertices[2] = sphere.center.add(new Vector(sphere.radius, -sphere.radius, sphere.radius));
        vertices[3] = sphere.center.add(new Vector(sphere.radius, -sphere.radius, -sphere.radius));

        vertices[4] = sphere.center.add(new Vector(-sphere.radius, sphere.radius, sphere.radius));
        vertices[5] = sphere.center.add(new Vector(-sphere.radius, sphere.radius, -sphere.radius));
        vertices[6] = sphere.center.add(new Vector(-sphere.radius, -sphere.radius, sphere.radius));
        vertices[7] = sphere.center.add(new Vector(-sphere.radius, -sphere.radius, -sphere.radius));

        faces[0] = new Parallelogram(vertices[0], vertices[1], vertices[2]);
        faces[1] = new Parallelogram(vertices[0], vertices[1], vertices[4]);
        faces[2] = new Parallelogram(vertices[0], vertices[2], vertices[4]);
        faces[3] = new Parallelogram(vertices[7], vertices[3], vertices[5]);
        faces[4] = new Parallelogram(vertices[7], vertices[3], vertices[6]);
        faces[5] = new Parallelogram(vertices[7], vertices[5], vertices[6]);

    }

    /**
     * CTOR 2 of 3
     */
    public BoundingBox(Triangle triangle){

    }
    /**
     * CTOR 3 of 3
     */
    public BoundingBox(BoundingBox ... boxes)
    {

    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray _ray) {

        List<GeoPoint> res = new LinkedList<>();
        List<GeoPoint> pointList = null;
        //(1) iterate thru faces, looking for intersections... there could be 0,1, or 2
        for (Parallelogram face_i: faces
             ) {
            pointList = face_i.findGeoIntersectionsHelper(_ray);
            if(pointList == null)// || pointList.isEmpty())
                continue;
            else {
                if(pointList.isEmpty())
                {
                    int g = 0;
                }
                res.add(pointList.get(0));
            }
        }

     return res;
    }

    @Override
    public Vector getNormal(Point pointOnSurface) {

        for (Parallelogram face:faces
             ) {
            if(face.isPointOnFace(pointOnSurface))
                return face.getNormal(pointOnSurface);
        }
        return null;
    }
}
