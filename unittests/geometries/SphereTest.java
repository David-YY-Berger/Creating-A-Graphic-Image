package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SphereTest {

    //objects used for entire class..
    Vector class_v1 = new Vector(0, 0, 1);
    Point class_p1 = new Point(0, 0, 0), p2 = new Point(0, 0, 6), p3 = new Point(0, 0, 5);
    Sphere class_s1 = new Sphere(5, class_p1);

    /**
     * Test method for {@link geometries.Sphere#getNormal(Point)}
     */
    @Test
    void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that the getNormal is proper
        assertEquals(class_v1, class_s1.getNormal(p3), "getNormal() -> wrong result");
        //should use util.isEqual??
    }

    /**
     * Test Method for {@link Sphere#findIntersections(Ray)}
     */
    @Test
    public void testFindIntersections() {
        Sphere sphere_1_0_0 = new Sphere(1d, new Point(1, 0, 0));

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere_1_0_0.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(1, 1, 0))),
                "Ray should not intersect with sphere!");

        // TC02: Ray starts before and crosses the sphere (2 points)
        //p1 and p2 are actual result points...
        Point p1 = new Point(0.0651530771650466, 0.355051025721682, 0);
        Point p2 = new Point(1.53484692283495, 0.844948974278318, 0);
        List<Point> result = sphere_1_0_0.findIntersections(new Ray(new Point(-1, 0, 0),
                new Vector(3, 1, 0)));
        //if we dont get 2 intersection points...
        assertEquals(2, result.size(), "Wrong number of points");

        //compare actual_result points...
        if (result.get(0).getX() > result.get(1).getX())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(p1, p2), result, "findIntersections() returned the wrong results!");

        // TC03: Ray starts inside the sphere (1 point) and continues out
        result = sphere_1_0_0.findIntersections(new Ray(new Point(1.5, 0, 0), new Vector(5, 1, 0)));
        assertEquals(1, result.size(), "Wrong num points if ray starts inside of sphere");

        // TC04: Ray starts after the sphere (0 points) and points out
        //result = sphere_1_0_0.findIntersections(new Ray(new Point(2.5, 0, 0), new Vector(5, 2, 0)));
        assertNull( sphere_1_0_0.findIntersections(new Ray(new Point(2.5, 0, 0), new Vector(5, 2, 0))),
                "Incorrectily finds a point, when ray begins out of circle and points outward!");

        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line crosses the sphere's boundary (but does not cross within the sphere)
        //->ASSUMPTION: we dismiss intersection of a ray's beginning point and the geometry's surface

        // TC11: Ray starts at sphere and goes inside (1 points)
        result = sphere_1_0_0.findIntersections(new Ray(new Point(2, 0, 0), new Vector(-5, 1, 0)));
        assertEquals(1, result.size(), "does not give intersection for Ray that begins " +
                "at sphere's boundary and points inward!");
        // TC12: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere_1_0_0.findIntersections(new Ray(new Point(2, 0, 0), new Vector(5, 1, 0))),
                "gives intersection for Ray that begins " +
                        "at sphere's boundary and points outward!");
       // result = sphere_1_0_0.findIntersections(new Ray(new Point(2, 0, 0), new Vector(5, 1, 0)));
        //assertEquals(0, result.size(), "gives intersection for Ray that begins " +
          //      "at sphere's boundary and points outward!");

        // **** Group: Ray's line goes through the center
        // TC13: Ray starts outside the sphere's boundary (2 points)
        result = sphere_1_0_0.findIntersections(new Ray(new Point(2.5, 0, 0), new Vector(-5, 0, 0)));
        assertEquals(2, result.size(), "Does not give 2 intersection points for vector" +
                "starting out of sphere and going inside thru sphere's center");

        // TC14: Ray starts at sphere and goes inside (1 points)
        result = sphere_1_0_0.findIntersections(new Ray(new Point(2, 0, 0), new Vector(-5, 0, 0)));
        assertEquals(1, result.size(), "Does not give 1 intersection point for vector" +
                "starting at sphere's boundary and going inside thru sphere's center");

        // TC15: Ray starts inside (1 points)
        result = sphere_1_0_0.findIntersections(new Ray(new Point(0.5, 0, 0), new Vector(5, 0, 0)));
        assertEquals(1, result.size(), "Does not give 1 intersection point for vector" +
                "starting inside sphere and going out thru sphere's center");

        // TC16: Ray starts at the center (1 points)
        result = sphere_1_0_0.findIntersections(new Ray(new Point(1, 0, 0), new Vector(0, 0, -5)));
        assertEquals(1, result.size(), "Does not give 1 intersection point for vector" +
                "starting at center and going out");

        // TC17: Ray starts at sphere and goes outside (0 points)
        result = sphere_1_0_0.findIntersections(new Ray(new Point(0, 1, 0), new Vector(0, 5, 0)));
        assertEquals(0, result.size(), "incorrectly gives intersection point for " +
                "vector starting on sphere boundary and going out");

        // TC18: Ray starts after sphere (0 points)
        result = sphere_1_0_0.findIntersections(new Ray(new Point(2.5, 0, 0), new Vector(5, 0, 0)));
        assertEquals(0, result.size(), "incorrectly gives intersection point for " +
                "vector starting after sphere boundary and going out");

        //(in hebrew, tangent = "meishik").
        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        //for these tests --> TANGENT POINT IS AT (2, 0, 0)
        // TC19: Ray starts before the tangent point
        result = sphere_1_0_0.findIntersections(new Ray(new Point(2, -1, 0), new Vector(0, 5, 0)));
        assertEquals(0, result.size(), "incorrectly gives intersection point for " +
                "vector tangent to sphere (starting before tangent point)");

        // TC20: Ray starts at the tangent point
        result = sphere_1_0_0.findIntersections(new Ray(new Point(2, 0, 0), new Vector(0, 5, 0)));
        assertEquals(0, result.size(), "incorrectly gives intersection point for " +
                "vector tangent to sphere (starting at tangent point)");

        // TC21: Ray starts after the tangent point
        result = sphere_1_0_0.findIntersections(new Ray(new Point(2, 1, 0), new Vector(0, 5, 0)));
        assertEquals(0, result.size(), "incorrectly gives intersection point for " +
                "vector tangent to sphere (starting before tangent point)");

        // **** Group: Special cases
        //(TC19 -> * i did not understand this test....david berger)
        // TC19: Ray's line is outside, and is orthogonal to the sphere's center line
        result = sphere_1_0_0.findIntersections(new Ray(new Point(3, 1, 0), new Vector(0, 5, 0)));
        assertEquals(0, result.size(), "incorrectly gives intersection point for " +
                "vector outside of sphere, orthogonal to ray of sphere's center line");

    }



    //END OF CLASS
}
