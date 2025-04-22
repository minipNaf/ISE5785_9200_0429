package geometries;
import java.util.List;

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
    Point p1 = new Point(4,4,5);
    Point p2 = new Point(4,4,1);
    Point p3 = new Point(0,1,3);
    Point p4 = new Point(0,1,-3);
    double xy = Math.sqrt(2)/2*3;
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
        // Group 1: Ray starts above the center of the tube's axis

        // Test 01: Ray starts inside the tube and goes above(1 point)

        assertEquals(List.of(new Point(0,3,5)),
                tube.findIntersections(new Ray(new Vector(0,1,1), p3)),
                "ERROR: the intersection point is not correct");

        // Test 02: Ray starts inside the tube and goes below(1 points)
        assertEquals(List.of(new Point(0,3,1)),
                tube.findIntersections(new Ray(new Vector(0, 1,-1), p3)));
        // Test 03: Ray starts outside the tube and goes inside(2 points)
        assertEquals(List.of(new Point(xy,xy,5-0.1*(4-xy)),new Point(-xy,-xy,5-0.1*(4+xy))),
                tube.findIntersections(new Ray(new Vector(-1,-1,-0.1), p1)),
                "ERROR: the intersection point is not correct");

        // Test 04: Ray starts outside the tube and goes the other way(0 points)
        assertNull(tube.findIntersections(new Ray(new Vector(1,0,-5), p1)),
                "ERROR: the are supposed to be no intersection points");
        // Test 05: Ray intersection points are before the tube's axis(2 points)
        assertEquals(List.of(new Point(xy,xy,5-20*(4-xy)),new Point(-xy,-xy,5-20*(4+xy))),
                tube.findIntersections(new Ray(new Vector(-1,-1,-20), p1)),
                "ERROR: the intersection point is not correct");
        // Test 06: One intersection point is before the tube's axis and the other is after(2 points)
        assertEquals(List.of(new Point(xy,xy,3.5),new Point(-xy,-xy,5-1.5*(4+xy)/(4-xy))),
                tube.findIntersections(new Ray(new Vector(-1,-1,-1.5/(4-xy)), p1)),
                "ERROR: the intersection point is not correct");
        // Group 2: Ray starts below the center of the tube's axis
        // Test 07: Ray starts inside the tube and goes below(1 point)

        assertEquals(List.of(new Point(0,3,1)),
                tube.findIntersections(new Ray(new Vector(0,1,-1), p4)),
                "ERROR: the intersection point is not correct");
        // Test 08: Ray starts inside the tube and goes above(1 points)
        assertEquals(List.of(new Point(0,3,5)),
                tube.findIntersections(new Ray(new Vector(0, 1,1), p4)));
        // Test 09: Ray starts outside the tube and goes inside(2 points)

        assertEquals(List.of(new Point(xy,xy,-3+0.1*(4-xy)),new Point(-xy,-xy,-3+0.1*(4+xy))),
                tube.findIntersections(new Ray(new Vector(-1,-1,0.1), p2)),
                "ERROR: the intersection point is not correct");
        // Test 10: Ray starts outside the tube and goes the other way(0 points)
        assertNull(tube.findIntersections(new Ray(new Vector(1,0,-5), p2)),
                "ERROR: the are supposed to be no intersection points");
        // Test 11: Ray intersection points are after the tube's axis(2 points)
        assertEquals(List.of(new Point(xy,xy,-3+20*(4-xy)),new Point(-xy,-xy,-3+20*(4+xy))),
                tube.findIntersections(new Ray(new Vector(-1,-1,20), p2)),
                "ERROR: the intersection point is not correct");
        // Test 12: One intersection point is before the tube's axis and the other is after(2 points)
        assertEquals(List.of(new Point(xy,xy,-0.5),new Point(-xy,-xy,2-3.5/(4+xy)/(4-xy))),
                tube.findIntersections(new Ray(new Vector(-1,-1,1.5/(4-xy)), p2)),
                "ERROR: the intersection point is not correct");
        // =========== Equivalence Partitions Tests ==============
        // Group 1: ray's line on the surface of the tube(0 points)
        // Test 01: ray starts before the tangent point
        // Test 02: ray starts after the tangent point
        // Test 03: ray starts at the tangent point
        // Group 2: ray's line is at the periphery around the tube's axis's head(0 points)
        // Test 04: ray starts before the tangent point
        // Test 05: ray starts after the tangent point
        // Test 06: ray starts at the tangent point
        // Group 3: ray's line is part of the tube's surface(0 points)


    }
}