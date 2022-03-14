package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class SphereTest {

    Vector v1 = new Vector(0, 0, 1);
    Point p1 = new Point(0, 0, 0), p2 = new Point(0, 0, 6), p3 = new Point(0, 0, 5);
    Sphere s1 = new Sphere(p1, 5);

    /**
     * Test method for {@link geometries.Sphere#getNormal(Point)}
     */
    @Test
    void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that the getNormal is proper
        assertEquals(v1, s1.getNormal(p3), "getNormal() -> wrong result");
        //should use util.isEqual??
    }

    /**
     * Test Method for {@link Sphere#findIntsersections(Ray)}
     */
    @Test
    public void testFindIntersections() {

        // ============ Equivalence Partitions Tests ==============

        // =============== Boundary Values Tests ==================


    }
}