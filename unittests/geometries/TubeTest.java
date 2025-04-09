package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Unit tests for the Tube class.
 * This class contains test cases to verify the functionality of the Tube class,
 * including its constructor and the getNormal method
 */
class TubeTest {
    /**
     * Test case for the Tube class.
     * This test case creates a Tube object and verifies its constructor and getNormal method.
     */
    Tube tube = new Tube(3, new Ray(new Vector(0,0,1), new Point(0,0, 2)));
    /**
     * Test case for the constructor of the Tube class.
     * This test case verifies that the constructor does not throw an exception
     * when creating a Tube object with valid parameters.
     *this test for: {@link geometries.Tube#Tube(double, Ray)}
     */
    @Test
    void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        assertDoesNotThrow(() -> new Tube(5.7, new Ray(new Vector(1, 2, 3), new Point(4, 5, 6)))
                , "ERROR: can't construct a regular cylinder");
    }

    /**
     * test case for the getNormal method of the Tube class.
     * This test case verifies that the getNormal method returns the correct normal vector
     * this test for: {@link geometries.Tube#getNormal(Point)}
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // Test 01: for a regular point on the tube's surface
        assertEquals(new Vector(-0.25, -2.99, 0).normalize(),tube.getNormal(new Point(-0.25,-2.99,2.8)),
                "normal for tube doesn't work correctly");

        // =============== Boundary Values Tests ==================
        // Test 01: for a point around the tube's axis's head
        assertEquals(new Vector(-0.75,-2.91,0).normalize(),tube.getNormal(new Point(-0.75, -2.91, 2)),
                "normal for points that are the most adjacent to the tube's axis's head doesn't work correctly");
    }
    /**
     * Test method for {@link Tube#findIntersections(Ray)}.
     * This test checks the intersection points between a ray and the tube.
     * The expected intersection points are calculated based on the ray's direction and the tube's axis.
     */
    @Test
    void testFindIntersections() {
        // ============ Equivalence Partitions Tests ==============
        //test 01: Ray's line is outside the tube (0 points)

        //test 02: Ray starts inside the tube (1 point)

        //test 03: Ray starts outside the tube and goes through it (2 points)

        //test 04: Ray's line is inside the tube and ray starts outside the tube and goes the other way (0 points)

        // =========== Boundary Values Tests =====================
        // **** Group 1: Ray's line crosses the tube (but not the center)

        //test 01: Ray starts at the periphery of the tube and goes outside (0 point)
        //test 02: Ray starts at the periphery of the tube and goes inside (1 point)
        // **** Group 2: Ray's line goes through the tube's axis

        //test 01: Ray starts at the tube's axis and goes outside (1 point)
        //test 02: Ray starts at the periphery of the tube and goes through its axis (1 point)
        //test 03: Ray starts at the periphery of the tube and goes outside (0 point)
        //test 04: Ray starts inside the tube and goes through its axis outside (1 point)
        //test 05: Ray starts outside the tube and goes through its axis inside (2 points)
        //test 06: Ray starts outside the tube and goes the opposite direction (0 point)

        // **** Group 3: Ray's line is tangent to the sphere (all tests 0 points)
        //test 01: Ray starts before the axis and goes to the tangent point
        //test 02: Ray starts at the tangent point and continues
        //test 03: Ray starts after the tangent point
        // **** Group 4: The vector between the ray's head and the tube's axis's head is orthogonal to the ray's direction
        //test 01: Ray starts outside the tube(0 points)
        //test 02: Ray starts inside the tube(1 point)
    }
}