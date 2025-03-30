package primitives;

import org.junit.jupiter.api.Test;

import static java.lang.Math.sqrt;
import static org.junit.jupiter.api.Assertions.*;

class PointTest {
    Point point = new Point(1,2,3);
    Vector vec = new Vector(4,5,6);
    Point otherPoint = new Point(3,2,1);
    @Test
    void testAdd() {
        assertEquals(new Point(5,7,9),point.add(vec),
                "ERROR: (point + vector) = other point does not work correctly");
        assertEquals(Point.ZERO,point.add(new Vector(-1,-2,-3)),
                "ERROR: (point + vector) = center of coordinates does not work correctly");
    }

    @Test
    void testDistanceSquared() {
        assertEquals(8,point.distanceSquared(otherPoint),0.00001,
                "ERROR: distanceSquared() does not work");
        assertEquals(0, point.distanceSquared(point), 0.00001,
                "ERROR: point squared distance to itself is not zero");

    }

    @Test
    void testDistance() {
        assertEquals(sqrt(8),point.distance(otherPoint),0.00001,
                "ERROR: distanceSquared() does not work");
        assertEquals(0, point.distance(point), 0.00001,
                "ERROR: point distance to itself is not zero");
    }
}