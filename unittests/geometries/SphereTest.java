package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;
import geometries.Sphere;
import static org.junit.jupiter.api.Assertions.*;

class SphereTest {
    Sphere sphere = new Sphere(1,new Point(0,0,0));

    @Test
    void testConstructor(){
        assertDoesNotThrow(()->new Sphere(3.68, new Point(3,6,3)),
                "ERROR: can't construct a regular sphere");
    }

    @Test
    void testGetNormal() {
        assertEquals(new Vector(0,1,0),sphere.getNormal(new Point(0,1,0)),
                "ERROR: getNormal() for sphere doesn't work correctly");
    }
}