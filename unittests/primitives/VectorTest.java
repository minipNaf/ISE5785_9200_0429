package primitives;

import org.junit.jupiter.api.Test;

import static java.lang.Math.sqrt;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * Unit tests for primitives.Vector class
 * This class contains test cases to verify the functionality of the Vector class,
 * including its constructor, scale, add, lengthSquared, length, normalize,
 * crossProduct, dotProduct, and subtract methods.
 */
class VectorTest {
    //test cases for Vector class
    Vector vec = new Vector(1,2,3);
    Vector other = new Vector(4,5,6);

    /**
     * Test constructor for the Vector class.
     * This test case verifies that the constructor does not throw an exception
     * test for: {@link primitives.Vector#Vector(double, double, double)}
     */
    @Test
    void testConstructorCoordinates() {
        // ============ Equivalence Partitions Tests ==============
        //test 01: check case of constructing a regular vector
        assertDoesNotThrow(()->new Vector(1,2,3), "ERROR: Failed constructing a correct vector");

        // =============== Boundary Values Tests ==================
        //test 01: check case of constructing a zero vector
        assertThrows(IllegalArgumentException.class, ()-> new Vector(0,0,0),
                "ERROR: can't construct a zero vector");
    }

    /**
     * test constructor for Doubl3
     * test for:{@link primitives.Vector#Vector(Double3)}
     */
    @Test
    void testConstructorDouble3() {
        // ============ Equivalence Partitions Tests ==============
        //test 01: check case of constructing a regular vector
        assertDoesNotThrow(()->new Vector(new Double3(1,2,3)), "ERROR: Failed constructing a correct vector");

        // =============== Boundary Values Tests ==================
        //test 01: check case of constructing a zero vector
        assertThrows(IllegalArgumentException.class, ()-> new Vector(new Double3(0,0,0)), "ERROR: can't construct a zero vector");
    }

    /**
     * test the scale operation
     * test for: {@link primitives.Vector#scale(double}
     */
    @Test
    void testScale() {
        // ============ Equivalence Partitions Tests ==============
        //test 01: check case of scaling by positive scalar
        assertEquals(new Vector(3.8*1,3.8*2,3.8*3),vec.scale(3.8),
                "ERROR: scaling by positive scalar doesn't work correctly");
        //test 02: check case of scaling by negitive scalar
        assertEquals(new Vector(-3.8*1,-3.8*2,-3.8*3),vec.scale(-3.8),
                "ERROR: scaling by negitive scalar doesn't work correctly");

        // =============== Boundary Values Tests ==================
        //test 01: check case of scaling by a scalar of zero
        assertThrows(IllegalArgumentException.class, ()->vec.scale(0),
                "ERROR: can't scale by factor of zero");
    }

    /**
     * test the add operation
     * test for: {@link primitives.Vector#add(Vector)}
     */
    @Test
    void testAdd() {

        // ============ Equivalence Partitions Tests ==============
        //test 01: check case of adding a vector to another vector
        assertEquals(new Vector(4,4,4), vec.add(new Vector(3,2 ,1)),
                "ERROR: add() does not work");
        // ============= Boundary Values Tests ==================
        //test 01: check case of adding a vector which is the negative of the vector
        assertThrows(IllegalArgumentException.class, ()->vec.add(new Vector(-1,-2,-3)),
                "ERROR: Vector + -itself does not throw an exception");
    }

    /**
     * test the lengthSquared operation
     * test for: {@link primitives.Vector#lengthSquared()}
     */
    @Test
    void testLengthSquared() {
        // ============ Equivalence Partitions Tests ==============
        //test 01: check case of calculating the length squared of a vector
        assertEquals(14,vec.lengthSquared(),0.00001,
                "ERROR: lengthSquared() does not work correctly");
    }

    /**
     * test the length operation
     * test for: {@link primitives.Vector#length()}
     */
    @Test
    void testLength() {
        // ============ Equivalence Partitions Tests ==============
        //test 01: check case of calculating the length of a vector
        assertEquals(sqrt(14),vec.length(),0.00001,
                "ERROR: lengthSquared() does not work correctly");
    }

    /**
     * test the normalize operation
     * test for: {@link primitives.Vector#normalize()}
     */
    @Test
    void testNormalize() {
        // ============ Equivalence Partitions Tests ==============
        //test 01: check case  for normalizing a vector
        assertEquals(new Vector(1/sqrt(14),2/sqrt(14),3/sqrt(14)),vec.normalize(),
                "ERROR: the normalized vector is not a unit vector");
    }

    /**
     * test the normalize operation
     * test for: {@link primitives.Vector#crossProduct(Vector)} ()}
     */
    @Test
    void testCrossProduct() {
        // ============ Equivalence Partitions Tests ==============
        //test 01: check case for cross product between regular vectors
        assertEquals(new Vector(-3/sqrt(54),6/sqrt(54),-3/sqrt(54)),vec.crossProduct(other),
                "ERROR: crossProduct() does not work correctly ");
        //test 02: check case for cross product between regular vectors - opposite product
        assertEquals(new Vector(3/sqrt(54),-6/sqrt(54),+3/sqrt(54)),other.crossProduct(vec),
                "ERROR: crossProduct() does not work correctly ");

        // ============= Boundary Values Tests ==================
        //test 01: check case for cross product between vector that share a same direction
        assertThrows(IllegalArgumentException.class,()->vec.crossProduct(new Vector(2,4,6)),
                "ERROR: the other vector is not parallel to the original one");
        //test 02: check case for cross product between vector that share an opposite direction
        assertThrows(IllegalArgumentException.class,()->vec.crossProduct(new Vector(-1,-2,-3)),
                "ERROR: the other vector is not parallel to the original one");
    }

    /**
     * test the dot product operation
     * test for: {@link primitives.Vector#dotProduct(Vector)} ()}
     */
    @Test
    void testDotProduct() {
        // ============ Equivalence Partitions Tests ==============
        //test 01: check case for dot product between vectors accute(חדה) angle
        assertEquals(0.68,new Vector(0,-1,0).dotProduct(new Vector(056,-0.68,0)),0.00001,
                "ERROR: dotProduct() does not work correctly ");
        //test 02: check case for dot product between vectors not accute(קהה) angle
        assertEquals(-0.68,new Vector(0,1,0).dotProduct(new Vector(056,-0.68,0)),0.00001,
                "ERROR: dotProduct() does not work correctly ");

        // ============= Boundary Values Tests ==================
        //test 01: check case for dot product between orthogonal vectors - 90 degrees
        assertEquals(0,new Vector(0,-1,0).dotProduct(new Vector(1,0,0)),0.00001,
                "ERROR: dotProduct() for orthogonal 90 degrees vectors is not zero");
        //test 01: check case for dot product between vectors 180 degrees
        assertEquals(-1,new Vector(0,-1,0).dotProduct(new Vector(0,1,0)),0.00001,
                "ERROR: dotProduct() for vectors 180 degress is incorrect");
        //test 01: check case for dot product between vectors 270 degrees
        assertEquals(0,new Vector(0,-1,0).dotProduct(new Vector(-1,0,0)),0.00001,
                "ERROR: dotProduct() for vectors 270 degress is not zero");
        //test 01: check case for dot product between vector and itself
        assertEquals(1,new Vector(0,-1,0).dotProduct(new Vector(0,-1,0)),0.00001,
                "ERROR: dotProduct() for vector with itself is incorrect");
    }

    /**
     * test the substract operation
     * test for: {@link primitives.Vecto#substract(Vector)} ()}
     */

    void testSubtract() {
        // ============ Equivalence Partitions Tests ==============
        //test 01: check case for substarcting two vectors
        assertEquals(new Point(-3,-3,-3),vec.subtract(other),
                "ERROR: subtract() does not work correctly");

        // ============= Boundary Values Tests ==================
        //test 01: check case for substracting a vector from itself
        assertThrows(IllegalAccessException.class,()->vec.subtract(vec),
                "ERROR: (point - itself) does not throw an exception");
    }
}