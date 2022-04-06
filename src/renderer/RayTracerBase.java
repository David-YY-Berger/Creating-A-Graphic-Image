package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * represents different classes that trace rays thru pixels, and return approriate color of each pixel
 */
abstract public class RayTracerBase {

    protected Scene scene;

    public RayTracerBase(Scene _scene){
        scene = _scene;
    }

    /**
     * traceRay receives ray, and
     * @return color for the pixel
     */
    abstract public Color traceRay(Ray _ray);
}
