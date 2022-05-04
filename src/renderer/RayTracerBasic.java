package renderer;

import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;

import static java.lang.Math.abs;
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
        return calcColor(closestPoint, _ray);
    }

    /**
     * depending on lighting,
     * @return appropriate color of point in the 3d space..
     */
//    public Color calcColor(GeoPoint geoPoint){
//         return scene.ambientLight.getIntensity().add(geoPoint.geometry.getEmission());
//    }
    public Color calcColor(GeoPoint geoPoint, Ray ray){
        return scene.ambientLight.getIntensity().add(geoPoint.geometry.getEmission())
                .add(calcLocalEffects(geoPoint, ray));
    }



    private Color calcLocalEffects(GeoPoint gp, Ray ray) {
        Color result_color = gp.geometry.getEmission();     //final result
        Vector v = ray.getDirVector ();
        Vector normalVector = gp.geometry.getNormal(gp.point);
        double nv = alignZero(normalVector.dotProduct(v)); // <- use scalar product to check if vector (from camera)
                                                        // and light vector are in the same direction
        if (nv == 0) return result_color; //if directly on the normal vector...

        Material material = gp.geometry.getMaterial();
        //add effect of every light source of color of the geoPoint
        for (LightSource lightSource : scene.lights) {
            Vector lightVector = lightSource.getL(gp.point);
            double nl = alignZero(normalVector.dotProduct(lightVector));

            if (nl * nv > 0) { // if both are positive || both are negative,
                //then add colors... otherwise, color is irrelevant
                Color intesityOfLightSource = lightSource.getIntensity(gp.point);
                result_color = result_color.add(intesityOfLightSource.scale(calcDiffusive(material, nl)),
                        intesityOfLightSource.scale(calcSpecular(material, normalVector, lightVector, nl, v)));

                //see diagram!! phong..

            }
        }
        return result_color;
    }

    public double calcDiffusive(Material material, double nl){
        return material.kD.d1 * Math.abs(nl);
    }

    public double calcSpecular(Material material, Vector normalVec, Vector lightVec, double nl, Vector cameraVec){

        //see diagram!!! for r

        //big equation: ks (-v * r) ^(nshini)
        //r = l - 2*(l*n)*n

        Vector r = lightVec.subtract(normalVec.scale(((lightVec.dotProduct(normalVec))*2)));
        double numberToExpo = (cameraVec.scale(-1)).dotProduct(r); //<- not efficient!
        if(numberToExpo<0)
            numberToExpo = 0;
        return material.kS.d1 * Math.pow(numberToExpo, material.nShininess);// Math.exp (material.nShininess);
    }


}
