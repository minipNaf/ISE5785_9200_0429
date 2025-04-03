package primitives;

import org.junit.jupiter.api.Test;

import static java.lang.Math.sqrt;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for primitives.Point class
 */
class PointTest {
    //test cases for Point class
    Point point = new Point(1,2,3);
    Vector vec = new Vector(4,5,6);
    Point otherPoint = new Point(3,2,1);

    /**
     * Test method for {@link Point#add(Vector)}
     */
    @Test
    void testAdd() {
        ///* ============ Equivalence Partitions Tests ============== */
        //test 01: check case of adding a vector to a point
        assertEquals(new Point(5,7,9),point.add(vec),
                "ERROR: (point + vector) = other point does not work correctly");

        ////* ============ Boundary Values Tests ================== */
        //test 01: adding a vector which is the negative of the point
        assertEquals(Point.ZERO,point.add(new Vector(-1,-2,-3)),
                "ERROR: (point + vector) = center of coordinates does not work correctly");
    }

    @Test
    void testDistanceSquared() {
        assertEquals(8,point.distanceSquared(otherPoint),0.00001,
                "ERROR: distanceSquared() does not work");
        assertEquals(0, point.distanceSquared(point), 0.00001,
                "ERROR: point squared distance to itself is not zero");

    }

    @Test
    void testDistance() {
        assertEquals(sqrt(8),point.distance(otherPoint),0.00001,
                "ERROR: distanceSquared() does not work");
        assertEquals(0, point.distance(point), 0.00001,
                "ERROR: point distance to itself is not zero");
    }

    /**
     * test the substract operation
     * test for: {@link primitives.Point#substract(Point)} ()}
     */
    @Test
    void testSubtract() {
        // ============ Equivalence Partitions Tests ==============
        //test 01: check case for substarcting a vector from a point
        assertEquals(new Vector(-3,-3,-3),point.subtract(vec),
                "ERROR: subtract() does not work correctly");

        // ============= Boundary Values Tests ==================
        //test 01: check case for substracting a vector from itself
        assertThrows(IllegalArgumentException.class,()->point.subtract(point),
                "ERROR: (point - vector with the same xyz) does not throw an exception");
    }
}