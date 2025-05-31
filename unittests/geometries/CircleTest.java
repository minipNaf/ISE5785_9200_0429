package geometries;
import java.util.List;
import org.junit.jupiter.api.Test;
import primitives.*;
import geometries.Sphere;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the circle class.
 * This class contains test cases to verify the functionality of the circle class,
 * including its constructor and the getNormal method.
 */
class CircleTest {
    /**
     * Test case for the Sphere class.
     * This test case creates a Sphere object and verifies its constructor and getNormal method.
     */
    Circle circle = new Circle(new Point(1,0,0), 1, new Vector(1,0,0));

    /**
     * Test case for the constructor of the Circle class.
     * This test case verifies that the constructor does not throw an exception
     * when creating a Circle object with valid parameters.
     * this test for: {@link geometries.Sphere#Sphere(double, Point)}
     */
    @Test
    void testConstructor(){
        // ============ Equivalence Partitions Tests ==============
        //test 01: check case of constructing a regular cirlce
        assertDoesNotThrow(()->new Circle(new Point(3,6,3), 3.68, new Vector(1,2,3)),
                "ERROR: can't construct a regular circle");
    }


    /**
     * Test method for {@link Circle#getNormal(Point)}.
     * This test checks the normal vector at a specific point on the circle's surface.
     * The expected normal vector is calculated based on the circle's center and the given point.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        //test 01: check case of getting the normal vector at a point on the circle's surface
        assertEquals(new Vector(1,0,0),circle.getNormal(new Point(1,1,0)),
                "ERROR: getNormal() for circle doesn't work correctly");
    }

    /*
     * Test method for {@link Sphere#findIntersections(Ray)}.
     * This test checks the intersection points between a ray and the circle.
     * The expected intersection points are calculated based on the ray's direction and the circle's center.
     */
    @Test
    void testFindIntersections() {
        // ============ Equivalence Partitions Tests ==============
        // all tests are looking at the circle as two dimensional geometry in the plane
        //test 01: Ray's line is outside the circle (0 points)
        assertNull(circle.findIntersections(new Ray(new Vector(0, 1, 0), new Point(2, 0, 0))),
                "ERROR 1 1: there must be 0 points(null)");

        //test 02: Ray starts outside the circle and goes through it (1 points)
        assertEquals(List.of(new Point(1,0,-0.5)), //end of list
                circle.findIntersections(new Ray(new Vector(-1,0,0), new Point(2,0,-0.5))),
                "ERROR 1 3: incorrect intersection points");

        //test 03: Ray's line is inside the circle and ray starts outside the circle and goes the other way (0 points)
        assertNull(circle.findIntersections(new Ray(new Vector(1,0,0), new Point(2,0,0))),
                "ERROR 1 4: there must be 0 points(null)");

        // =========== Boundary Values Tests =====================
        // **** Group 1: Ray's line crosses the circle (but not the center)
        //test 01: Ray starts outside the circle and goes through the center inside (1 points)
        assertEquals(List.of(new Point(1,0,0)),
                circle.findIntersections(new Ray(new Vector(-1,0,0), new Point(2,0,0))),
                "ERROR 3 5: incorrect intersection points");

        // **** Group 2: The vector between the ray's head and the circle's center is orthogonal to the ray's direction
        //test 01: Ray starts outside the circle(0 points)
        assertNull(circle.findIntersections(new Ray(new Vector(0,1,0), new Point(2,0,0))),
                "ERROR 5 1: there must be 0 points(null)");
    }

    @Test
    void testIntersectionWithDistance() {
        // ============ Equivalence Partitions Tests ==============
        //test 01: Ray starts outside of circle and goes through it, but the maxDistance leaves intersection point out of range (0 point)
        assertNull(circle.findIntersections((new Ray(new Vector(-1,0,0), new Point(2,0,0))), 0.9),
                "ERROR: there must be 0 points(null)");
        //test 02: Ray starts outside of circle and goes through it, but the maxDistance leaves intersection point in range (1 point)
        assertEquals(List.of(new Point(1, 0, 0)),
                circle.findIntersections((new Ray(new Vector(-1,0,0), new Point(2,0,0))), 1),
                "ERROR: incorrect intersection point");
        //test 03: Ray starts outside of circle and doesn't go through it, though it's negative direction goes through it (0 point)
        assertNull(circle.findIntersections(new Ray(new Vector(1,0,0), new Point(2,0,0)), 2),
                "ERROR: there must be 0 points(null)");
        //test 04: Ray starts outside of circle and neither it nor its negative direction goes through it (0 point)
        assertNull(circle.findIntersections(new Ray(new Vector(0,1,0), new Point(2,0,0)), 2),
                "ERROR: there must be 0 points(null)");

    }
}