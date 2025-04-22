package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for primitives.Ray class
 * This class contains test cases to verify the functionality of the Ray class,
 * including its constructor and the getDirection and getHead methods.
 */
class RayTest {

    /**
     * Test case for the Ray class.
     * This test case creates a Ray object and verifies its constructor, getDirection, and getHead methods.
     */
    Vector vec = new Vector(0,3,4);
    Point p = new Point(1,2,3);
    Ray ray = new Ray(vec, p);

    /**
     * Test case for the constructor of the Ray class.
     * This test case verifies that the constructor does not throw an exception
     * when creating a Ray object with valid parameters.
     * this test for: {@link primitives.Ray#Ray(Vector, Point)}
     */
    @Test
    void testConstructor(){
        // ============ Equivalence Partitions Tests ==============
        //test 01: check case of constructing a regular ray
        assertDoesNotThrow(()->new Ray(vec, p),
                "ERROR: Failed constructing a correct Ray");
    }
    /**
     * Test case for the getDirection method of the Ray class.
     * This test case verifies that the getDirection method returns the correct direction vector
     * for the given ray.
     * this test for: {@link primitives.Ray#getDirection()}
     */
    @Test
    void testGetDirection() {
        // ============ Equivalence Partitions Tests ==============
        // Test 01: check case of getting the direction vector of the ray
        assertEquals(vec.normalize(), ray.getDirection(), "ERROR: getDirection() doesn't work");
    }

    /**
     * Test case for the getHead method of the Ray class.
     * This test case verifies that the getHead method returns the correct head point
     * for the given ray.
     * this test for: {@link primitives.Ray#getHead()}
     */
    @Test
    void testGetHead() {
        // ============ Equivalence Partitions Tests ==============
        // Test 01: check case of getting the head point of the ray
        assertEquals(p, ray.getHead(), "ERROR: getHead() doesn't work");
    }

    @Test
    void testGetPoint() {
        // ============ Equivalence Partitions Tests ==============
        // Test 01: check case of getting the point at t=1
        assertEquals(new Point(1, 2.6,3.8), ray.getPoint(1),
                "ERROR: wrong point - positive scalar");
        // Test 02: check case of getting the point at t=-1
        assertEquals(new Point(1,1.4,2.2), ray.getPoint(-1),
                "ERROR: wrong point - negative scalar");

        // ============ Boundary Values Tests ==============
        // Test 01: check case of getting the point at t=0
        assertEquals(p, ray.getPoint(0), "ERROR: getPoint() for head doesn't work doesn't work");
    }
}