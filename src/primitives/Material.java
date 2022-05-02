package primitives;

/**
 * contained by Geometry {@link geometries.Geometry}
 * used for calculating the light refraction on Geometry's surfaces
 * {@link Material#kD the difusive coeffitient}
 * {@link Material#kS the specular coeffitient}
 */
public class Material {

    public Double3 kD = Double3.ZERO;
    public Double3 kS = Double3.ZERO;
    public int nShininess = 0;

    // setters
    public Material setkD(Double3 kD) {
        this.kD = kD;
        return this;
    }

    public Material setkS(Double3 kS) {
        this.kS = kS;
        return this;
    }

    public Material setkD(double kD) {
        this.kD = new Double3(kD);
        return this;
    }

    public Material setkS(double kS) {
        this.kS = new Double3(kS);
        return this;
    }

    // getters
    public Double3 getkD() {
        return kD;
    }

    public Double3 getkS() {
        return kS;
    }

    public int getnShininess() {
        return nShininess;
    }

    public Material setnShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }




}
