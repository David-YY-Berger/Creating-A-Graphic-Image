package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class PointLight extends Light implements LightSource{

    private Point position;
    private double kC = 1, kL = 0, kQ = 0; //coef calc light attenuation with distance; if higher, light decreases more quickly
    //equation: Intensity / (kC + kL*d + kQ*d^2) .... d = distance...

    //see Readme - Diagram 3.3 - Light Attenuation with Distance

    //CTOR
    public PointLight(Point _position, Color color){

        super(color);
        position = _position;
    }

    /**
     * @param kC - light attenuation with distance; if higher, light decreases more quickly
     */
    public PointLight setKc(double kC) { //"Narrow Beam"
        this.kC = kC;
        return this;
    }

    /**
     * @param kL - light attenuation with distance; if higher, light decreases more quickly (kL*d)
     */
    public PointLight setkL(double kL) {
        this.kL = kL;
        return this;
    }

    /**
     * @param kQ - light attenuation with distance; if higher, light decreases more quickly (kQ*d^2)
     */
    public PointLight setkQ(double kQ) {
        this.kQ = kQ;
        return this;
    }


    @Override
    public Color getIntensity(Point p) {

        double d = position.distance(p);
        double scaled = 1 / (kC + kL*d + kQ*d*d);
        return super.getIntensity().scale(scaled);
    }

    @Override
    public Vector getL(Point p) {

        return p.subtract(position).normalize();
    }

    @Override
    public double getDistance(Point point) {
        return point.distance(position);
    }
}
