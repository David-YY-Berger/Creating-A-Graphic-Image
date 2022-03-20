package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class TriangleTest {

    public void testGetNormal() {

        // ============ Equivalence Partitions Tests ==============

        //TC01 Check if it returns the correct normal
        Triangle testTriangle =  new Triangle(new Point(1, 1, 2), new Point(-4, 2, 2), new Point(-2, 1, 5));
        Vector ans = new Vector(3, 9, 1);
        assertEquals(testTriangle.getNormal(), ans, "getNormal() does not return correct vector!");
    }
    /**
     * Test Method for {@link Triangle#findIntsersections(Ray)}
     */
    @Test
    public void testFindIntersections() {

        // ============ Equivalence Partitions Tests ==============

        //TC02 The point is inside the triangle

        //TC03 The point is against edge

        //TC04 The point is against vertex

        // =============== Boundary Values Tests ==================

        //TC05 The point is on edge

        //TC06 The point is on vertex

        //TC07 The point is on edge continuation

    }
}