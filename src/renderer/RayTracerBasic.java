package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

import java.util.List;

/**
 * inherits from RayTracerBase...
 */
public class RayTracerBasic extends RayTracerBase{

    public RayTracerBasic(Scene _scene){
        super(_scene);

        //throw new IllegalArgumentException(); //need to work on this...
    }

    /**
     *  traceRay() finds intersections of ray with any geometry in scene, and
     *  @return color of the point closest to the camera
     */
    @Override
    public Color traceRay(Ray _ray) {
        List<Point> intersectingPoints = scene.getGeometriesList().findIntersections(_ray);
        if(intersectingPoints == null) // if no intersections..
            return scene.backgroundColor;
        Point closestPoint = _ray.findClosestPoint(intersectingPoints);
        return calcColor(closestPoint);
    }

    /**
     * depending on lighting,
     * @return appropriate color of point in the 3d space..
     */
    public Color calcColor(Point point){
        //well add code here....
        return scene.backgroundColor;
    }
}
