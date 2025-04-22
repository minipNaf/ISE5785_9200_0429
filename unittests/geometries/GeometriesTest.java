package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;
import static org.junit.jupiter.api.Assertions.*;

class GeometriesTest {

    Geometries geometries = new Geometries(
            new Polygon(
                    new Point(-2, 2, 0),
                    new Point(-2, -2, 0),
                    new Point(2, -2, 0),
                    new Point(2,2 , 0)
            ),
            new Sphere(
                    2,
                    new Point(0, 0, -5)

            ),
            new Triangle(
                    new Point(0, 0, -14),
                    new Point(1, 0, -13),
                    new Point(0, 1, -15)
            )
    );
    /**
     * Test for findIntersections method
     * This test checks the amount of the returned intersection points
     * Test
     */
    @Test
    void testFindIntersections() {
        // ============ Equivalence Partitions Tests ==============
        // Test 01: some geometries are intersected by the ray
        assertEquals(3, geometries.findIntersections(new Ray(new Vector(-0.5,-1,-3),
                new Point(0.9, 1.8, 2.4))).size(),
                "The number of intersection points is not correct");

        // =========== Boundary Values Tests =====================
        // Test 01: no geometries
        assertNull(new Geometries().findIntersections(new Ray(new Vector(1,1,1),new Point(1,1,1))),
                "No intersections should be found");
        // Test 02: no intersections
        assertNull(geometries.findIntersections(new Ray(new Vector(1,1,1),new Point(1,1,1))),
                "No intersections should be found");
        // Test 03: only one geometry are intersected by the ray
        assertEquals(1, geometries.findIntersections(new Ray(new Vector(-5.59,0.32,-13.91),
                new Point(6, 0, 0))).size(),
                "The number of intersection points is not correct");
        // Test 04: all geometries are intersected by the ray
        assertEquals(4, geometries.findIntersections(new Ray(new Vector(0,0,-1),
                new Point(0.2, 0.2, 5))).size(),
                "The number of intersection points is not correct");
    }
}