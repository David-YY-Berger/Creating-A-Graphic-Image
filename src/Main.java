import primitives.*;

import static java.lang.System.out;

public final class Main {

    public static void main(String[] args) {

        Ray ray = new Ray(Point.ZERO, new Vector(1, 1, 1));
        ray.getRandomRays(new Point(4, -4, 0), .04);



    }
}
