package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import java.util.List;

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
        //TC01 Point on the same line
        assertThrows(IllegalArgumentException.class,
                () -> new Plane(new Point(0,0,0), new Point(1, 2, 3), new Point(-2, -4, -6)),
                "Plane CTOR does not throw exception for points on same line");

        //TC02 Identical points
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
        //TC01 Check if it returns the correct normal
        Plane testPlane =  new Plane(new Point(-1, 1, 2), new Point(-4, 2, 2), new Point(-2, 1, 5));
        Vector ans = new Vector(3, 9, 1);
        ans = ans.normalize();
        assertEquals(testPlane.getNormal(), ans, "getNormal() does not return correct vector!");

    }

    /**
     * Test Method for {@link Plane#findIntersections(Ray)}
     */
    @Test
    public void testFindIntersections() {
        Plane pl = new Plane(new Point(0, 0, 1), new Vector(1, 1, 1));

        // ============ Equivalence Partitions Tests ==============

        //TC01 Ray starts above the plane and don't cross it
        assertNull(pl.findIntersections(new Ray(new Point(2,0,0),new Vector(1,0,0))),
                "Must not be plane intersection");

        //TC02 Ray starts under the plane and cross it
        assertEquals(List.of(new Point(1,0,0)), pl.findIntersections(new Ray(new Point(0.5, 0, 0), new Vector(1, 0, 0))),
                "Bad plane intersection");

        // =============== Boundary Values Tests ==================

        //TC03 Ray is parallel to the plane
        assertNull(pl.findIntersections(new Ray(new Point(1,1,1), new Vector(1,-1,0))),
                "Must not be plane intersection");

        //TC04 Ray is contained in the plane
        assertNull(pl.findIntersections(new Ray(new Point(1,0,0), new Vector(1,-1,0))),
                "Must not be plane intersection");

        //TC05 Ray is orthogonal to the plane under it
        assertEquals(List.of(new Point(1d/3,1d/3,1d/3)),pl.findIntersections(new Ray(new Point(1,1,1), new Vector(-1,-1,-1))),
                "Bad plane intersection");

        //TC06 Ray is orthogonal to the plane on it
        assertNull( pl.findIntersections(new Ray(new Point(1d/3, 1d/3, 1d/3), new Vector(1, 1, 1))),
                "Must not be plane intersection");

        //TC07 Ray is orthogonal to the plane above it
        assertNull(pl.findIntersections(new Ray(new Point(1,1,1), new Vector(1,1,1))),
                "Must not be plane intersection");

        //TC08 Ray starts on the plane
        assertNull(pl.findIntersections(new Ray(new Point(1d/3, 1d/3, 1d/3), new Vector(1, 1, 0))),
                "Must not be plane intersection");

        //TC09 Ray starts on the point we built the plane (constructor with point and ray)
        assertNull(pl.findIntersections(new Ray(new Point(0, 0, 1), new Vector(1, 1, 0))),
                "Must not be plane intersection");

    }

}