package renderer;

import geometries.*;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.Scene;

import static java.awt.Color.*;

/**
 * Testing soft shadows in a scene with a sphere and a ground plane.
 * The shadows are created by a soft spotlight, which produces softer edges.
 */
public class SoftShadowsTest {

    /** Default constructor to satisfy JavaDoc generator */
    SoftShadowsTest() { /* to satisfy JavaDoc generator */ }
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

    @Test
    void testSoftShadowsFinal() {
        // Ground plane for shadow casting
        scene.geometries.add(
                new Plane(new Point(0, -50, 0), new Vector(0, 1, 0))
                        .setEmission(new Color(180, 180, 180))
                        .setMaterial(new Material().setKd(0.7).setKs(0.2).setShininess(10))
        );

        // Varied geometries spread apart
        scene.geometries.add(new Cylinder(20, new Ray( new Vector(0, 1, 0), new Point(80, 0, 60)), 50)
                .setEmission(new Color(100, 200, 100))
                .setMaterial(new Material().setKd(0.7).setKs(0.4).setShininess(70)));

        scene.geometries.add(new Tube(10, new Ray( new Vector(0, 1, 0), new Point(-80, 0, 90)))
                .setEmission(new Color(250, 100, 150))
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(80)));

        scene.geometries.add(new Sphere(25, new Point(60, 10, -40))
                .setEmission(new Color(200, 100, 250))
                .setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(90)));

        scene.geometries.add(new Triangle(new Point(-90, 0, -80), new Point(-110, 50, -80), new Point(-70, 40, -80))
                .setEmission(new Color(150, 250, 50))
                .setMaterial(new Material().setKd(0.6).setKs(0.3).setShininess(50)));

        scene.geometries.add(new Polygon(new Point(-60, 0, 110), new Point(-30, 50, 100), new Point(30, 50, 100), new Point(60, 0, 110))
                .setEmission(new Color(200, 200, 50))
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)));

        scene.geometries.add(new Tube(15, new Ray( new Vector(0, 1, 0), new Point(70, 0, -80)))
                .setEmission(new Color(100, 150, 250))
                .setMaterial(new Material().setKd(0.7).setKs(0.3).setShininess(70)));

        scene.geometries.add(new Cylinder(30, new Ray( new Vector(0, 1, 0), new Point(-50, 0, 60)), 60)
                .setEmission(new Color(250, 150, 100))
                .setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(80)));

        // Two additional geometries for variety
        scene.geometries.add(new Tube(12, new Ray( new Vector(0, 1, 0), new Point(40, 0, 120)))
                .setEmission(new Color(220, 140, 200))
                .setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(75)));

        scene.geometries.add(new Triangle(new Point(-30, 0, -120), new Point(-50, 60, -120), new Point(-10, 50, -120))
                .setEmission(new Color(250, 180, 90))
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(85)));

        // Three dim light sources for visible shadows
        scene.lights.add(new SpotLight(new Color(300, 250, 200), new Point(50, 150, 100), new Vector(-1, -1, -1))
                .setkL(1E-4).setkQ(1E-5).setRadius(10));

        scene.lights.add(new SpotLight(new Color(250, 300, 250), new Point(-100, 200, 100), new Vector(1, -1, -2))
                .setkL(1E-4).setkQ(1E-5).setRadius(12));

        scene.lights.add(new SpotLight(new Color(200, 250, 300), new Point(100, 50, 150), new Vector(-1, -1, -1))
                .setkL(1E-4).setkQ(1E-5).setRadius(15));

        // Camera positioned **closer** while maintaining a **wide enough view**
        cameraBuilder.setLocation(new Point(0, 100, 500)) // Moved closer
                .setDirection(new Vector(0, 0, -1))
                .setVpSize(250, 250) // Adjusted size for better focus
                .setVpDistance(300)  // Reduced distance to get closer view
                .setResolution(500, 500)
                .setMultithreading(-1)
                .build()
                .renderImage()
                .writeToImage("SoftShadowsFinalTest");
    }
}