package geometries;
import primitives.*;
import java.util.List;
import java.util.Objects;

/**
 * Interface helps w RayTracing - every obj can return intersection points w a ray
 */
public abstract class Intersectable {
    /**
     * The function returns the intersection points with the geometry
     * @param ray
     * @return list of point/s that intersects the geometry
     */
    abstract public List<Point> findIntersections(Ray ray);

    /**
     * PDS  - Passive data structure;
     * enables a way to assign every point to a color (of its specific geometry)
     */
    public static class  GeoPoint {
        public Geometry geometry;
        public Point point;

        public GeoPoint(Geometry _geometry, Point _point) {
            this.geometry = geometry;
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
