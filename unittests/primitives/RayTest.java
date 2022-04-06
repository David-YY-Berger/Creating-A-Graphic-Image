package primitives;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RayTest {

    /**
     * test method for {@link Ray#findClosestPoint(List)}
     */
    @Test
    void testFindClosest() {

        String errorMsg = "function does not find closet point to p0 in ray!";

        Ray ray = new Ray(new Point(0, 0, 0), new Vector(10, 0, 0));
        //points in order of shortest distance from p0 in ray
        Point closestPoint = new Point(1, 0, 0);
        Point secondPoint = new Point(3, 0, 0);
        Point thirdPoint = new Point(6, 0, 0);
        Point lastPoint = new Point(11, 0, 0);

        // ============ Equivalence Partitions Tests ==============
        //TC01 find closest point in middle of list
        List<Point> lst = Arrays.asList(secondPoint, closestPoint, thirdPoint, lastPoint);
        assertTrue(closestPoint.equals(ray.findClosestPoint(lst)), errorMsg);

        // =============== Boundary Values Tests ==================
        //TC02 find closest point at front of list
        lst = Arrays.asList(closestPoint, secondPoint, thirdPoint, lastPoint);
        assertTrue(closestPoint.equals(ray.findClosestPoint(lst)), errorMsg);

        //TC03 find closest point at end of list
        lst = Arrays.asList(secondPoint, thirdPoint, lastPoint, closestPoint);
        assertTrue(closestPoint.equals(ray.findClosestPoint(lst)), errorMsg);

        //TC04 run function with empty list
        List<Point> finalLst = new LinkedList<>();
        assertThrows(IllegalArgumentException.class, ()-> {ray.findClosestPoint(finalLst);},
            "does not throw exception for empty list (given as parameter)" );

    }
}