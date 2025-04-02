package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

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

    /**
     * Test method for {@link geometries.Cylinder#Cylinder(double, Ray, double)}
     */
    @Test
    void testConstructor(){
        //// ============ Equivalence Partitions Tests ==============
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
        assertEquals(new Vector(-0.25, -2.99, 0).normalize(), cylinder.getNormal(p1),
                "ERROR: wrong normal for point on round surface");
        //// ============ Equivalence Partitions Tests ==============

        assertEquals(new Vector(0,0,0.8).normalize(), cylinder.getNormal(new Point(-1.47,0.68,4)),
                "ERROR: wrong normal for point on base (not center)");
        //// ============ Equivalence Partitions Tests ==============

        assertEquals(new Vector(0,0,-0.8).normalize(), cylinder.getNormal(new Point(1.05,0.38,2)),
                "ERROR: wrong normal for point on base (not center)");

        //// ============ Boundary Values Tests =====================
        assertEquals(new Vector(0,0,0.8).normalize(), cylinder.getNormal(new Point(0,0,4)),
                "ERROR: wrong normal for center of base");
        //// ============ Boundary Values Tests =====================

        assertEquals(new Vector(0,0,-0.8).normalize(), cylinder.getNormal(new Point(0,0,2)),
                "ERROR: wrong normal for center of base");

        //// ============ Boundary Values Tests =====================

        assertEquals(new Vector(0,0,-1).normalize(), cylinder.getNormal(new Point(-0.75,-2.91,2)),
                "ERROR: wrong normal for base edge");
        //// ============ Boundary Values Tests =====================

        assertEquals(new Vector(0,0,1).normalize(), cylinder.getNormal(new Point(-3,-0.13,4)),
                "ERROR: wrong normal for base edge");

    }
}
