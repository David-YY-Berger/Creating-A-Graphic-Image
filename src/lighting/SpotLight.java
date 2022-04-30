package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class SpotLight extends PointLight{

    private Vector direction;

    public SpotLight(Vector _direction, Point _position, double _kC, double _kL, double _kQ, Color color){

        super(_position, _kC, _kL, _kQ, color);
        direction = _direction.normalize();
    }

    @Override
    public Color getIntensity(Point p) {

        double scaled = direction.dotProduct(getL(p));
        if(scaled > 0){
            return super.getIntensity(p).scale(scaled);
        }
        else {
            return super.getIntensity(p).scale(0);
        }
    }
}
