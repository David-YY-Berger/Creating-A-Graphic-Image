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
    private static final double INITIAL_K = 1.0;
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
                if (gp.point.distance(p.point) < distance && p.geometry.getMaterial().kT.d1 == 0)
                    return false; // shaded
            }
            return true;
        }
    }

    /**
     * in order to calculate PARTIAL shading, this function returns a double - btw 0 and 1,
     * representing how shaded this object is
     * @param l - vector from the light..
     * @param n - normal vector from the light
     * @param nv scalar value btw Camera's vector and normal vector of l....
     * @return
     */
    private double transparency(GeoPoint geoPoint, LightSource lightsource, Vector l, Vector n, double nv){
        //check if camera is on the same side of the object as the light source:
        Vector deltaVector = n.scale(nv < 0 ? DELTA : -DELTA);
        // moves the point "outside" of the shape -
        //to ensure that the shape does not "shade" itself
        Point point = geoPoint.point.add(deltaVector);

        Vector vecFromShapeToLight = l.scale(-1); // from point to light source
        //create a "backwards" ray from the shape to the light - to see if there are any other shapes btw this shape and the light source
        Ray lightRay = new Ray(point, vecFromShapeToLight);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
        if (intersections == null)
            return 0; // the is no shade.. return the full color of the shape..
        else {
            double ktr = 1; //<- how much transparency to return... (?)

//             for each intersection which is closer to the point than the light source,
//             multiply ktr by 𝒌𝑻 of its geometry:
            double distance = lightsource.getDistance(geoPoint.point);
            for (GeoPoint p : intersections) {
                // check if the intersection is between the light
                if (geoPoint.point.distance(p.point) < distance)
                    ktr = ktr * p.geometry.getMaterial().kT.d1;
            }
            return ktr;
        }
    }



    /**
     *  traceRay() finds intersections of ray with any geometry in scene, and
     *  @return color of the point closest to the camera
     */
    @Override
    public Color traceRay(Ray _ray) {

        GeoPoint closestPoint = findClosestIntersection(_ray);

        if(closestPoint == null)
            return scene.backgroundColor;
//        List<GeoPoint> intersectingGeoPoints = scene.geometries.findGeoIntersections(_ray);
//        if(intersectingGeoPoints == null) // if no intersections..
//            return scene.backgroundColor;
//        GeoPoint closestPoint = _ray.findClosestGeoPoint(intersectingGeoPoints);
        return calcColor(closestPoint, _ray);
    }

    /**
     * depending on lighting,
     * @return appropriate color of point in the 3d space..
     */
    public Color calcColor(GeoPoint geoPoint, Ray ray){

        return calcColor(geoPoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(scene.ambientLight.getIntensity());

//        Color color = scene.ambientLight.getIntensity().add(geoPoint.geometry.getEmission())
//                .add(calcLocalEffects(geoPoint, ray));
//        color = color.add(calcGlobalEffects(geoPoint, ray));
//
//        return color;
    }

    private Color calcColor(GeoPoint intersection, Ray ray, int level, double k) {

        Color color = intersection.geometry.getEmission()
                .add(calcLocalEffects(intersection, ray));

        if(level == 1)
            return color;
        else
            return color.add(calcGlobalEffects(intersection, ray, level, k));

//        return (1 == level) ? color : color = color.add(calcGlobalEffects(intersection, ray,
//                level, k));
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

    /**
     * calculates color based on the differet light sources in the environment...
     * @param gp receives point in our 3d space - it is connected with a geometry and color
     * @param ray ray to trace back to the camera..
     * @return colors
     */
    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, double k) {

        Color color = Color.BLACK;
        double kr = gp.geometry.getMaterial().kR.d1;
        double kkr = k*kr;
        if(kkr>MIN_CALC_COLOR_K) {
            Ray reflectedRay = constructReflectedRay(gp.geometry.getNormal(gp.point), gp.point, ray);
            GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);

            if (reflectedPoint != null)
                color = color.add(calcColor(reflectedPoint, reflectedRay, level - 1, kkr)
                    .scale(kr));
        }

        double kt = gp.geometry.getMaterial().kT.d1;
        double kkt = k*kt;

        if(kkt>MIN_CALC_COLOR_K) {
            Ray refractedRay = constructRefractedRay(gp.point, ray);
            GeoPoint refractedPoint = findClosestIntersection(refractedRay);

            if (refractedPoint != null)
                color = color.add(calcColor(refractedPoint, refractedRay, level - 1, kkt)
                    .scale(kt));
        }

        return color;
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

    private double calcDiffusive(Material material, double nl) {

        return material.kD.d1*Math.abs(nl);
    }

    /**
     *
     * @param normal
     * @param p
     * @param cameraRay
     * @return
     */
    public Ray constructReflectedRay(Vector normal, Point p, Ray cameraRay){

        Vector deltaVector = cameraRay.getDirVector().scale(-DELTA);
        Point point = p.add(deltaVector);
        return new Ray(point,
                cameraRay.getDirVector()
                        .add(normal.scale(cameraRay.getDirVector().dotProduct(normal)*(-2))));
    }

    /**
     *
     * @param p
     * @param cameraRay
     * @return
     */
    public Ray constructRefractedRay(Point p, Ray cameraRay){

        Vector deltaVector = cameraRay.getDirVector().scale(DELTA);
        // moves the point "outside" of the shape -
        //to ensure that the shape does not "shade" itself
        Point point = p.add(deltaVector);
        return new Ray(point, cameraRay.getDirVector());
    }

    private GeoPoint findClosestIntersection(Ray ray){

            List<GeoPoint> geoIntersections = scene.geometries.findGeoIntersections(ray);
            if (geoIntersections == null)
                return null;
            else
                return ray.findClosestGeoPoint(geoIntersections);

    }


}
