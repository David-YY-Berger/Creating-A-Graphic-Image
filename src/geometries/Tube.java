package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Tube implements Geometry{

    Ray axisRay;
    Double radius;

    public Tube(Ray _axisRay, Double _radius) {
        axisRay = _axisRay;
        radius = _radius;
    }

    @Override
    public Vector getNormal(Point pointOnSurface) {
        double t = axisRay.getDirVector().dotProduct(pointOnSurface.subtract(axisRay.getP0()));
        if (t == 0)
            throw new IllegalArgumentException();
        Point o = axisRay.getP0().add(axisRay.getDirVector().scale(t));
        return pointOnSurface.subtract(o).normalize();
    }

    public Double getRadius() {
        return radius;
    }
    public Ray getAxisRay() {
        return axisRay;
    }
}
