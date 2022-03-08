package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for primitives.Point class
 */
class PointTest {
    Point p1 = new Point(1, 2, 3), p2 = new Point(4, 6, 8);
    Vector v1 = new Vector(3, 4, 5);

    /**
     * Test method for {@link primitives.Point#subtract(Point)} 
     */
    @Test
    void subtract() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that the subtract is proper
        assertEquals(v1, p2.subtract(p1), "subtract() wrong result");
    }

    /**
     * Test method for {@link primitives.Point#add(Vector)}
     */
    @Test
    void add() {
        // ============ Equivalence Partitions Tests ==============
        // TC02: Test that the add is proper
        assertEquals(p2, p1.add(v1), "add() wrong result");
    }
    /**
     * Test method for {@link primitives.Point#distanceSquared(Point)} 
     */
    @Test
    void distanceSquared() {
        fail("Not yet implemented");
    }
    /**
     * Test method for {@link primitives.Point#distance(Point)}
     */
    @Test
    void distance() {
        fail("Not yet implemented");
    }
}