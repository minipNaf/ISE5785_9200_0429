package geometries;
import java.util.List;
import org.junit.jupiter.api.Test;
import primitives.*;
import geometries.Sphere;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Sphere class.
 * This class contains test cases to verify the functionality of the Sphere class,
 * including its constructor and the getNormal method.
 */
class SphereTest {
    /**
     * Test case for the Sphere class.
     * This test case creates a Sphere object and verifies its constructor and getNormal method.
     */
    Sphere sphere = new Sphere(1,new Point(1,0,0));

    /**
     * Test case for the constructor of the Sphere class.
     * This test case verifies that the constructor does not throw an exception
     * when creating a Sphere object with valid parameters.
     * this test for: {@link geometries.Sphere#Sphere(double, Point)}
     */
    @Test
    void testConstructor(){
        // ============ Equivalence Partitions Tests ==============
        //test 01: check case of constructing a regular sphere
        assertDoesNotThrow(()->new Sphere(3.68, new Point(3,6,3)),
                "ERROR: can't construct a regular sphere");
    }


    /**
     * Test method for {@link Sphere#getNormal(Point)}.
     * This test checks the normal vector at a specific point on the sphere's surface.
     * The expected normal vector is calculated based on the sphere's center and the given point.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        //test 01: check case of getting the normal vector at a point on the sphere's surface
        assertEquals(new Vector(0,1,0),sphere.getNormal(new Point(1,1,0)),
                "ERROR: getNormal() for sphere doesn't work correctly");
    }

    /*
     * Test method for {@link Sphere#findIntersections(Ray)}.
     * This test checks the intersection points between a ray and the sphere.
     * The expected intersection points are calculated based on the ray's direction and the sphere's center.
     */
    @Test
    void testFindIntersections() {
        // ============ Equivalence Partitions Tests ==============
        //test 01: Ray's line is outside the sphere (0 points)

        assertNull(sphere.findIntersections(new Ray(new Vector(0, 0, 1), new Point(1, 0, 2))),
                "ERROR 1 1: there must be 0 points(null)");
        //test 02: Ray starts inside the sphere (1 point)

        assertEquals(List.of(new Point(1,Math.sqrt(0.5),Math.sqrt(0.5))),
                sphere.findIntersections(new Ray(new Vector(0,0,1), new Point(1,Math.sqrt(0.5),0))),
                "ERROR 1 2: incorrect intersection point");
        //test 03: Ray starts outside the sphere and goes through it (2 points)

        assertEquals(List.of(new Point(1,Math.sqrt(0.5),Math.sqrt(0.5)),
                        new Point(1,-Math.sqrt(0.5),Math.sqrt(0.5))), //end of list
                sphere.findIntersections(new Ray(new Vector(0,1,0),
                        new Point(1,-8,Math.sqrt(0.5)))),
                "ERROR 1 3: incorrect intersection points");

        //test 04: Ray's line is in side the sphere and ray starts outside the sphere and goes the other way (0 points)

        assertNull(sphere.findIntersections(new Ray(new Vector(0,0,1), new Point(1,0,2))),
                "ERROR 1 4: there must be 0 points(null)");
        // =========== Boundary Values Tests =====================
        // **** Group 1: Ray's line crosses the sphere (but not the center)

        //test 01: Ray starts at the periphery of the sphere and goes outside (0 point)
        assertNull(sphere.findIntersections(new Ray(new Vector(0,0,1), new Point(1,1,0))),
                "ERROR 2 1: there must be 0 points(null)");
        //test 02: Ray starts at the periphery of the sphere and goes inside (1 point)

        assertEquals(List.of(new Point(1,-Math.sqrt(0.5),Math.sqrt(0.5))),
                sphere.findIntersections(new Ray(new Vector(0,-1,0),
                        new Point(1,Math.sqrt(0.5),Math.sqrt(0.5)))),
                "ERROR 2 2: incorrect intersection point");
        // **** Group 2: Ray's line goes through the center

        //test 01: Ray starts at the center of the sphere and goes outside (1 point)
        assertEquals(List.of(new Point(1,Math.sqrt(0.5),Math.sqrt(0.5))),
                sphere.findIntersections(new Ray(new Vector(0,1,1),
                        new Point(1,0,0))),
                "ERROR 3 1: incorrect intersection point");
        //test 02: Ray starts at the periphery of the sphere and goes through the center (1 point)
        assertEquals(List.of(new Point(1,Math.sqrt(0.5),Math.sqrt(0.5))),
                sphere.findIntersections(new Ray(new Vector(0,1,1),
                        new Point(1,-Math.sqrt(0.5),-Math.sqrt(0.5)))),
                "ERROR 3 2: incorrect intersection point");
        //test 03: Ray starts at the periphery of the sphere and goes outside (0 point)
        assertNull(sphere.findIntersections(new Ray(new Vector(1,1,1),
                        new Point(1,Math.sqrt(0.5),Math.sqrt(0.5)))),
                "ERROR 3 3: there must be 0 points(null)");
        //test 04: Ray starts inside the sphere and goes through the center outside (1 point)
        assertEquals(List.of(new Point(1,Math.sqrt(0.5),Math.sqrt(0.5))),
                sphere.findIntersections(new Ray(new Vector(0,1,1),
                        new Point(1,-0.5,-0.5))),
                "ERROR 3 4: incorrect intersection point");
        //test 05: Ray starts outside the sphere and goes through the center inside (2 points)
        assertEquals(List.of(new Point(1,Math.sqrt(0.5),Math.sqrt(0.5)),
                        new Point(1,-Math.sqrt(0.5),-Math.sqrt(0.5))),
                sphere.findIntersections(new Ray(new Vector(0,-1,-1),
                        new Point(1,2,2))),
                "ERROR 3 5: incorrect intersection points");
        //test 06: Ray starts outside of the sphere and goes the opposite direction (0 point)

        assertNull(sphere.findIntersections(new Ray(new Vector(0,1,1),
                        new Point(1,2,2))),
                "ERROR 3 6: there must be 0 points(null)");
        // **** Group 3: Ray's line is tangent to the sphere (all tests 0 points)
        //test 01: Ray starts before the sphere and goes to the tangent point
        assertNull(sphere.findIntersections(new Ray(new Vector(0,1,0),
                        new Point(1,-1,-1))),
                "ERROR 4 1: there must be 0 points(null)");
        //test 02: Ray starts at the tangent point and continues
        assertNull(sphere.findIntersections(new Ray(new Vector(0,1,0),
                        new Point(1,Math.sqrt(0.5),Math.sqrt(0.5)))),
                "ERROR 4 2: there must be 0 points(null)");
        //test 03: Ray starts after the tangent point
        assertNull(sphere.findIntersections(new Ray(new Vector(0,1,0),
                        new Point(1,2,2))),
                "ERROR 4 3: there must be 0 points(null)");
        // **** Group 4: The vector between the ray's head and the sphere's center is orthogonal to the ray's direction
        //test 01: Ray starts outside the sphere(0 points)
        assertNull(sphere.findIntersections(new Ray(new Vector(0,1,0),
                        new Point(1,0,2))),
                "ERROR 5 1: there must be 0 points(null)");
        //test 02: Ray starts inside the sphere(1 point)
        assertEquals(List.of(new Point(1,Math.sqrt(0.5),Math.sqrt(0.5))),
                sphere.findIntersections(new Ray(new Vector(0,1,0),
                        new Point(1,0,Math.sqrt(0.5)))),
                "ERROR 5 2: incorrect intersection point");


    }
}