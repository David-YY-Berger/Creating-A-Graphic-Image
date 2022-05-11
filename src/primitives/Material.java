package primitives;

/**
 * contained by Geometry {@link geometries.Geometry}
 * used for calculating the light refraction on Geometry's surfaces
 * {@link Material#kD the difusive coeffitient}
 * {@link Material#kS the specular coeffitient}
 */
public class Material {

    //fields are explained here: https://en.wikipedia.org/wiki/Phong_reflection_model

    //FIELDS :
    public Double3 kD = Double3.ZERO; //diffusive coefficient; if (kD ==0), Material is color of the light shining on it...
                                        //if kD is higher, than the material keeps its original color, depsite the color of the light
    public Double3 kS = Double3.ZERO; //specular coef; strength of light returned WHERE LIGHT BEAM HITS OBJECTS
    public int nShininess = 0;          //when nShininess is large, material is shiny...general shininess, not just where light beam hits object

    public Double3 kT = Double3.ZERO; //transparency; if(kT ==0), Material is opaque (not transparent)
    public Double3 kR = Double3.ZERO; //reflectance; if(kR ==0), Material does not reflect at all


    // SETTERS
    /**
     * @param kS sets specular coef
     */
    public Material setKs(Double3 kS) {
        this.kS = kS;
        return this;
    }
    /**
     * @param kS sets specular coef
     */
    public Material setKs(double kS) {
        this.kS = new Double3(kS);
        return this;
    }

    /**
     * @param kD sets diffusive coef
     */
    public Material setKd(double kD) {
        this.kD = new Double3(kD);
        return this;
    }

     /**
     * @param kR - sets reflectance
     */
    public Material setKr(double kR) {
        this.kR = new Double3(kR);
        return this;
    }
    /**
     * @param kT set transparency
     */
    public Material setKt(Double3 kT) {
        this.kT = kT;
        return this;
    }
    /**
     * @param kT set transparency
     */
    public Material setKt(double kT) {
        this.kT = new Double3(kT);
        return this;
    }
    /**
     * @param nShininess see shininess
     */
    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }
}
