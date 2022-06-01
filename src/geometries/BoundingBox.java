package geometries;

import primitives.Color;
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

    //for 6 sided box...
    public Parallelogram[] faces = new Parallelogram[6];
    public Geometries boxShapeList = null; //holds shapes inside each box
    private Point[] vertices = new Point[8];
    private double EPSILON = .1; //move sides of box out to prevent a Zero Vector
    //indexes in vertices which hold max/min values for x, y, z in every box
    private static final int max_point_Index = 0;
    private static final int min_point_Index = 7;

    /* explanation of vertices:
        max X = 0, 1, 2, 3
        min X = 4, 5, 6, 7
        max y = 0, 1, 4, 5
        min y = 2, 3, 6, 7
        max z = 0, 2, 6, 4
        min z = 1, 3, 5, 7
     */


    /**
     * helper function -
     * constructs the caller of this function, And returns the object
     */
    public BoundingBox constructThisBox(double max_x, double min_x, double max_y,
                                        double min_y, double max_z, double min_z,
                                        Geometries shapeList)
    {
        //to prevent Zero Vector
        max_x = max_x + EPSILON;
        min_x = min_x - EPSILON;
        max_y = max_y + EPSILON;
        min_y = min_y - EPSILON;
        max_z = max_z + EPSILON;
        min_z = min_z - EPSILON;

        vertices[0] = new Point(max_x, max_y, max_z);
        vertices[1] = new Point(max_x, max_y, min_z);
        vertices[2] = new Point(max_x, min_y, max_z);
        vertices[3] = new Point(max_x, min_y, min_z);

        vertices[4] = new Point(min_x, max_y, max_z);
        vertices[5] = new Point(min_x, max_y, min_z);
        vertices[6] = new Point(min_x, min_y, max_z);
        vertices[7] = new Point(min_x, min_y, min_z);

        faces[0] = new Parallelogram(vertices[0], vertices[1], vertices[2]);
        faces[1] = new Parallelogram(vertices[0], vertices[1], vertices[4]);
        faces[2] = new Parallelogram(vertices[0], vertices[2], vertices[4]);
        faces[3] = new Parallelogram(vertices[7], vertices[3], vertices[5]);
        faces[4] = new Parallelogram(vertices[7], vertices[3], vertices[6]);
        faces[5] = new Parallelogram(vertices[7], vertices[5], vertices[6]);

        boxShapeList = shapeList;
        return this;
    }

    /**
     * CTOR - builds box around geometries given in shapeList
     * if a shape in shapeList is not a box, it puts a box around it..
     */
    public BoundingBox(Geometries shapeList) {

        //to find general boundaries for box - for all shapes
        double max_x_val = Double.NEGATIVE_INFINITY;
        double min_x_val = Double.POSITIVE_INFINITY;
        double max_y_val = Double.NEGATIVE_INFINITY;
        double min_y_val = Double.POSITIVE_INFINITY;
        double max_z_val = Double.NEGATIVE_INFINITY;
        double min_z_val = Double.POSITIVE_INFINITY;

        double temp; // to compare
        //(1) iterate thru shapes, extract farthest values..
        for (Intersectable shape: shapeList.intersectableList
             ) {
            if(shape instanceof Sphere)
            {
                Sphere sphere = (Sphere)shape;
                temp = sphere.center.getX() + sphere.radius;
                if(temp > max_x_val) max_x_val = temp;
                temp = sphere.center.getX() - sphere.radius;
                if(temp < min_x_val) min_x_val = temp;
                temp = sphere.center.getY() + sphere.radius;
                if(temp > max_y_val) max_y_val = temp;
                temp = sphere.center.getY() - sphere.radius;
                if(temp < min_y_val) min_y_val = temp;
                temp = sphere.center.getZ() + sphere.radius;
                if(temp > max_z_val) max_z_val = temp;
                temp = sphere.center.getZ() - sphere.radius;
                if(temp < min_z_val) min_z_val = temp;
                continue;
            }
            if(shape instanceof Triangle)
            {
                //iterate thru points in trianlge, and found the max and min values in all 6 directions:
                for (Point point: ((Triangle)shape).vertices)
                {
                    if(point.xyz.d1 > max_x_val) max_x_val = point.xyz.d1;
                    if(point.xyz.d1 < min_x_val) min_x_val = point.xyz.d1;

                    if(point.xyz.d2 > max_y_val) max_y_val = point.xyz.d2;
                    if(point.xyz.d2 < min_y_val) min_y_val = point.xyz.d2;

                    if(point.xyz.d3 > max_z_val) max_z_val = point.xyz.d3;
                    if(point.xyz.d3 < min_z_val) min_z_val = point.xyz.d3;
                }
                continue;
            }
            if(shape instanceof BoundingBox)
            {
                BoundingBox box = (BoundingBox) shape;
                if(box.vertices[max_point_Index].xyz.d1 > max_x_val) max_x_val = box.vertices[max_point_Index].xyz.d1;
                if(box.vertices[min_point_Index].xyz.d1 < min_x_val) min_x_val = box.vertices[min_point_Index].xyz.d1;

                if(box.vertices[max_point_Index].xyz.d2 > max_y_val) max_y_val = box.vertices[max_point_Index].xyz.d2;
                if(box.vertices[min_point_Index].xyz.d2 < min_y_val) min_y_val = box.vertices[min_point_Index].xyz.d2;

                if(box.vertices[max_point_Index].xyz.d3 > max_z_val) max_z_val = box.vertices[max_point_Index].xyz.d3;
                if(box.vertices[min_point_Index].xyz.d3 < min_z_val) min_z_val = box.vertices[min_point_Index].xyz.d3;
                continue;
            }
        }
        constructThisBox(max_x_val, min_x_val, max_y_val, min_y_val, max_z_val, min_z_val,
                shapeList);
    }

    public void setColorAndTransp(Color color, double transp){
        for (Parallelogram face: faces
             ) {
            face.setColorAndTransparency(color, transp);
        }
    }



    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray _ray) {

        List<GeoPoint> res = null;
        List<GeoPoint> temp = null;
        //(1) iterate thru faces, looking for intersections... there could be 0,1, or 2
        for (Parallelogram face_i: faces
             ) {
            temp = face_i.findGeoIntersectionsHelper(_ray);
            if(temp == null)  //if there are no intersections...
                continue;
            else {
                //add the intersection:
                if(res == null)
                    res = new LinkedList<>();
                res.add(temp.get(0));
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
