package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class CylinderTest {
    Cylinder cylinder = new Cylinder(3, new Ray(new Vector(0,0,1), new Point(0,0, 2)), 2);
    Point p1 = new Point(-0.25,-2.99,2.8);

    @Test
    void testConstructor(){
        assertDoesNotThrow(()->new Cylinder(5.7, new Ray(new Vector(1,2,3),new Point(4,5,6)),
        3), "ERROR: can't construct a regular cylinder");
    }
    @Test
    void testGetNormal() {
        assertEquals(new Vector(-0.25, -2.99, 0).normalize(), cylinder.getNormal(p1),
                "ERROR: wrong normal for point on round surface");
        assertEquals(new Vector(0,0,0.8).normalize(), cylinder.getNormal(new Point(-1.47,0.68,4)),
                "ERROR: wrong normal for point on base (not center)");
        assertEquals(new Vector(0,0,-0.8).normalize(), cylinder.getNormal(new Point(1.05,0.38,2)),
                "ERROR: wrong normal for point on base (not center)");


        assertEquals(new Vector(0,0,0.8).normalize(), cylinder.getNormal(new Point(0,0,4)),
                "ERROR: wrong normal for center of base");
        assertEquals(new Vector(0,0,0.8).normalize(), cylinder.getNormal(new Point(0,0,2)),
                "ERROR: wrong normal for center of base");

        assertEquals(new Vector(-0.75,-2.91,0).normalize(), cylinder.getNormal(new Point(-0.75,-2.91,2)),
                "ERROR: wrong normal for base edge");
        assertEquals(new Vector(-3,-0.13,0).normalize(), cylinder.getNormal(new Point(-3,-0.13,4)),
                "ERROR: wrong normal for base edge");
    }
}