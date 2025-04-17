package geometries;

import org.junit.jupiter.api.Test;
import  primitives.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PolygonTest {
    Polygon pentagon = new Polygon(
            new Point(0, -2, 0),
            new Point(0, 0, -2),
            new Point(0, 2, 0),
            new Point(0, 0, 3),
            new Point(0, -2, 1)
    );
    Point p1 = new Point(5, 0, 0);
    @Test
    void testFindIntersections() {
        // ============ Equivalence Partitions Tests ==============
        //test 01: check case of ray intersecting the pentagon
        assertEquals(List.of(new Point(0,0,2)),
                pentagon.findIntersections(new Ray(new Vector(-5,0,2), p1)),
                "ERROR: wrong intersection point");
        //test 02: check case of itersection the plain of the pentagon against edge(0 points)
        assertNull(pentagon.findIntersections(new Ray(new Vector(-5,-2.32,0.67), p1)),
                "ERROR: there must be 0 points(null)");
        //test 03: check case of intersection the plain of the pentagon against vertex (0 points)
        assertNull(pentagon.findIntersections(new Ray(new Vector(-5,-3,0), p1)),
                "ERROR: there must be 0 points(null)");
        // ============ Boundary Values Tests =====================
        //test 01: check case of intersecting a pentagon's edge(0 points)
        assertNull(pentagon.findIntersections(new Ray(new Vector(-5,1,-1), p1)),
                "ERROR: there must be 0 points(null)");
        //test 02: check case of intersecting a pentagon's vertex(0 points)
        assertNull(pentagon.findIntersections(new Ray(new Vector(-5,2,0), p1)),
                "ERROR: there must be 0 points(null)");
        //test 03: check case of intersecting the continuation of a pentagon's edge(0 points)
        assertNull(pentagon.findIntersections(new Ray(new Vector(-5,-2,-1), p1)),
                "ERROR: there must be 0 points(null)");
    }
}