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


@Test
void DOFcomplex() {

    for (int i = 0; i < 7; i++) {
        scene.geometries.add(
                new Sphere(15, new Point((i - 3) * 30, 0, 150 + i * 30))
                        .setEmission(new Color(30 * i, 255 - 30 * i, 50 + 20 * i))
                        .setMaterial(new Material().setKd(0.5).setKs(0.3).setShininess(30))
        );
    }

    // Add 3 small triangles behind the spheres
    scene.geometries.add(
            new Triangle(new Point(-60, 30, 300), new Point(-30, 0, 300), new Point(-90, 0, 300))
                    .setEmission(new Color(150, 150, 255))
                    .setMaterial(new Material().setKd(0.5).setKs(0.3).setShininess(30))
    );
    scene.geometries.add(
            new Triangle(new Point(30, 30, 330), new Point(60, 0, 330), new Point(0, 0, 330))
                    .setEmission(new Color(255, 200, 100))
                    .setMaterial(new Material().setKd(0.5).setKs(0.3).setShininess(30))
    );
    scene.geometries.add(
            new Triangle(new Point(0, 40, 360), new Point(30, 10, 360), new Point(-30, 10, 360))
                    .setEmission(new Color(100, 255, 100))
                    .setMaterial(new Material().setKd(0.5).setKs(0.3).setShininess(30))
    );

    // Add 3 varied light sources
    scene.lights.add(new DirectionalLight(new Color(200, 180, 150), new Vector(-1, -1, -1)));
    scene.lights.add(new SpotLight(new Color(500, 300, 300), new Point(0, -100, 100), new Vector(0, 1, 1))
            .setkL(0.0005).setkQ(0.00005));
    scene.lights.add(new PointLight(new Color(200, 400, 200), new Point(100, 50, 200))
            .setkL(0.0005).setkQ(0.0001));

    // Focus is on the center sphere (Z ~ 140)
    cameraBuilder
            .setLocation(new Point(0, -200, 150))
            .setDirection(new Point(0, 0, 240), new Vector(0, 1, 1)) // Looking at center sphere
            .setVpDistance(200)
            .setVpSize(150, 150)
            .setResolution(500, 500)
            .setMultithreading(-1)                // Use streams for parallel rendering
            .setApertureWindow(240)              // Focus on the center (Z=140)
            .build()
            .renderImage()
            .writeToImage("DOFcomplex");
    }
}