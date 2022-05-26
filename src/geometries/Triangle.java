package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

public class Triangle extends Polygon{

    public Triangle(Point... vertices) {

        super(vertices);
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        // check first if the ray intersects the plane that contains the triangle
        List <GeoPoint> intersectingGeoPoints = plane.findGeoIntersectionsHelper(ray);
        if (intersectingGeoPoints == null)
            return null;

        // check if the point is in the triangle, the way is in diagram 1.3
        Point p0 = ray.getP0();
        Vector v = ray.getDirVector();

        Vector v1 = vertices.get(0).subtract(p0);
        Vector v2 = vertices.get(1).subtract(p0);
        Vector v3 = vertices.get(2).subtract(p0);

        // calculate the normals of the sides of the pyramid
        Vector n1 = v1.crossProduct(v2);
        Vector n2 = v2.crossProduct(v3);
        Vector n3 = v3.crossProduct(v1);

        // calculate the direction of the normals
        double d1 = v.dotProduct(n1);
        double d2 = v.dotProduct(n2);
        double d3 = v.dotProduct(n3);

        if (d1 == 0 || d2 == 0 || d3 == 0)
            return null;

        if(!((d1 > 0 && d2 > 0 && d3 > 0) || (d1 < 0 && d2 < 0 && d3 < 0)))
            return null;

        //because the geoPoints were created with the Plane's function, we must change their geometry field to point to this triangle:
        for (GeoPoint gP: intersectingGeoPoints
             ) {
            gP.geometry = this;
        }

        return intersectingGeoPoints;
    }

    //uses Plane's functions..  .

}
