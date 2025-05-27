package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
/**
 * Unit tests for geometries.Plane class
 */
class PlaneTest {
    /**
     * test cases for testing the Plane class
     */
    Point p1 = new Point(-2.83,0,0);
    Point p2 = new Point(0,0,3);
    Point p3 = new Point(3,0,0);
    Plane p = new Plane(p1, p2, p3);
    Vector vecOnPlane1 = p1.subtract(p2);
    Vector VecOnPlane2 = p3.subtract(p2);
    Ray r1 = new Ray( new Vector(-2.83,-1,0), new Point(0,1,0));
    Point p4 = new Point(-1, 0,0);
    Point p5 = new Point(1,0,0);
    Point p6 = new Point(4,0,0);
    Vector vec1 = new Vector(0,1,0);
    /**
     * Test method for {@link geometries.Plane#Plane(Point, Point, Point)}
     * tests the constructor of the Plane class
     * tests the normal vector of the plane
     */
    @Test
    void testConstructorPoints(){
        //// ============ Equivalence Partitions Tests ==============
        //test 01: check to see if the normal vector is orthogonal to one vector on the plane
        assertEquals(0, p.getNormal().dotProduct(vecOnPlane1), 0.0001,
                "ERROR: normal not orthogonal to vector on plane");
        //test 02: check to see if the normal vector is orthogonal to the other vector on the plane
        assertEquals(0, p.getNormal().dotProduct(VecOnPlane2), 0.0001,
                "ERROR: normal not orthogonal to vector on plane");
        //test 03: check to see if the normal vector has length of one
        assertEquals(1, p.getNormal().length(), 0.0001,
                "ERROR: normal doesn't have length of one");

        //// ============ Boundary Values Tests =====================
        //test 01: check case of first two points which are the same
        assertThrows(IllegalArgumentException.class, ()->new Plane(p1,p1,p3),
                "ERROR: first point and second point are the same");
        //test 02: check case of second and third points which are the same
        assertThrows(IllegalArgumentException.class, ()->new Plane(p1,p2,p2),
                "ERROR: second point and third point are the same");
        //test 03: check case of first and third points which are the same
        assertThrows(IllegalArgumentException.class, ()->new Plane(p1,p2,p1),
                "ERROR: first point and third point are the same");
        //test 04: check case of all three points which are the same
        assertThrows(IllegalArgumentException.class, ()->new Plane(p1,p1,p1),
                "ERROR: all points are the same");
        //test 05: check case of all three points which are on the same line
        assertThrows(IllegalArgumentException.class, ()->new Plane(p4,p5,p6),
                "ERROR: all points are on the same line");
    }
    /**
     * Test method for {@link geometries.Plane#getNormal(Point)}
     * tests the getNormal method of the Plane class
     * tests the normal vector of the plane
     */
    @Test
    void testGetNormal() {
        //// ============ Equivalence Partitions Tests ==============
        //test 01: check to see if the normal vector correct
        assertEquals(new Vector(0,1,0), p.getNormal(new Point(-3,0,5)),
                "ERROR: didn't get correct vector of plane");
    }

    @Test
    void testFindIntersections() {
        //// ============ Equivalence Partitions Tests ==============
        //test 01: ray starts out of plain and intersects the plane(1 point)
        assertEquals(List.of(p1), p.findIntersections(r1),
                "ERROR: didn't get correct intersection point");
        //test 02: ray starts out of plain and doesn't intersect the plane(0 point)
        assertNull(p.findIntersections(new Ray(vec1, new Point(0,1,0))),
                "ERROR: there must be 0 points(null)");

        //// ============ Boundary Values Tests =====================
        //Ray parallel to the plane
        //test 01: Ray outside the plane (0 points)
        assertNull(p.findIntersections(new Ray(new Vector(-3,0,0), new Point(0,1,0))),
                "ERROR: there must be 0 points(null)");
        //test 02: Ray on the plane (0 points)
        assertNull(p.findIntersections(new Ray(new Vector(-1,0,3), new Point(1,0,0))),
                "ERROR: there must be 0 points(null)");

        //Ray is orthogonal to the plane
        //test 03: Ray starts after the plane(0 points)
        assertNull(p.findIntersections(new Ray(vec1, new Point(-3,1,0))),
                "ERROR: there must be 0 points(null)");
        //test 04: Ray starts in the plane(0 point)
        assertNull(p.findIntersections(new Ray(vec1, p1)),
                "ERROR: there must be 0 points(null)");
        //test 05: Ray starts before the plane(1 point)
        assertEquals(List.of(p1), p.findIntersections(new Ray(vec1, new Point(-2.83,-3,0))),
                "ERROR: didn't get correct intersection point");
        //Ray neither parallel nor orthogonal to the plane
        //test 06: Ray begins at the plane(0 points)
        assertNull(p.findIntersections(new Ray(new Vector(2.83,3,0), p2)),
                "ERROR: there must be 0 points(null)");
        //test 07: Ray begins at the plane at the reference point(0 points)
        assertNull(p.findIntersections(new Ray(new Vector(2.83,3,0), p1)),
                "ERROR: there must be 0 points(null)");
    }

    @Test
    void testFindIntersectionsWithDistance(){
        //============== Equivalence Partitions Tests ==============
        //test 01: ray starts out of plain and intersects the plane(1 point)
        assertEquals(List.of(p1), p.findIntersections(r1, 10),
                "ERROR: didn't get correct intersection point");
        //test 02: ray starts out of plain and doesn't intersect the plane(0 point)
        assertNull(p.findIntersections(new Ray(new Vector(0,1,0), new Point(0,1,0)), 10),
                "ERROR: there must be 0 points(null)");
        //test 03: ray starts before the plane(1 point)
        assertEquals(List.of(p1), p.findIntersections(r1, 10),
                "ERROR: didn't get correct intersection point");
        //test 04: ray starts before the plane but maxDistance leaves intersection point out of range(0 point)
        assertNull(p.findIntersections(r1, 1),
                "ERROR: didn't get correct intersection point");

        //// ============ Boundary Values Tests =====================
        //Ray parallel to the plane
        //test 01: Ray outside the plane (0 points)
        assertNull(p.findIntersections(new Ray(new Vector(-3,0,0), new Point(0,1,0)), 10),
                "ERROR: there must be 0 points(null)");
        //test 02: Ray on the plane (0 points)
        assertNull(p.findIntersections(new Ray(new Vector(-1,0,3), new Point(1,0,0)), 10),
                "ERROR: there must be 0 points(null)");

        //Ray is orthogonal to the plane
        //test 03: Ray starts after the plane(0 points)
        assertNull(p.findIntersections(new Ray(vec1, new Point(-3,1,0)), 10),
                "ERROR: there must be 0 points(null)");
        //test 04: Ray starts in the plane(0 point)
        assertNull(p.findIntersections(new Ray(vec1, p1), 10),
                "ERROR: there must be 0 points(null)");
        //test 05: Ray starts before the plane(1 point)
        assertEquals(List.of(p1), p.findIntersections(new Ray(vec1, new Point(-2.83,-3,0)), 3),
                "ERROR: didn't get correct intersection point");
        //test 05: Ray starts before the plane but maxDistance leaves intersection point out of range(0 point)
        assertNull(p.findIntersections(new Ray(vec1, new Point(-2.83,-3,0)), 2.99),
                "ERROR: didn't get correct intersection point");

        //Ray neither parallel nor orthogonal to the plane
        //test 07: Ray begins at the plane(0 points)
        assertNull(p.findIntersections(new Ray(new Vector(2.83,3,0), p2)),
                "ERROR: there must be 0 points(null)");
        //test 08: Ray begins at the plane at the reference point(0 points)
        assertNull(p.findIntersections(new Ray(new Vector(2.83,3,0), p1)),
                "ERROR: there must be 0 points(null)");
    }
}