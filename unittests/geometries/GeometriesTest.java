package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GeometriesTest {

    @Test
    void findIntersections() {

        //this functions uses the same variables, but overwrites the data for each test

        // ============ Equivalence Partitions Tests ==============
        //EPT-Test 1: standard test - ray intersects two of three shapes...
        Sphere sphere = new Sphere(2, new Point(-2, 0, 0));
        Triangle tri = new Triangle(new Point(-6, 0, 2), new Point(-6, 0, -2), new Point(-6, 5, 0));
        Tube tube = new Tube(new Ray(new Point(0, 0, 10), new Vector(-1, 0, 0)), 2d);
        Geometries geometries = new Geometries();
        geometries.add(sphere);
        geometries.add(tri);
        geometries.add(tube);
        Ray ray = new Ray (new Point(1, 0, 0), new Vector(-10, 1, 0));

        List<Point> result = geometries.findIntersections(ray);
        assertEquals(3, result.size(), "Finds inaccurate number of intersection points");

        /**
         * o	אוסף גופים ריק (BVA)
                * o	אף צורה לא נחתכת (BVA)
                * o	צורה אחת בלבד נחתכת (BVA)
                * o	כמה צורות (אך לא כולן) נחתכות (EP)
                * o	כל הצורות נחתכות (BVA)
                */
        // =============== Boundary Values Tests ==================
        //BVA-Test 2: tests that no shape is intersected:
        sphere = new Sphere(3, new Point(1, 0, 0));
        ray = new Ray(new Point(0, 0, 3), new Vector(0, 0, 5));
        geometries = new Geometries(sphere);
        assertEquals(0, geometries.findIntersections(ray).size(),
                "incorrectly returns intersection point!");

        //BVA-Test 3: tests that only one shape is intersected:
        ray = new Ray(new Point(0, 0, .1), new Vector(0, 0, 5));
        assertEquals(1, geometries.findIntersections(ray).size(),
                "does not return 1 intersection point");

        //BVA-Test 4: tests with an empty geometries collection:

        //BVA-Test 4: tests that every shape in collection is intersected:

    }
}