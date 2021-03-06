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
    public void testSubtract() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that the subtract is proper
        assertEquals(v1, p2.subtract(p1), "subtract() wrong result");
    }

    /**
     * Test method for {@link primitives.Point#add(Vector)}
     */
    @Test
    public void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        // TC02: Test that the add is proper
        assertEquals(p2, p1.add(v1), "add() wrong result");
    }
    /**
     * Test method for {@link primitives.Point#distanceSquared(Point)} 
     */
    @Test
    public void testDistanceSquared() {
        // ============ Equivalence Partitions Tests ==============
        // TC03: Test that the distanceSquared is proper
        assertEquals(50, p2.distanceSquared(p1), "distanceSquared() wrong result");
    }
    /**
     * Test method for {@link primitives.Point#distance(Point)}
     */
    @Test
    public void testDistance() {
        // ============ Equivalence Partitions Tests ==============
        // TC04: Test that the distance is proper
        assertEquals(Math.sqrt(50), p2.distance(p1), "distance() wrong result");
    }

}