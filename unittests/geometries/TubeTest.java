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
        // Test for a regular point on the tube's surface
        assertEquals(new Vector(-0.25, -2.99, 0).normalize(),tube.getNormal(new Point(-0.25,-2.99,2.8)),
                "normal for tube doesn't work correctly");
        // =============== Boundary Values Tests ==================
        // Test for a point around the tube's axis's head
        assertEquals(new Vector(-0.75,-2.91,0).normalize(),tube.getNormal(new Point(-0.75, -2.91, 2)),
                "normal for points that are the most adjacent to the tube's axis's head doesn't work correctly");
    }
}