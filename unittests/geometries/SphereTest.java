package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;
import geometries.Sphere;
import static org.junit.jupiter.api.Assertions.*;

class SphereTest {
    Sphere sphere = new Sphere(1,new Point(0,0,0));
    @Test
    void testGetNormal() {
        assertEquals(new Vector(0,1,0),sphere.getNormal(new Point(0,1,0)),
                "normal for sphere doesn't work correctly");
    }
}