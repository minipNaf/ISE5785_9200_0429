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
    void testConstructor(){
        assertDoesNotThrow(()->new Triangle(new Point(7,-2,2),
                new Point(5,7,1),
                new Point(1.5,8,8)),
                "ERROR: can't construct a regular triangle");
    }

    @Test
    void testGetNormal(){
        assertEquals(new Vector(1,0,1), triangle.getNormal(new Point(0,0,1)),
                "ERROR: didn't get correct normal of triangle");
    }
}