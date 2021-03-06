package primitives;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author David Berger
 */
class VectorTest {

    /**
     * Test method for BOTH of Vector's CTOR...
     * {@link Vector#Vector(double, double, double)}
     * {@link Vector#Vector(Double3)}
     */
    @Test
    public void testVector(){
        //tests for both CTORs..
        assertThrows(IllegalArgumentException.class, () -> new Vector(0,0,0),
                "CTOR does not throw exception for Zero Vector!");

        assertThrows(IllegalArgumentException.class,  () -> new Vector(Double3.ZERO),
                "CTOR does not throw exception for Zero Vector!");
    }

    /**
     * Test method for {@link primitives.Vector#add(Vector)}
     */
    @Test
    public void testAdd() {
        Vector v1 = new Vector(1, 2, 3), v2 = new Vector(4, 5, 6), v3 = new Vector(5, 7, 9);
        assertEquals(v3, v1.add(v2), "add() wrong result");
    }

    /**
     * Test method for {@link primitives.Vector#subtract(Point)}
     */
    @Test
    public void testSubtract() {
        Vector v2 = new Vector(4, 5, 6), v3 = new Vector(5, 7, 9);
        Point p1 = new Point(1, 2, 3);
        assertEquals(v2, v3.subtract(p1), "subtract() wrong result");
    }

    /**
     * Test method for {@link Vector#normalize()}
     */
    @Test
    public void testNormalize(){

        // ============ Equivalence Partitions Tests ==============
        //TEST 1
        Vector v1 = new Vector(3, 4, 5);
        Vector normalizedVec = v1.normalize();
        assertTrue(Util.isZero(normalizedVec.length() - 1),
                "normalize() does not return vector whose length is = 1!");
        //TEST 2
        assertTrue(normalizedVec.dotProduct(v1) > 0,
                "normalize() returns a vector in the opposite direction!");
        //bec dot product is negative....

        //TEST 3
        //calculated normal of vector (3, 4, 5)
        Vector calculatedNormal = new Vector(0.4242640687119285, 0.565685424949238, 0.7071067811865475);
        assertEquals(normalizedVec , calculatedNormal, "calculation is inaccurate!");
    }

    /**
     * Test method for {@link Vector#dotProduct(Vector)}
     */
    @Test
    void testDotProduct() {

        // ============ Equivalence Partitions Tests ==============
        Vector v1 = new Vector(3, 4, 5);
        Vector v2 = new Vector(7, 8, 9);
        assertEquals(98, v1.dotProduct(v2), "dotProduct() is inaccurate!");

        // =============== Boundary Values Tests ==================
        //ortho vectors...
        v1 = new Vector(-1, 1, 0);
        v2 = new Vector(1, 1, -1);

        assertTrue(Util.isZero(v1.dotProduct(v2)), "dot product of orthogonal vectors does not yield zero!");
    }

    /**
     * Test method for {@link Vector#crossProduct(Vector)}
     */
    @Test
    public void testCrossProduct() {
        //example objects:
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(0, 3, -2);
        Vector actualProduct = v1.crossProduct(v2);

        // ============ Equivalence Partitions Tests ==============

        // Test that length of cross-product is proper (orthogonal vectors taken
        // for simplicity)
        assertEquals( actualProduct.length(), v1.length() * v2.length(),0.00001, "crossProduct() wrong result");

        // Test cross-product result orthogonality to its operands
        assertTrue(Util.isZero(actualProduct.dotProduct(v1)), "crossProduct() result is not orthogonal to 1st operand");
        assertTrue(Util.isZero(actualProduct.dotProduct(v2)), "crossProduct() result is not orthogonal to 2nd operand");

        // =============== Boundary Values Tests ==================
        // Test zero vector from cross-productof co-lined (parallel) vectors
        Vector v3 = new Vector(-2, -4, -6);
        assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v3),
                "crossProduct() for parallel vectors does not throw an exception (rather, returns a Zero vector...)");
    }

    /**
     * Test method for {@link Vector#length()}
     */
    @Test
    public void testLength() {
        //example Vector
        Vector v1 = new Vector(3, 4, 5);
        assertEquals(Math.sqrt(50), v1.length(), "length() is innaccurate");
        //check for negative direction...
        v1 = new Vector(3, 4, -5);
        assertEquals(Math.sqrt(50), v1.length(), "length() is innaccurate");
    }
    /**
     * Test method for {@link Vector#projection(Vector)}
     */
    @Test
    public void testProjection(){
        // ============ Equivalence Partitions Tests ==============
        //Test proj of vec 1 onto vec 2
        Vector vec1 = new Vector(-1, 4, 2);
        Vector vec2 = new Vector(1, 0, 3);
        assertEquals(new Vector(.5, 0, 1.5), vec2.projection(vec1),
                "projection() is inaccurate!");
    }

    /**
     * Test method for {@link Vector#rotateAroundZ(double)}
     */
    @Test
    public void testRotateLeft(){
        Vector v = new Vector(1, 0, 3);
        assertEquals( new Vector(0, 1, 3), v.rotateAroundZ(90), "does not accurately rotate vector to the left!");
    }
    /**
     * Test method for {@link Vector#rotateAroundY(double)}
     */
    @Test
    public void testRotateDown(){
        Vector v = new Vector(1, 0, 3);
        assertEquals( new Vector(3, 0, -1), v.rotateAroundY(90),"does not accurately rotate vector downward!");
    }


}