package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class CylinderTest {

    /**
     * Test Method for {@link Cylinder#getNormal(Point)}
     */
    @Test
    void testGetNormal() {

        // ============ Equivalence Partitions Tests ==============

        // TC01: when point is on the top of the cylinder
        Cylinder pl = new Cylinder(new Ray(new Point(0, 0, 0),new Vector(0,0,1)),  1d, 1d);
        assertEquals(new Vector(0, 0, 1d), pl.getNormal(new Point(0, 0.5, 1)), "Bad normal to cylinder");

        // TC02: when point is on the base of the cylinder
        assertEquals(new Vector(0, 0, -1d), pl.getNormal(new Point(0, 0.5, 0)), "Bad normal to cylinder");

        // TC03: when point is on the surface of the cylinder
        assertEquals(new Vector(0, 1d, 0), pl.getNormal(new Point(0, 1, 0.5)), "Bad normal to cylinder");

        // =============== Boundary Values Tests ==================

        // TC04: the center of the base of cylinder
        assertEquals(new Vector(0, 0, -1d),pl.getNormal(new Point(0,0,0)), "Error in boundary test with base !");

        // TC05: the center of the top of cylinder
        assertEquals(new Vector(0, 0, 1d),pl.getNormal(new Point(0,0,1)), "Error in boundary test with top !");
    }
}