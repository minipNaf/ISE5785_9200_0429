package renderer;

import geometries.Plane;
import geometries.Sphere;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.Scene;

import static java.awt.Color.*;

public class SoftShadowsTest {

    private final Scene scene = new Scene("Soft Shadows Test");

    private final Camera.Builder cameraBuilder = Camera.getBuilder()
            .setRayTracer(scene, RayTracerType.SIMPLE);

    @Test
    void testSoftShadows() {
        // Ground plane (to catch shadows)
        scene.geometries.add(
                new Plane(new Point(0, -50, 0), new Vector(0, 1, 0))
                        .setEmission(new Color(200, 200, 200))
                        .setMaterial(new Material().setKd(0.7).setKs(0.2).setShininess(10))
        );

        // Main object: a floating sphere
        scene.geometries.add(
                new Sphere(50, new Point(0, 0, 0))
                        .setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100))
        );

        // Soft spot light
        scene.lights.add(
                new SpotLight(new Color(1000, 600, 600), new Point(100, 100, 150), new Vector(-1, -1, -2))
                        .setkL(1E-4)
                        .setkQ(1E-5)
                        .setRadius(15) // Bigger radius = softer shadow
        );

        // Camera setup
        cameraBuilder
                .setLocation(new Point(0, 200, 1000))
                .setDirection(new Vector(0, 0, -1))
                .setVpSize(200, 200)
                .setVpDistance(500)
                .setResolution(600, 600)
                .setMultithreading(-1)
                .build()
                .renderImage()
                .writeToImage("SoftShadowsTest");
    }
}