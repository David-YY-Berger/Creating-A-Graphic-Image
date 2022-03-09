package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class SphereTest {

    Vector v1 = new Vector(1, 1, 1);
    Point p1 = new Point(2, 2, 2), p2 = new Point(2, 2, 3);
    Sphere s1 = new Sphere(p1, 5);

    /**
     * Test method for {@link geometries.Sphere#getNormal(Point)}
     */
    @Test
    void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that the getNormal is proper
        assertThrows(IllegalArgumentException.class, () -> s1.getNormal(p2),
                "getNormal() for point that is not on sphere does not throw any exeption");
        assertEquals(v1, s1.getNormal(p2), "getNormal() -> wrong result");
    }

}