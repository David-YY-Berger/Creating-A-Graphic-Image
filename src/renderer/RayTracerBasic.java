package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

public class RayTracerBasic extends RayTracerBase{

    public RayTracerBasic(Scene _scene){
        super(_scene);

        throw new IllegalArgumentException(); //waht to do here??
    }

    @Override
    public Color traceRay(Ray _ray) {
        return null;
    }
}
