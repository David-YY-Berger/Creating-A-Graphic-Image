package renderer;

import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;

import static primitives.Util.alignZero;

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

    private Color calcLocalEffects(GeoPoint gp, Ray ray) {
        Color color = gp.geometry.getEmission();
        Vector v = ray.getDirVector ();
        Vector normalVector = gp.geometry.getNormal(gp.point);
        double nv = alignZero(normalVector.dotProduct(v));
        if (nv == 0) return color; //if directly on the normal vector...

        Material material = gp.geometry.getMaterial();
        for (LightSource lightSource : scene.lights) {
            Vector lightVector = lightSource.getL(gp.point);
            double nl = alignZero(normalVector.dotProduct(lightVector));

            if (nl * nv > 0) { // if both are positive || both are negative,
                //then add colors... otherwise, color is irrelevant
                Color intesityOfLightSource = lightSource.getIntensity(gp.point);
                color = color.add(intesityOfLightSource.scale(calcDiffusive(material, nl)),
                        intesityOfLightSource.scale(calcSpecular(material, normalVector, lightVector, nl, v)));
            }
        }
        return color;
    }

    //What to do in these functions??

    public double calcDiffusive(Material material, double nl){

        return 0d;
    }
    public double calcSpecular(Material material, Vector normalVec, Vector lightVec, double nl, Vector origDirection){
        return 0d;
    }


}
