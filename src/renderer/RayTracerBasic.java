package renderer;

import geometries.Intersectable.GeoPoint;
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
        List<GeoPoint> intersectingGeoPoints = scene.geometries.findGeoIntersections(_ray);
        if(intersectingGeoPoints == null) // if no intersections..
            return scene.backgroundColor;
        GeoPoint closestPoint = _ray.findClosestGeoPoint(intersectingGeoPoints);
        return calcColor(closestPoint);
    }

    /**
     * depending on lighting,
     * @return appropriate color of point in the 3d space..
     */
    public Color calcColor(GeoPoint geoPoint){
        //well add code here....
        //return geoPoint.geometry.getEmission();

        return scene.ambientLight.getIntensity().add(geoPoint.geometry.getEmission());
    }

}
