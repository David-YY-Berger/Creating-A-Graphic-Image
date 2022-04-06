package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

abstract public class RayTracerBase {

    protected Scene scene;

    public RayTracerBase(Scene _scene){
        scene = _scene;
    }
    abstract public Color traceRay(Ray _ray);
}
