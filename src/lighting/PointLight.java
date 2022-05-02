package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class PointLight extends Light implements LightSource{

    private Point position;
    private double kC = 1, kL = 0, kQ = 0;

    public PointLight(Point _position, Color color){

        super(color);
        position = _position;
    }

    public PointLight setNarrowBeam(double kC) {
        this.kC = kC;
        return this;
    }

    public PointLight setkL(double kL) {
        this.kL = kL;
        return this;
    }

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
}
