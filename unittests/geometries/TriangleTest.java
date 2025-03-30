package geometries;

import primitives.Point;
import primitives.Vector;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TriangleTest {
    Triangle triangle = new Triangle(
            new Point(0,-2,0),
            new Point(0,0,3),
            new Point(0,2,0));

    @Test
    void testGetNormal(){
        assertEquals(new Vector(1,0,1), triangle.getNormal(new Point(0,0,1)),
                "ERROR: didn't get correct normal of triangle");
    }
}