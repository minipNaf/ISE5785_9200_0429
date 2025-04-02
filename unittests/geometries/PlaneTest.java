package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

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

    Point p4 = new Point(-1, 0,0);
    Point p5 = new Point(1,0,0);
    Point p6 = new Point(4,0,0);

    /**
     * Test method for {@link geometries.Plane#Plane(Point, Point, Point)}
     * tests the constructor of the Plane class
     * tests the normal vector of the plane
     */
    @Test
    void testConstructorPoints(){
        //// ============ Equivalence Partitions Tests ==============
        assertEquals(0, p.getNormal().dotProduct(vecOnPlane1), 0.0001,
                "ERROR: normal not orthogonal to vector on plane");
        //// ============ Equivalence Partitions Tests ==============
        assertEquals(0, p.getNormal().dotProduct(VecOnPlane2), 0.0001,
                "ERROR: normal not orthogonal to vector on plane");
        //// ============ Equivalence Partitions Tests ==============
        assertEquals(1, p.getNormal().length(), 0.0001,
                "ERROR: normal doesn't have length of one");
        //// ============ Boundary Values Tests =====================
        assertThrows(IllegalArgumentException.class, ()->new Plane(p1,p1,p3),
                "ERROR: first point and second point are the same");
        //// ============ Boundary Values Tests =====================
        assertThrows(IllegalArgumentException.class, ()->new Plane(p1,p2,p2),
                "ERROR: second point and third point are the same");
        //// ============ Boundary Values Tests =====================

        assertThrows(IllegalArgumentException.class, ()->new Plane(p1,p2,p1),
                "ERROR: first point and third point are the same");
        //// ============ Boundary Values Tests =====================

        assertThrows(IllegalArgumentException.class, ()->new Plane(p1,p1,p1),
                "ERROR: all points are the same");
        //// ============ Boundary Values Tests =====================

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
        assertEquals(new Vector(0,1,0), p.getNormal(new Point(-3,0,5)),
                "ERROR: didn't get correct vector of plane");
    }
}