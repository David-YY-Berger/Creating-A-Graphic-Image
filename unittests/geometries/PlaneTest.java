package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

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

        // =============== Boundary Values Tests ==================
        assertThrows(IllegalArgumentException.class,
                () -> new Plane(new Point(0,0,0), new Point(1, 2, 3), new Point(-2, -4, -6)),
                "Plane CTOR does not throw exception for points on same line");

        assertThrows(IllegalArgumentException.class,
                ()-> new Plane(new Point(1, 2, 3), new Point( 5, 6, 7), new Point(1, 2, 3)),
                "Plane CTOR does not throw exception for identical points!");
    }

    /**
     * Test method for {@link Plane#getNormal(Point)}
     */
    @Test
    public void testGetNormal() {

        // ============ Equivalence Partitions Tests ==============
        Plane testPlane =  new Plane(new Point(-1, 1, 2), new Point(-4, 2, 2), new Point(-2, 1, 5));
        Vector ans = new Vector(3, 9, 1);
        ans = ans.normalize();
        assertEquals(testPlane.getNormal(), ans, "getNormal() does not return correct vector!");

    }
}