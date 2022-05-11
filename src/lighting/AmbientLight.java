package lighting;

import primitives.Color;
import primitives.Double3;

/**
 *
 */
public class AmbientLight extends Light{

    /**
     *
     * @param Ia intensity of the light
     * @param Ka adjusts the light...a higher kA is stronger light
     */
    public AmbientLight(Color Ia, Double3 Ka){
        super(Ia.scale(Ka));
    }

    public AmbientLight(){
        super(Color.BLACK);
    }

}
