package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Cylinder class
 */
class CylinderTest {
    /**
     * test cases for testing the Cylinder class
     */
    Cylinder cylinder = new Cylinder(3, new Ray(new Vector(0,0,1), new Point(0,0, 2)), 2);
    Point p1 = new Point(-0.25,-2.99,2.8);
    double xy = Math.sqrt(2)/2*3;

    /**
     * Test method for {@link geometries.Cylinder#Cylinder(double, Ray, double)}
     */
    @Test
    void testConstructor(){
        //// ============ Equivalence Partitions Tests ==============
        //test 01: valid cylinder
        assertDoesNotThrow(()->new Cylinder(5.7, new Ray(new Vector(1,2,3),new Point(4,5,6)),
                3), "ERROR: can't construct a regular cylinder");
    }
    /**
     * Test method for {@link geometries.Cylinder#getNormal(Point)}
     * tests the normal vector of the cylinder
     */
    @Test
    void testGetNormal() {
        //// ============ Equivalence Partitions Tests ==============
        //test 01: point on the round surface
        assertEquals(new Vector(-0.25, -2.99, 0).normalize(), cylinder.getNormal(p1),
                "ERROR: wrong normal for point on round surface");
        //test 02: point on base 1
        assertEquals(new Vector(0,0,0.8).normalize(), cylinder.getNormal(new Point(-1.47,0.68,4)),
                "ERROR: wrong normal for point on base (not center)");
        //test 03: point on base 2
        assertEquals(new Vector(0,0,-0.8).normalize(), cylinder.getNormal(new Point(1.05,0.38,2)),
                "ERROR: wrong normal for point on base (not center)");

        //// ============ Boundary Values Tests =====================
        //test 01: point on the center of base 1
        assertEquals(new Vector(0,0,0.8).normalize(), cylinder.getNormal(new Point(0,0,4)),
                "ERROR: wrong normal for center of base");
        //test 02: point on the center of base 2
        assertEquals(new Vector(0,0,-0.8).normalize(), cylinder.getNormal(new Point(0,0,2)),
                "ERROR: wrong normal for center of base");

        //test 03: point on the edge of base 1
        assertEquals(new Vector(0,0,-1).normalize(), cylinder.getNormal(new Point(-0.75,-2.91,2)),
                "ERROR: wrong normal for base edge");
        //test 04: point on the edge of base 2
        assertEquals(new Vector(0,0,1).normalize(), cylinder.getNormal(new Point(-3,-0.13,4)),
                "ERROR: wrong normal for base edge");

    }
    /**
     * Test method for {@link geometries.Cylinder#findIntersections(Ray)}
     * tests the intersections of the cylinder
     */

    @Test
    void testFindIntersections() {
        // ============ Equivalence Partitions Tests ==============
        //test 01: ray intersects the round surface and then the base(2 points)
        assertEquals(List.of(new Point(xy,xy,3), new Point(1,1,4)),
                cylinder.findIntersections(new Ray(new Vector(1-xy,1-xy,1), new Point(2*xy-1,2*xy-1,2))),
                "ERROR: wrong intersection points");
        //test 02: ray intersects the base and then the round surface(2 points)
        assertEquals(List.of(new Point(xy,xy,3), new Point(1,1,4)),
                cylinder.findIntersections(new Ray(new Vector(xy-1,xy-1,-1), new Point(2-xy,2-xy,5))),
                "ERROR: wrong intersection points");
        //test 03: ray intersects the round surface twice(2 points)
        assertEquals(List.of(new Point(xy,xy,3), new Point(-xy,-xy,3)),
                cylinder.findIntersections(new Ray(new Vector(-1,-1,0), new Point(5,5,3))),
                "ERROR: wrong intersection points");
        //test 04: ray intersects the bases twice(2 points)
        assertEquals(List.of(new Point(1,0,2), new Point(0,1,4)),
                cylinder.findIntersections(new Ray(new Vector(1,-1,-2), new Point(-1,2,6))),
                "ERROR: wrong intersection points");
        //test 05: ray doesn't intersect the cylinder but its line does(0 points)
        assertNull(cylinder.findIntersections(new Ray(new Vector(1,1,0), new Point(5,5,3))),
                "ERROR: there must be 0 points(null)");
        //test 06: ray starts inside the cylinder and goes out through the base(1 point)
        assertEquals(List.of(new Point(1,0,2)),
                cylinder.findIntersections(new Ray(new Vector(1,-1,-1), new Point(0,1,3))),
                "ERROR: wrong intersection points");

        // =========== Boundary Values Tests =====================
        //test 01: ray intersects the perpendicular line of the cylinder(0 point)
        assertNull(cylinder.findIntersections(new Ray(new Vector(0,1,1), new Point(0,2,3))),
                "ERROR: wrong intersection points");
        //test 02: ray intersects the cylinder at the center of the base(1 point)
        assertEquals(List.of(new Point(0,0,4)),
                cylinder.findIntersections(new Ray(new Vector(0,1,1), new Point(0,-1,3))),
                "ERROR: wrong intersection points");
        //test 03: ray goes the same direction as the cylinder's axis and intersects the bases(2 points)
        assertEquals(List.of(new Point(0,0,2), new Point(0,0,4)),
                cylinder.findIntersections(new Ray(new Vector(0,0,1), new Point(0,0,-1))),
                "ERROR: wrong intersection points");

        // rays that start on top base
        //test 04: ray starts directly at the top base and goes out (not center) (0 point)
        assertNull(cylinder.findIntersections(new Ray(new Vector(0,1,1), new Point(0,1,4))),
                "ERROR: there must be 0 points(null)");
        //test 05: ray starts at the center of top base and goes out(0 point)
        assertNull(cylinder.findIntersections(new Ray(new Vector(0,1,1), new Point(0,0,4))),
                "ERROR: there must be 0 points(null)");
        //test 06: ray starts at top base and goes out through the top base surface(0 point)
        assertNull(cylinder.findIntersections(new Ray(new Vector(1,1,0), new Point(0,1,4))),
                "ERROR: there must be 0 points(null)");
        //test 07: ray starts at top base and goes out through round surface(1 point)
        assertEquals(List.of(new Point(0,3,3)),
                cylinder.findIntersections(new Ray(new Vector(0,1,-1), new Point(0,2,4))),
                "ERROR: wrong intersection points");
        //test 08: ray starts at the middle of top base and goes out through th bottom base(1 point)
        assertEquals(List.of(new Point(0,0,2)),
                cylinder.findIntersections(new Ray(new Vector(0,0,-1), new Point(0,0,4))),
                "ERROR: wrong intersection points");

        // rays that start on bottom base
        //test 09: ray starts directly at the bottom base and goes out (not center) (0 point)
        assertNull(cylinder.findIntersections(new Ray(new Vector(0,-1,-1), new Point(0,1,2))),
                "ERROR: there must be 0 points(null)");
        //test 10: ray starts at the center of bottom base and goes out(0 point)
        assertNull(cylinder.findIntersections(new Ray(new Vector(0,-1,-1), new Point(0,0,2))),
                "ERROR: there must be 0 points(null)");
        //test 11: ray starts at bottom base and goes out through the bottom base surface(0 point)
        assertNull(cylinder.findIntersections(new Ray(new Vector(1,1,0), new Point(0,1,2))),
                "ERROR: there must be 0 points(null)");
        //test 12: ray starts at bottom base and goes out through round surface(1 point)
        assertEquals(List.of(new Point(0,3,3)),
                cylinder.findIntersections(new Ray(new Vector(0,1,1), new Point(0,2,2))),
                "ERROR: wrong intersection points");
        //test 13: ray starts at the middle of bottom base and goes out through top base(1 point)
        assertEquals(List.of(new Point(0,0,4)),
                cylinder.findIntersections(new Ray(new Vector(0,0,1), new Point(0,0,2))),
                "ERROR: wrong intersection points");
    }

    @Test
    void testFindIntersectionsWithDistance() {
        // ============ Equivalence Partitions Tests ==============
        //test 01: ray intersects the round surface and then the base(2 points)
        assertEquals(List.of(new Point(3, 0,3), new Point(2,0,4)),
                cylinder.findIntersections(new Ray(new Vector(1,0,-1), new Point(0,0,6)), 7),
                "ERROR: wrong intersection points");
        //test 02: ray intersects the round surface and then the base but maxDistance leaves second intersection point out of range(2 points)
        assertEquals(List.of(new Point(2,0,4)),
                cylinder.findIntersections(new Ray(new Vector(1,0,-1), new Point(0,0,6)), 3),
                "ERROR: wrong intersection points");
        //test 03: ray intersects the round surface and then the base but maxDistance leaves intersection points out of range(2 points)
        assertNull(cylinder.findIntersections(new Ray(new Vector(1,0,-1), new Point(0,0,6)), 2),
                "ERROR: wrong intersection points");

        //test 04: ray intersects the round surface twice(2 points)
        assertEquals(List.of(new Point(-3, 0,3), new Point(3,0,3)),
                cylinder.findIntersections(new Ray(new Vector(1,0,0), new Point(-4,0,3)), 10),
                "ERROR: wrong intersection points");
        //test 05: ray intersects the round surface twice but maxDistance leaves second point out of range(2 points)
        assertEquals(List.of(new Point(-3, 0,3)),
                cylinder.findIntersections(new Ray(new Vector(1,0,0), new Point(-4,0,3)),6),
                "ERROR: wrong intersection points");
        //test 06: ray intersects the round surface twice but maxDistance leaves second point out of range(2 points)
        assertNull(cylinder.findIntersections(new Ray(new Vector(1,0,0), new Point(-4,0,3)),0.5),
                "ERROR: wrong intersection points");
        //test 07: ray intersects the bases twice(2 points)
        assertEquals(List.of(new Point(1,0,2), new Point(0,1,4)),
                cylinder.findIntersections(new Ray(new Vector(1,-1,-2), new Point(-1,2,6)), 10),
                "ERROR: wrong intersection points");
        //test 08: ray intersects the bases twice but maxDistance leaves second point out of range
        assertEquals(List.of(new Point(0,1,4)),
                cylinder.findIntersections(new Ray(new Vector(1,-1,-2), new Point(-1,2,6)), 3),
                "ERROR: wrong intersection points");
        //test 09: ray intersects the bases twice but maxDistance leaves points out of range
        assertNull(cylinder.findIntersections(new Ray(new Vector(1,-1,-2), new Point(-1,2,6)), 2),
                "ERROR: wrong intersection points");

        //test 10: ray starts inside the cylinder and goes out through the base(1 point)
        assertEquals(List.of(new Point(1,0,2)),
                cylinder.findIntersections(new Ray(new Vector(1,-1,-1), new Point(0,1,3)), 5),
                "ERROR: wrong intersection points");
        //test 11: ray starts inside the cylinder and goes out through the base but maxDistance leaves intersection point out of range
        assertNull(cylinder.findIntersections(new Ray(new Vector(1,-1,-1), new Point(0,1,3)), 1),
                "ERROR: wrong intersection points");

        // =========== Boundary Values Tests =====================
        //test 01: ray intersects the cylinder at the center of the base(1 point)
        assertEquals(List.of(new Point(0,0,4)),
                cylinder.findIntersections(new Ray(new Vector(0,1,1), new Point(0,-1,3)), 1.5),
                "ERROR: wrong intersection points");
        //test 02: ray intersects the cylinder at the center of the base but maxDistance leaves point out of range
        assertNull(cylinder.findIntersections(new Ray(new Vector(0,1,1), new Point(0,-1,3)), 1),
                "ERROR: wrong intersection points");
        //test 03: ray goes the same direction as the cylinder's axis and intersects the bases(2 points)
        assertEquals(List.of(new Point(0,0,2), new Point(0,0,4)),
                cylinder.findIntersections(new Ray(new Vector(0,0,1), new Point(0,0,-1)), 10),
                "ERROR: wrong intersection points");
        //test 04: ray goes the same direction as the cylinder's axis and intersects the bases but maxDistance leaves second point out of range
        assertEquals(List.of(new Point(0,0,2)),
                cylinder.findIntersections(new Ray(new Vector(0,0,1), new Point(0,0,-1)), 3),
                "ERROR: wrong intersection points");

        // rays that start on top base
        //test 05: ray starts at top base and goes out through round surface(1 point)
        assertEquals(List.of(new Point(0,3,3)),
                cylinder.findIntersections(new Ray(new Vector(0,1,-1), new Point(0,2,4)), 1.5),
                "ERROR: wrong intersection points");
        //test 06: ray starts at top base and goes out through round surface but maxDistance leaves point out of range
        assertNull(cylinder.findIntersections(new Ray(new Vector(0,1,-1), new Point(0,2,4)), 1),
                "ERROR: wrong intersection points");
        //test 07: ray starts at the middle of top base and goes out through the bottom base(1 point)
        assertEquals(List.of(new Point(0,0,2)),
                cylinder.findIntersections(new Ray(new Vector(0,0,-1), new Point(0,0,4)), 2),
                "ERROR: wrong intersection points");
        //test 08: ray starts at the middle of top base and goes out through the bottom base but point out of range
        assertNull(cylinder.findIntersections(new Ray(new Vector(0,0,-1), new Point(0,0,4)), 1.99),
                "ERROR: wrong intersection points");

    }
}
