package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;
import geometries.Sphere;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Sphere class.
 * This class contains test cases to verify the functionality of the Sphere class,
 * including its constructor and the getNormal method.
 */
class SphereTest {
    /**
     * Test case for the Sphere class.
     * This test case creates a Sphere object and verifies its constructor and getNormal method.
     */
    Sphere sphere = new Sphere(1,new Point(0,0,0));

    /**
     * Test case for the constructor of the Sphere class.
     * This test case verifies that the constructor does not throw an exception
     * when creating a Sphere object with valid parameters.
     * this test for: {@link geometries.Sphere#Sphere(double, Point)}
     */
    @Test
    void testConstructor(){
        // ============ Equivalence Partitions Tests ==============
        //test 01: check case of constructing a regular sphere
        assertDoesNotThrow(()->new Sphere(3.68, new Point(3,6,3)),
                "ERROR: can't construct a regular sphere");
    }


    /**
     * Test method for {@link Sphere#getNormal(Point)}.
     * This test checks the normal vector at a specific point on the sphere's surface.
     * The expected normal vector is calculated based on the sphere's center and the given point.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        //test 01: check case of getting the normal vector at a point on the sphere's surface
        assertEquals(new Vector(0,1,0),sphere.getNormal(new Point(0,1,0)),
                "ERROR: getNormal() for sphere doesn't work correctly");
    }
}