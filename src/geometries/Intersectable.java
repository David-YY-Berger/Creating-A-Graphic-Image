package geometries;
import primitives.*;
import java.util.List;

/**
 * Interface which helps with RayTracing - finds intersection points..
 */
public interface Intersectable {
    /**
     * The function returns the intersection points with the geometry
     * @param ray
     * @return list of point/s that intersects the geometry
     */
    public List<Point> findIntersections(Ray ray);
}
