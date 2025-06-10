package renderer;

import org.junit.jupiter.api.Test;
import primitives.*;
import geometries.Sphere;
import scene.Scene;


/**
 * Testing anti-aliasing in rendering
 */
public class AntiAliasingTests {
    /** Default constructor to satisfy JavaDoc generator */
    AntiAliasingTests() { /* to satisfy JavaDoc generator */ }

    /** Scene for the tests */
    private final Scene scene         = new Scene("Test scene");
    /** Camera builder for the tests with triangles */
    private final Camera.Builder cameraBuilder = Camera.getBuilder()     //
            .setRayTracer(scene, RayTracerType.SIMPLE);

    /**
     * Test for rendering a black circle with anti-aliasing
     */
    @Test
    public void blackCircle(){
        scene.geometries.add(new Sphere(100, new Point(1, 0, 0))
                .setEmission(new Color(0, 0, 0))
                .setMaterial(new Material().setShininess(100)));
        scene.setBackground(new Color(255,255,255));
        cameraBuilder
                .setLocation(new Point(0, 0, 500)) //
                .setDirection(Point.ZERO, Vector.AXIS_Y) //
                .setVpDistance(100).setVpSize(100, 100) //
                .setResolution(200, 200)
                .setAntiAliasing(true)//
                .build() //
                .renderImage() //
                .writeToImage("CircleWithAntiAliasing");
        cameraBuilder.setAntiAliasing(false)
                .build()
                .renderImage()
                .writeToImage("CircleWithoutAntiAliasing");
    }

}