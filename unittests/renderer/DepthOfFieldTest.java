package renderer;

import org.junit.jupiter.api.Test;

import geometries.*;
import lighting.*;
import primitives.*;
import scene.Scene;

/**
 * Tests for depth of field rendering
 * This test creates a scene with three spheres at different distances from the camera,
 * and uses depth of field to focus on the middle sphere.
 */
public class DepthOfFieldTest {
    /** Default constructor to satisfy JavaDoc generator */
    DepthOfFieldTest() { /* to satisfy JavaDoc generator */ }

    /** Scene for the tests */
    private final Scene          scene         = new Scene("Test scene");
    /** Camera builder for the tests with triangles */
    private final Camera.Builder cameraBuilder = Camera.getBuilder()     //
            .setRayTracer(scene, RayTracerType.SIMPLE);
    @Test
    void DepthOfFieldTest() {
        // Add three spheres at different distances from the camera
        scene.geometries.add(
                new Sphere(30d, new Point(0, 0, 100)) // Closest
                        .setEmission(new Color(255, 0, 0)) // Red
                        .setMaterial(new Material().setKd(0.6).setKs(0.3).setShininess(10))
        );

        scene.geometries.add(
                new Sphere(30d, new Point(0, 0, 200)) // Middle (focus)
                        .setEmission(new Color(0, 255, 0)) // Green
                        .setMaterial(new Material().setKd(0.6).setKs(0.3).setShininess(10))
        );

        scene.geometries.add(
                new Sphere(30d, new Point(0, 0, 300)) // Farthest
                        .setEmission(new Color(0, 0, 255)) // Blue
                        .setMaterial(new Material().setKd(0.6).setKs(0.3).setShininess(10))
        );

        // Lighting setup
        scene.lights.add(new SpotLight(new Color(500, 500, 500),
                new Point(50, -50, 150), new Vector(-1, 1, 0))
                .setkL(0.0002).setkQ(0.00002)
        );

        // Camera setup with aperture window and focus distance set to second sphere
        cameraBuilder
                .setLocation(new Point(0, -200, 150))
                .setDirection(new Point(0, 0, 200), new Vector(0, 0, 1))
                .setVpDistance(200)
                .setVpSize(150, 150)
                .setResolution(600, 600)
                .setApertureWindow(200) // Focus distance matches middle sphere's Z
                .build()
                .renderImage()
                .writeToImage("DepthOfFieldTest");
    }

}
