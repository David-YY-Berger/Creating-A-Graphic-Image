package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

public class Tube implements Geometry{

    Ray axisRay;
    Double radius;

    public Tube(Ray _axisRay, Double _radius) {
        axisRay = _axisRay;
        radius = _radius;
    }

    @Override
    public Vector getNormal(Point pointOnSurface) {
        double distance = axisRay.getDirVector().dotProduct(pointOnSurface.subtract(axisRay.getP0()));
        //"distance" measures dist btw p0 of the axisRAy,
        // and the intersection of the pointOnSurface's ray with the axisRay
        if (distance == 0.0)
        {
            //if (the ray btw pointOnSurface and the tube's axisRay) == a straight angle,
            //then must calculate the normal vector differently -> directly with the p0
            return pointOnSurface.subtract(axisRay.getP0()).normalize();
        }
        else {
            Point intersectPntWAxis = axisRay.getP0().add(axisRay.getDirVector().scale(distance));
            //intersection point with the axis ray, and ray from pointOnSurface
            return pointOnSurface.subtract(intersectPntWAxis).normalize();
        }
    }

    public Double getRadius() {
        return radius;
    }
    public Ray getAxisRay() {
        return axisRay;
    }

    @Override
    public List<Point> findIntsersections(Ray ray) {
        return null;
    }
}
