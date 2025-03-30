package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class TubeTest {
    Tube tube = new Tube(1,
            new Ray(new Vector(0,1,0), new Point(1,1,1)));
    @Test
    void testGetNormal() {
        assertEquals(new Vector(1,0,0),tube.getNormal(new Point(-3,0,0)),
                "normal for tube doesn't work correctly");

    }
}