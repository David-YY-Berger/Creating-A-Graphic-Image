package geometries;
import primitives.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Interface helps w RayTracing - every obj can return intersection points w a ray
 */
public abstract class Intersectable {
    /**
     * The function returns the intersection points with the geometry
     * @param _ray
     * @return list of point/s that intersects the geometry
     */
    //abstract public List<Point> findIntersections(Ray ray);
    public List<Point> findIntersections(Ray _ray) {

        var geoList = findGeoIntersections(_ray);
        List<Point> res = null;
        if (geoList != null) {
            res = new LinkedList<>();
            for (GeoPoint gP : geoList
            ) {
                res.add(gP.point);
            }
//           return geoList == null ? null
//                    : geoList.stream().map(gp -> gp.point).toList(); //<doesnt work; i guess i dont have the right java?
        }
        return res; //returns null if no points..
    }
    public List<GeoPoint> findGeoIntersections(Ray ray){
        return findGeoIntersectionsHelper(ray);
    }
    abstract protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray);


    /**
     * PDS  - Passive data structure;
     * enables a way to assign every point to a color (of its specific geometry)
     */
    public static class  GeoPoint {
        public Geometry geometry;
        public Point point;

        public GeoPoint(Geometry _geometry, Point _point) {
            this.geometry = _geometry;
            this.point = _point;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            //check by reference!!!
            return this.geometry == ((GeoPoint) o).geometry
                    && this.point == ((GeoPoint) o).point;
            //(if we wanted to check by value...
            //return Objects.equals(geometry, geoPoint.geometry) && Objects.equals(point, geoPoint.point);
        }

        @Override
        public String toString() {
            return "GeoPoint{" +
                    "geometry=" + geometry +
                    ", point=" + point +
                    '}';
        }
    }

}
