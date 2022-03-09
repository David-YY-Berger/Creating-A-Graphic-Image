package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Cylinder extends  Tube{

    Double height;

    @Override
    public Vector getNormal(Point pointOnSurface) {
        return super.getNormal(pointOnSurface);
    }

    public Double getHeight() {
        return height;
    }
}
