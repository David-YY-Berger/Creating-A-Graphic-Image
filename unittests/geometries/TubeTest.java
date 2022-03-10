package geometries;
import primitives.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TubeTest {


    @Test
    void getNormal() {

        // ============ Equivalence Partitions Tests ==============
        Tube t = new Tube(new Ray(new Point(0, 0, 0), new Vector(0, 0, 1)),
                1.0);
        Point p = new Point(0, 1, 2); //sits on cylinder's surface
        Vector normalVec = new Vector(0, 1, 0);
        assertEquals(normalVec, t.getNormal(p), "getNormal() does not return proper vector!");

    }
}