package geometries;

import primitives.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Unit tests for geometries.Triangle class.
 *
 * @author Naftali Vilner
 */
class TriangleTest {

    /**
     * test case for triangle's tests.
     */
    Point p1 = new Point(3,0,0);
    Triangle triangle = new Triangle(
            new Point(0,-2,0),
            new Point(0,0,3),
            new Point(0,2,0));

    /**
     *
     * test case for triangle's constructor, there shouldn't be any exception
     * this test for: {@link geometries.Triangle#Triangle(Point, Point, Point)}
     */
    @Test
    void testConstructor(){
        // ============ Equivalence Partitions Tests ==============
        //test 01: check case of constructing a regular triangle
        assertDoesNotThrow(()->new Triangle(new Point(7,-2,2),
                new Point(5,7,1),
                new Point(1.5,8,8)),
                "ERROR: can't construct a regular triangle");
    }

    /**
     *
     * test case for getNormal method of triangle class.
     * This test case verifies that the getNormal method returns the correct normal vector
     * for a point on the triangle's surface.
     * The expected normal vector is calculated based on the triangle's vertices.
     * this test for: {@link geometries.Triangle#getNormal(Point)}
     */
    @Test
    void testGetNormal(){
        // ============ Equivalence Partitions Tests ==============
        //test 01: check case of getting the normal vector at a point on the triangle's surface
        assertEquals(new Vector(-1,0,0), triangle.getNormal(new Point(0,0,1)),
                "ERROR: didn't get correct normal of triangle");
    }

    @Test
    void testFindIntersections() {
        // ============ Equivalence Partitions Tests ==============
        //test 01: check case of ray intersecting the triangle
        assertEquals(List.of(new Point(0,0,2)),
                triangle.findIntersections(new Ray(new Vector(-3,0,2), p1)),
                "ERROR: wrong intersection point");
        //test 02: check case of itersection the plain of the triangle against edge(0 points)
        assertNull(triangle.findIntersections(new Ray(new Vector(-3,1,2), p1)),
                "ERROR: there must be 0 points(null)");
        //test 03: check case of intersection the plain of the triangle against vertex (0 points)
        assertNull(triangle.findIntersections(new Ray(new Vector(-3,2.86,-0.57), p1)),
                "ERROR: there must be 0 points(null)");
        // ============ Boundary Values Tests =====================
        //test 01: check case of intersecting a triangle's edge(0 points)
        assertNull(triangle.findIntersections(new Ray(new Vector(-3,1,0), p1)),
                "ERROR: there must be 0 points(null)");
        //test 02: check case of intersecting a triangle's vertex(0 points)
        assertNull(triangle.findIntersections(new Ray(new Vector(-3,2,0), p1)),
                "ERROR: there must be 0 points(null)");
        //test 03: check case of intersecting the continuation of a triangle's edge(0 points)
        assertNull(triangle.findIntersections(new Ray(new Vector(-3,4,-3), p1)),
                "ERROR: there must be 0 points(null)");
    }

    @Test
    void testIntersectionWithDistance() {
        // ============ Equivalence Partitions Tests ==============
        //test 01: check case of ray intersecting the triangle
        assertEquals(List.of(new Point(0,0,2)),
                triangle.findIntersections(new Ray(new Vector(-3,0,2), p1), 5),
                "ERROR: wrong intersection point");
        //test 02: check case of ray intersecting the triangle but the distance is too small (0 points)
        assertNull(triangle.findIntersections(new Ray(new Vector(-3,0,2), p1), 1),
                "ERROR: there must be 0 points(null)");
        //============ Boundary Values Tests =====================
        //test 01: check case of ray intersecting the triangle edge (0 points)
        assertNull(triangle.findIntersections(new Ray(new Vector(-3,4,-3), p1), 1),
                "ERROR: there must be 0 points(null)");
    }
}