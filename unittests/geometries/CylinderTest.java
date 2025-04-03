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
}
