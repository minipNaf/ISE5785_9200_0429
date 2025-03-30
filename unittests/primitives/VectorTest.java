package primitives;

import org.junit.jupiter.api.Test;

import static java.lang.Math.sqrt;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class VectorTest {
    Vector vec = new Vector(1,2,3);
    Vector other = new Vector(4,5,6);

    @Test
    void testConstructorCoordinates() {
        assertDoesNotThrow(()->new Vector(1,2,3), "ERROR: Failed constructing a correct vector");
        assertThrows(()-> new Vector(0,0,0), "ERROR: can't construct a zero vector");
    }

    @Test
    void testConstructorDouble3() {
        assertDoesNotThrow(()->new Vector(new Double3(1,2,3)), "ERROR: Failed constructing a correct vector");
        assertThrows(()-> new Vector(new Double3(0,0,0)), "ERROR: can't construct a zero vector");
    }
    @Test
    void testScale() {
        assertEquals(new Vector(3.8*1,3.8*2,3.8*3),vec.scale(3.8),
                "ERROR: factor*vector does not work correctly");
        assertThrows(IllegalArgumentException.class, ()->vec.scale(0),
                "ERROR: can't scale by factor of zero");
    }

    @Test
    void testAdd() {
        assertEquals(new Vector(4,4,4), vec.add(new Vector(3,2 ,1)),
                "ERROR: add() does not work");
        assertThrows(IllegalArgumentException.class, ()->vec.add(new Vector(-1,-2,-3)),
                "ERROR: Vector + -itself does not throw an exception");
    }

    @Test
    void testLengthSquared() {
        assertEquals(14,vec.lengthSquared(),0.00001,
                "ERROR: lengthSquared() does not work correctly");
    }

    @Test
    void testLength() {
        assertEquals(sqrt(14),vec.length(),0.00001,
                "ERROR: lengthSquared() does not work correctly");
    }

    @Test
    void testNormalize() {
        assertEquals(new Vector(1/sqrt(14),2/sqrt(14),3/sqrt(14)),vec.normalize(),
                "ERROR: the normalized vector is not a unit vector");
    }

    @Test
    void testCrossProduct() {
        assertEquals(new Vector(-3/sqrt(54),6/sqrt(54),-3/sqrt(54)),vec.crossProduct(other),
                "ERROR: crossProduct() does not work correctly ");
        assertThrows(IllegalArgumentException.class,()->vec.crossProduct(new Vector(2,4,6)),
                "ERROR: the other vector is not parallel to the original one");
    }

    @Test
    void testDotProduct() {
        assertEquals(32,vec.dotProduct(other),0.00001,
                "ERROR: dotProduct() does not work correctly ");
        assertEquals(0,vec.dotProduct(new Vector(0,-3,2)),0.00001,
                "ERROR: dotProduct() for orthogonal vectors is not zero");
    }
    void testSubtract() {
        assertEquals(new Point(-3,-3,-3),vec.subtract(other),
                "ERROR: subtract() does not work correctly");
        assertThrows(IllegalAccessException.class,()->vec.subtract(vec),
                "ERROR: (point - itself) does not throw an exception");
    }
}