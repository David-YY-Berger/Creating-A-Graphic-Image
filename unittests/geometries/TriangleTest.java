package geometries;

import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class TriangleTest {

    public void testGetNormal() {
        Triangle testTriangle =  new Triangle(new Point(1, 1, 2), new Point(-4, 2, 2), new Point(-2, 1, 5));
        Vector ans = new Vector(3, 9, 1);
        assertEquals(testTriangle.getNormal(), ans, "getNormal() does not return correct vector!");
    }
}