package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author David Berger
 */
class PlaneTest {
    /**
     * Test methods for Plane's CTOR 
     * {@link Plane#Plane(Point, Point, Point)}
     */
    @Test
    public void testPlane() {
        assertThrows(IllegalArgumentException.class,
                () -> new Plane(new Point(0,0,0), new Point(1, 2, 3), new Point(-2, -4, -6)),
                "Plane CTOR does not throw exception for points on same line");
        assertThrows(IllegalArgumentException.class,
                ()-> new Plane(new Point(1, 2, 3), new Point( 5, 6, 7), new Point(1, 2,3)),
                "Plane CTOR does not throw exception for identical points!");
    }

}