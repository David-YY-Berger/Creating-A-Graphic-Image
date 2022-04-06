package lighting;

import primitives.Color;
import primitives.Double3;

public class AmbientLight {

    Color intencity;

    public AmbientLight(Color Ia, Double3 Ka){

        intencity = Ia.scale(Ka);
    }

    public AmbientLight(){
        intencity = Color.BLACK;
    }

    public Color getIntencity(){
        return intencity;
    }
}
