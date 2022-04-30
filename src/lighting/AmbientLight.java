package lighting;

import primitives.Color;
import primitives.Double3;

/**
 *
 */
public class AmbientLight extends Light{

    /**
     *
     * @param Ia
     * @param Ka
     */
    public AmbientLight(Color Ia, Double3 Ka){
        super(Ia.scale(Ka));
    }

    public AmbientLight(){
        super(Color.BLACK);
    }

}
