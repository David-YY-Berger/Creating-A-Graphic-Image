package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Tube implements Geometry{

    Ray axisRay;
    Double radius;

    @Override
    public Vector getNormal(Point pointOnSurface) {
        return null;
    }

    public Double getRadius() {
        return radius;
    }
    public Ray getAxisRay() {
        return axisRay;
    }
}
