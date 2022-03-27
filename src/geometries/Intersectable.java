package geometries;
import primitives.*;
import java.util.List;

/**
 * Interface helps w RayTracing - every obj can return intersection points w a ray
 */
public interface Intersectable {
    /**
     * The function returns the intersection points with the geometry
     * @param ray
     * @return list of point/s that intersects the geometry
     */
    public List<Point> findIntersections(Ray ray);
}
