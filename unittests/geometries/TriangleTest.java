package geometries;

import primitives.Point;
import primitives.Vector;
import org.junit.jupiter.api.Test;
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
        assertEquals(new Vector(1,0,1), triangle.getNormal(new Point(0,0,1)),
                "ERROR: didn't get correct normal of triangle");
    }
}