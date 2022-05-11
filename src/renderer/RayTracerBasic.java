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

    private static final double DELTA = 0.1; // moves the point "outside" of the shape.. see "unshaded()"
    //stopping coniditions for recursion:
    private static final int MAX_CALC_COLOR_LEVEL = 10;     //
    private static final double MIN_CALC_COLOR_K = 0.001;   // if color is no longer significant



    public RayTracerBasic(Scene _scene){
        super(_scene);

    }

    /**
     * @return whether or not given point is shaded..
     * @param l - vector from the light..
     * @param n - normal vector from the light
     * @param nv scalar value btw Camera's vector and normal vector of l....
     */
    private boolean unshaded(GeoPoint gp, Vector l, Vector n, double nv, LightSource lightsource) {

        //check if camera is on the same side of the object as the light source:
        Vector deltaVector = n.scale(nv < 0 ? DELTA : -DELTA);
        // moves the point "outside" of the shape -
        //to ensure that the shape does not "shade" itself
        Point point = gp.point.add(deltaVector);

        Vector vecFromShapeToLight = l.scale(-1); // from point to light source
        //create a "backwards" ray from the shape to the light - to see if there are any other shapes btw this shape and the light source
        Ray lightRay = new Ray(point, vecFromShapeToLight);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
        if (intersections == null)
            return true; // the is no shade
        else {
            double distance = lightsource.getDistance(gp.point);
            for (GeoPoint p : intersections) {
                // check if the intersection is between the light
                if (gp.point.distance(p.point) < distance)
                    return false; // shaded
            }
            return true;
        }
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
    public Color calcColor(GeoPoint geoPoint, Ray ray){
        return scene.ambientLight.getIntensity().add(geoPoint.geometry.getEmission())
                .add(calcLocalEffects(geoPoint, ray));
    }


    /**
     * calculates color based on the differet light sources in the environment...
     * @param gp receives point in our 3d space - it is connected with a geometry and color
     * @param ray ray to trace back to the camera..
     * @return colors
     */
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
            double nl = alignZero(normalVector.dotProduct(lightVector)); //scalar product of light vector and its normal vector

            if (Util.checkSign(nl, nv)) { // if both are positive || both are negative,
                //then add colors... otherwise, color is irrelevant

                if(unshaded(gp, lightVector, normalVector, nv, lightSource))
                {
                    Color intesityOfLightSource = lightSource.getIntensity(gp.point);
                    result_color = result_color.add(intesityOfLightSource.scale(calcDiffusive(material, nl)),
                            intesityOfLightSource.scale(calcSpecular(material, normalVector, lightVector, nl, v)));
                }
            }
        }
        return result_color;
    }

    public double calcDiffusive(Material material, double nl){
        return material.kD.d1 * Math.abs(nl);
    }

    public double calcSpecular(Material material, Vector normalVec, Vector lightVec, double nl, Vector cameraVec){

        /**
         * see ReadMe! diagram 3.2 - Calculating Vectors "r" and "l"
         * equation: ks (-v * r) ^(nshini)
         */

        Vector r = lightVec.subtract(normalVec.scale(((lightVec.dotProduct(normalVec))*2)));
        double numberToExpo = (cameraVec.scale(-1)).dotProduct(r); //<- not efficient!
        if(numberToExpo<0)
            numberToExpo = 0;
        return material.kS.d1 * Math.pow(numberToExpo, material.nShininess);// Math.exp (material.nShininess);
    }


}
