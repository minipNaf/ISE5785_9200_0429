package renderer;

import geometries.Cylinder;
import geometries.Plane;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import scene.Scene;

import java.awt.*;

public class SoftShadowsTest {
    /**
     * Scene for the tests
     */
    private final Scene scene = new Scene("Test scene");
    /**
     * Camera builder for the tests with triangles
     */
    private final Camera.Builder cameraBuilder = Camera.getBuilder()     //
            .setRayTracer(scene, RayTracerType.SIMPLE);

    @Test
    void testSoftShadows() {// Add geometries to the scene
        scene.geometries.add(
                new Plane(new Point(0, 0, 1), new Vector(0, 0, 1))
                        .setEmission(new Color(255, 255, 255)),

                new Cylinder(3, new Ray(new Vector(0, 0, 1), new Point(0, 0, 0)), 50)
                        .setEmission(new Color(100, 100, 50))
                        .setMaterial(new Material().setKs(1).setKd(1).setShininess(100))
        );
        // Add a light source with soft shadows
        scene.lights.add(
                new SpotLight(new Color(255, 255, 0), new Point(-40, 60, 20), new Vector(4, -6, -1))
                        .setRadius(1));
        cameraBuilder
                .setLocation(new Point(40, -60, 20)) //
                .setDirection(Point.ZERO, Vector.AXIS_Z) //
                .setVpDistance(10).setVpSize(3, 3) //
                .setResolution(500, 500) //
                .setMultithreading(-1)
                .build() //
                .renderImage() //
                .writeToImage("SoftShadowsTest");

    }
}
