package geometries;
import primitives.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TubeTest {


    @Test
    public void getNormal() {

        // ============ Equivalence Partitions Tests ==============
        Tube t = new Tube(new Ray(new Point(0, 0, 0), new Vector(0, 0, 1)),
                1.0);
        Point p1 = new Point(0, 1, 2); //sits on cylinder's surface
        Vector normalVec1 = new Vector(0, 1, 0);
        assertEquals(normalVec1, t.getNormal(p1), "getNormal() does not return proper vector!");

        // =============== Boundary Values Tests ==================

        Point p2 = new Point(1, 0, 0); //sits on cylinder's surface
        Vector normalVec2 = new Vector(1, 0, 0);
        assertEquals(normalVec2, t.getNormal(p2), "getNormal() does not return proper vector!");
    }
}