package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TriangleTest {

    /**
     * Test Method for {@link Triangle#findIntersections(Ray)}
     */
    @Test
    public void testGetNormal() {

        // ============ Equivalence Partitions Tests ==============

        //TC01 Check if it returns the correct normal
        Triangle testTriangle =  new Triangle(new Point(1, 1, 2), new Point(-4, 2, 2), new Point(-2, 1, 5));
        Vector ans = new Vector(3, 9, 1);
        assertEquals(testTriangle.getNormal(), ans, "getNormal() does not return correct vector!");
    }
    /**
     * Test Method for {@link Triangle#findIntersections(Ray)}
     */
    @Test
    public void testFindIntersections() {

        Triangle tr = new Triangle(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0));

        // ============ Equivalence Partitions Tests ==============

        //TC01 The point is inside the triangle
        assertEquals(List.of(new Point(1d / 3, 1d / 3, 1d / 3)), tr.findIntersections(new Ray(new Point(1, 1, 1), new Vector(-1, -1, -1))),
                "Bad intersection");

        //TC02 The point is against edge


        //TC03 The point is against vertex

        // =============== Boundary Values Tests ==================

        //TC04 The point is on edge

        //TC05 The point is on vertex

        //TC06 The point is on edge continuation

    }
}