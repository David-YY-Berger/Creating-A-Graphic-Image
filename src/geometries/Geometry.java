package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Interface
 */
public interface Geometry extends Intersectable {
    Vector getNormal(Point pointOnSurface);

}
