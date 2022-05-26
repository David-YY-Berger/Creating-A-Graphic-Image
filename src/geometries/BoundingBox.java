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

    private static final int[] max_x_index = {0, 1, 2, 3};

    //for 6 sided box..
    public Parallelogram[] faces = new Parallelogram[6];
    public Point[] vertices = new Point[8];

    //make static int[] of each index..
    /* explanation of vertices:
        max X = 0, 1, 2, 3
        min X = 4, 5, 6, 7
        max y = 0, 1, 4, 5
        min y = 2, 3, 6, 7
        max z = 0, 2, 6, 4
        min z = 1, 3, 5, 7
     */


    /**
     * CTOR 1 of 3
     */
    public BoundingBox(Sphere sphere) {

        //vertices of the box:
        //Point[] vertices = new Point[8];
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
        List<Double> max_X = new LinkedList<>();
        List<Double> max_Y = new LinkedList<>();
        List<Double> max_Z = new LinkedList<>();
        List<Double> min_X = new LinkedList<>();
        List<Double> min_Y = new LinkedList<>();
        List<Double> min_Z = new LinkedList<>();


        //for all min/max values, get appropraite value, and build 8 vertices based on that...

        /* explanation of vertices:
        max X = 0, 1, 2, 3
        min X = 4, 5, 6, 7
        max y = 0, 1, 4, 5
        min y = 2, 3, 6, 7
        max z = 0, 2, 6, 4
        min z = 1, 3, 5, 7
     */for (BoundingBox box: boxes
             ) {
            for (int index: max_x_index
                 ) {
                max_X.add(box.vertices[index].getX());
            }
        }
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
