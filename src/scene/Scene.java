package scene;

import geometries.Geometries;
import geometries.Geometry;
import lighting.AmbientLight;
import lighting.LightSource;
import primitives.Color;
import primitives.Double3;

import java.util.LinkedList;
import java.util.List;

public class Scene {

    public String name;
    public Color backgroundColor;
    public AmbientLight ambientLight;
    public Geometries geometries;
    public List<LightSource> lights;

    private Scene(String _name){
        name = _name;
        backgroundColor = Color.BLACK;
        ambientLight = new AmbientLight();
        geometries = new Geometries();
        lights = new LinkedList<>();
    }



//    public String getName() { return name; }
//    public Color getBackground() { return backgroundColor; }
//    public Geometries getGeometriesList() { return geometriesList; }
//    public AmbientLight getAmbient() { return ambientLight; }

    /**
     * EXAMPLE OF HOW TO CALL THE SCENE'S BUILDER:
     *     Scene myScene = new Scene.Builder("davidsTestScene")
     *                 .setAmbientLight(new Color(255, 255, 0), new Double3(.1, .1, .1))
     *                 .setBackground(new Color(100, 100, 100))
     *                 .addGeometry(new Sphere(2, new Point(6, 0, 0)))
     *                 .addGeometry(new Sphere(2, new Point(6, 3, 0)))
     *                 .build();           // <<--MUST END WITH BUILD()!!!!!
     *
     *
     *                 or
     *                 scene.ambientLight = new AmbientLight(new Color(java.awt.Color.WHITE), new Double3(0.15));
     */
    public static class Builder{

        private Scene scene;
        public Builder(String name){
            scene = new Scene(name);
        }
        public Builder reset(String name){
            scene = new Scene(name);
            return this;
        }
        public Builder setBackground(Color color){
            scene.backgroundColor = color;
            return this;
        }
        public Builder setAmbientLight(Color color, Double3 Ka){
            scene.ambientLight = new AmbientLight(color, Ka);
            return this;
        }
        public Builder setAmbientLight(AmbientLight ambientLight){
            scene.ambientLight = ambientLight;
            return this;
        }
        public Builder addGeometry(Geometry geo){
            scene.geometries.add(geo);
            return this;
        }
        public Builder setLights(List<LightSource>  lights) {
            scene.lights = lights;
            return this;
        }

        public Scene build(){
            if(scene.name == null
            || scene.geometries == null)
                throw new NullPointerException("Scene is lacking either a name of geometries!");
            else
                return scene;
        }
    }
}
