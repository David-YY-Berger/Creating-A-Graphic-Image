package geometries;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.isZero;

public class Cylinder extends  Tube{

    Double height;

    public Cylinder(Ray _axisRay, Double _radius, Double _height){
        super(_axisRay, _radius);
        height = _height;
    }
    @Override
    public Vector getNormal(Point pointOnSurface) {

        //When point is center of 1st base
        if (pointOnSurface.equals(axisRay.getP0())) {

            return axisRay.getDirVector().scale(-1);
        }
        //When point is center of top's base
        if (pointOnSurface.equals(axisRay.getPoint(height))){

            return axisRay.getDirVector();
        }

        //when point is on the base
        if (pointOnSurface.subtract(axisRay.getP0()).length() < radius){

            return axisRay.getDirVector().scale(-1);
        }
        //when point is on the top
        else if (pointOnSurface.subtract(axisRay.getP0().add(axisRay.getDirVector().scale(height))).length() <radius){

            return axisRay.getDirVector();
        }

        //when point on the surface, same normal as tube
        return super.getNormal(pointOnSurface);
    }

    public Double getHeight() {
        return height;
    }
}
