package renderer;
import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.*;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class CameraIntersectionsIntegrationTests {
    // Test method to check if the camera can find intersections with a given ray
    private int intersectGeometry(Camera camera, Geometry geometry) {
        int intersectionsCount = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                // Construct a ray for each pixel in the view plane
                Ray ray = camera.constructRay(3, 3, j, i);
                // Check if the ray intersects with any geometry
                try{
                    intersectionsCount += geometry.findIntersections(ray).size();
                }
                catch (NullPointerException e){}
            }
        }
        return intersectionsCount;
    }

    Camera camera = Camera.getBuilder()
            .setLocation(new Point(0, 0, 1))
            .setVpDistance(10)
            .setVpSize(9, 9)
            .setDirection(new Vector(0, 1, 0), new Vector(0, 0, 1))
            .build();

    @Test
    void testCameraIntersectionsWithSphere() {
        // Create a camera object

        Point p1 = new Point(0, 13, 1);
        Point p2 = new Point(0, -4, 0);
        Sphere sphere = new Sphere(1, p1);
        Sphere sphere2 = new Sphere(7, p1);
        Sphere sphere3 = new Sphere(5, p1);
        Sphere sphere4 = new Sphere(14, p1);
        Sphere sphere5 = new Sphere(3, p2);

        // Test the camera's ability to find intersections with sphere
        //test 01: intersection with one ray only (2 points)
        assertEquals(2, intersectGeometry(camera, sphere),
                "ERROR: there should be 2 intersection points");
        //test 02: intersection with all rays (18 points)
        assertEquals(18, intersectGeometry(camera, sphere2),
                "ERROR: there should be 18 intersection points");
        //test 03: intersection with all rays which are not at corner pixels (10 points)
        assertEquals(10, intersectGeometry(camera, sphere3),
                "ERROR: there should be 10 intersection points");
        //test 04: intersection with all rays but the rays start inside the sphere(9 points)
        assertEquals(9, intersectGeometry(camera, sphere4),
                "ERROR: there should be 9 intersection points");
        //test 05: sphere is behind the camera (0 points)
        assertEquals(0, intersectGeometry(camera, sphere5),
                "ERROR: there should be 0 intersection points");

        // Test the camera's ability to find intersections with plane

    }

    @Test
    void testCameraIntersectionsWithTriangle() {
        Point p1 = new Point(-1,13,0.5);
        Point p2 = new Point(1, 13, 0.5);
        Triangle triangle1 = new Triangle(p1, p2, new Point(0, 13, 2));
        Triangle triangle2 = new Triangle(p1, p2, new Point(0, 13, 6));


        // Test the camera's ability to find intersections with triangle
        //test 01: intersection with one ray only (1 point)
        assertEquals(1, intersectGeometry(camera, triangle1),
                "ERROR: there should be 1 intersection point");
        //test 02: intersection with 2 rays only (2 points)
        assertEquals(2, intersectGeometry(camera, triangle2),
                "ERROR: there should be 2 intersection points");
    }

    @Test
    void testCameraIntersectionsWithPlane(){
        Point p1 = new Point(0, 12, 0);
        Point p2 = new Point(1, 12, 0);
        Plane plane1 = new Plane(p1, p2, new Point(0, 12, 1));
        Plane plane2 = new Plane(p1, p2, new Point(0, 11, 1));
        Plane plane3 = new Plane(p1, p2, new Point(0, 12.5, 1));

        //test 01: plane is parallel to view plane (9 points)
        assertEquals(9, intersectGeometry(camera, plane1),
                "ERROR: there should be 9 intersection points");
        //test 02: plane has intersections with bottom six rays (6 points)
        assertEquals(6, intersectGeometry(camera, plane1),
                "ERROR: there should be 6 intersection points");
        //test 03: plane has intersections with all rays but isn't parallel to view plane (9 points)
        assertEquals(9, intersectGeometry(camera, plane1),
                "ERROR: there should be 9 intersection points");
    }
}
