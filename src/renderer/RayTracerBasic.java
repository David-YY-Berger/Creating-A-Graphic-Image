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
     * in order to calculate PARTIAL shading, this function returns a double - btw 0 and 1,
     * representing how shaded this object is
     * @param l - vector from the light..
     * @param normalVector - normal vector from the light
     //* @param nv scalar value btw Camera's vector and normal vector of l....
     * @return
     */
    private double transparency(GeoPoint geoPoint, LightSource lightsource, Vector l, Vector normalVector){


        Vector lightDirection = l.scale(-1);

        Ray lightRay = new Ray(geoPoint.point, lightDirection, normalVector);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
        if (intersections == null)
            return 1; // there are no other points btw this shape and the light source... ie there is full light..
        else {
            double ktr = 1; //<- assume fully transparent

//             for each intersection which is closer to the point than the light source,
//             multiply ktr by 𝒌𝑻 of its geometry -> shade more of the point (acc to transparency of each point)
            double distance = lightsource.getDistance(geoPoint.point);
            for (GeoPoint p1 : intersections) {
                // check if the intersection is between the light
                if (geoPoint.point.distance(p1.point) < distance)
                    ktr = ktr * p1.geometry.getMaterial().kT.d1;
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

        return calcColor(closestPoint, _ray);
        /*
        for help, see: https://stackoverflow.com/questions/32077952/ray-tracing-glossy-reflection-sampling-ray-direction

        Collection = getListRandomRays(_ray, gp,  gp.Material.getGlossyParam()); //
                                              // funciont contains Constant = how many rays to make (number of samples)
        Color sumColors = 0;
        int n = collection.Size;// numColors
        for i==0; i<n.. //foreach i-ray in Collection
                sumColors += calcColor(closestPoint, i);

        return sumColors/n;
         */

    }

    /**
     * depending on lighting,
     * @return appropriate color of point in the 3d space..
     */
    public Color calcColor(GeoPoint geoPoint, Ray ray){

        return calcColor(geoPoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(scene.ambientLight.getIntensity());
    }

    /**
     * @param intersection gp on surface of shape
     * @param ray origin ray
     * @param level for recursion -
     * @param k for recursion - initialized, and gets smaller with distance
     * @return
     */
    private Color calcColor(GeoPoint intersection, Ray ray, int level, double k) {

        Color color = intersection.geometry.getEmission()
                .add(calcLocalEffects(intersection, ray, k));

        if(level == 1)
            return color;
        else
            return color.add(calcGlobalEffects(intersection, ray, level, k));
    }

    /**
     * calculates color based on the differet light sources (diffusive, specular)in the environment...
     * @param gp receives point in our 3d space - it is connected with a geometry and color
     * @param ray ray to trace back to the camera..
     * @param recursiveConstant - coefficient for transparency (if recursiveConstant == 0: opaque)
     * @return colors
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray, double recursiveConstant) {

        Color result_color = Color.BLACK;     //final result
        Vector cameraVec = ray.getDirVector ();
        Vector normalVector = gp.geometry.getNormal(gp.point);
        double nv = alignZero(normalVector.dotProduct(cameraVec)); // <- use scalar product to check if vector (from camera)
                                                        // and light vector are in the same direction
        if (nv == 0) return result_color; //if directly on the normal vector...
        Material material = gp.geometry.getMaterial();

        //add effect of every light source of color of the geoPoint
        for (LightSource lightSource : scene.lights) {

            Vector lightVector = lightSource.getL(gp.point);
            double nl = alignZero(normalVector.dotProduct(lightVector)); //scalar product of light vector and its normal vector

            if (Util.checkSign(nl, nv)) { // if both are positive || both are negative,
                //then add colors... otherwise, color is irrelevant
                double ktr = transparency(gp, lightSource, lightVector, normalVector);
                //(how much color is "lost" by transparency
                if(ktr * recursiveConstant > MIN_CALC_COLOR_K) //checks transparency of objects btw lightsource and this shape..
                {
                    Color intesityOfLightSource = lightSource.getIntensity(gp.point).scale(ktr); //if ktr is very small,
                                                                                //color will be must less bright
                    result_color = result_color.add(intesityOfLightSource.scale(calcDiffusive(material, nl)),
                            intesityOfLightSource.scale(calcSpecular(material, normalVector, lightVector, nl, cameraVec)));
                }
            }
        }
        return result_color;
    }

    /**
     * calculates color based on the differet light sources in the environment...
     * @param gp receives point in our 3d space - it is connected with a geometry and color
     * @param rayFromCamera ray to trace back to the camera..
     * @return colors
     */
    private Color calcGlobalEffects(GeoPoint gp, Ray rayFromCamera, int level, double k) {

        Color color = Color.BLACK;
        double kr = gp.geometry.getMaterial().kR.d1;
        double kkr = k*kr;

        //(1) ADD COLOR FROM REFLECTION
        if(kkr>MIN_CALC_COLOR_K) {
            //check if material is blurry:
            if(gp.geometry.getMaterial().kBlurry != Double3.ZERO) //if blurry:
            {
                Color resColor = Color.BLACK;
                List<Ray> randomRays = constructReflectedRay(gp.geometry.getNormal(gp.point), gp.point, rayFromCamera)
                        .getRandomRays(gp.point, gp.geometry.getMaterial().kBlurry.d1);
                for (Ray ray_i: randomRays) {

                    GeoPoint reflectedPoint = findClosestIntersection(ray_i);
                    if(reflectedPoint != null)
                        resColor = resColor.add(calcColor(reflectedPoint, ray_i, level - 1, kkr)
                                .scale(kr));
                }
                int num = randomRays.size();
                resColor=resColor.reduce(num);
                color=color.add(resColor);             //to get average color..
            }
            else
            {
                Ray reflectedRay = constructReflectedRay(gp.geometry.getNormal(gp.point), gp.point, rayFromCamera);
                GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);

                if (reflectedPoint != null)
                    color = color.add(calcColor(reflectedPoint, reflectedRay, level - 1, kkr)
                            .scale(kr));
            }


        }

        double kt = gp.geometry.getMaterial().kT.d1;
        double kkt = k*kt;

        //(2) ADD COLOR FROM REFRACTION
        if(kkt>MIN_CALC_COLOR_K) {
            //check if material is blurry:
            if(gp.geometry.getMaterial().kBlurry != Double3.ZERO) //if blurry:
            {
                Color resColor = Color.BLACK;
                List<Ray> randomRays = constructRefractedRay(gp, rayFromCamera, gp.geometry.getNormal(gp.point))
                        .getRandomRays(gp.point, gp.geometry.getMaterial().kBlurry.d1);
                for (Ray ray_i: randomRays) {

                    GeoPoint refractedPoint = findClosestIntersection(ray_i);
                    if(refractedPoint != null)
                        resColor = resColor.add(calcColor(refractedPoint, ray_i, level - 1, kkt)
                                .scale(kt));
                }
                int num = randomRays.size();
                resColor=resColor.reduce(num);
                color=color.add(resColor);             //to get average color..
            }
            else
            {
                Ray refractedRay = constructRefractedRay(gp, rayFromCamera, gp.geometry.getNormal(gp.point));
                GeoPoint refractedPoint = findClosestIntersection(refractedRay);

                if (refractedPoint != null)
                    color = color.add(calcColor(refractedPoint, refractedRay, level - 1, kkt)
                            .scale(kt));
            }

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
     * @param normal normal ray at geometry's point
     * @param p of interesection with cameraRay and geometry
     * @param cameraRay - ray from camera
     * @return new ray from interection point to surroundings
     */
    public Ray constructReflectedRay(Vector normal, Point p, Ray cameraRay){

        //see ReadMe - Diagram 3.2 - Calculating Vectors "r" and "l"!

        Vector v = cameraRay.getDirVector();
        Vector dir = v.subtract(normal.scale(v.dotProduct(normal)*(2))).normalize();

        return new Ray(p, dir, normal);
    }

    /**
     * @param gp - point to return color for
     * @param cameraRay ray from camera...
     * @return ray from gp to surrounding (refraction of camera...)
     */
    public Ray constructRefractedRay(GeoPoint gp, Ray cameraRay, Vector normal){

        return new Ray(gp.point, cameraRay.getDirVector(), normal);
        // ** in our implementation, we will assume that the ray passes thru the material
        // without changing direction
    }

    private GeoPoint findClosestIntersection(Ray ray){

            List<GeoPoint> geoIntersections = scene.geometries.findGeoIntersections(ray);
            if (geoIntersections == null)
                return null;
            else
                return ray.findClosestGeoPoint(geoIntersections);

    }


}
