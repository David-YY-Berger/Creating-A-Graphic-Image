package geometries;
import primitives.*;
import java.util.List;

/**
 * Interface which helps with RayTracing - finds intersection points..
 */
public interface Intersectable {

    public List<Point> findIntsersections(Ray ray);
}
