package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import primitives.Color;
import primitives.Double3;

public class Scene {

    public String name;
    public Color background;
    public AmbientLight ambientLight;
    public Geometries geometries;

    private Scene(String _name){
        name = _name;
        background = Color.BLACK;
        ambientLight = new AmbientLight();
        geometries = new Geometries();
    }

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
            scene.background = color;
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
        public Scene build(){
            if(scene.name == null
            || scene.geometries == null)
                throw new NullPointerException("Scene is lacking either a name of geometries!");
            else
                return scene;
        }
    }
}
