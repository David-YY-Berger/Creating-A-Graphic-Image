import primitives.*;

import static java.lang.System.out;

public final class Main {

    public static void main(String[] args) {

        //-2<x<6
        //-4<y<4
        Ray ray = new Ray(new Point(2, 0, 10), new Vector(0, 0, -1));
        ray.getRandomRays(new Point(2, 0, 0), .04);



    }
}
