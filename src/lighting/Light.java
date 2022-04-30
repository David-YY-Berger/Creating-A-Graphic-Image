package lighting;

import primitives.Color;

/**
 *
 */
abstract class Light {

    private Color intensity;

    /**
     *
     * @param _intensity
     */
    protected Light(Color _intensity){

        intensity = _intensity;
    }

    /**
     *
     * @return
     */
    public Color getIntensity() {

        return intensity;
    }
}
