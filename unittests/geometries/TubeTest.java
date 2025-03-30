package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class TubeTest {
    Tube tube = new Tube(3, new Ray(new Vector(0,0,1), new Point(0,0, 2)));

    @Test
    void testConstructor() {
        assertDoesNotThrow(() -> new Tube(5.7, new Ray(new Vector(1, 2, 3), new Point(4, 5, 6)))
                , "ERROR: can't construct a regular cylinder");
    }

    @Test
    void testGetNormal() {
        assertEquals(new Vector(-0.25, -2.99, 0).normalize(),tube.getNormal(new Point(-0.25,-2.99,2.8)),
                "normal for tube doesn't work correctly");

        assertEquals(new Vector(-0.75,-2.91,0).normalize(),tube.getNormal(new Point(-0.75, -2.91, 2)),
                "normal for tube doesn't work correctly");
    }
}