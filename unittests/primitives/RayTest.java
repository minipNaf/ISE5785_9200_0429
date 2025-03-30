package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RayTest {

    Vector vec = new Vector(1,2,3);
    Point p = new Point(1,2,3);
    Ray ray = new Ray(vec, p);
    @Test
    void testConstructor(){
        assertDoesNotThrow(()->new Ray(vec, p),
                "ERROR: Failed constructing a correct Ray");
    }
    @Test
    void testGetDirection() {
        assertEquals(vec.normalize(), ray.getDirection(), "ERROR: getDirection() doesn't work");
    }

    @Test
    void testGetHead() {
        assertEquals(p, ray.getHead(), "ERROR: getHead() doesn't work");
    }

}