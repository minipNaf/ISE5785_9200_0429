package renderer;

import geometries.Cylinder;
import geometries.Polygon;
import geometries.Sphere;
import lighting.AmbientLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.Scene;

import static java.awt.Color.BLUE;
import static java.awt.Color.RED;

public class GlossyDiffuseTests {
    private final Scene scene         = new Scene("Test scene");
    /** Camera builder for the tests with triangles */
    private final Camera.Builder cameraBuilder = Camera.getBuilder()     //
            .setRayTracer(scene, RayTracerType.SIMPLE);
//    @Test
//    void DiffuseGlass() {
//        scene.setAmbientLight(new AmbientLight(new Color(20, 20, 20))); // Lower ambient light for clarity
//        scene.geometries.add(
//                new Polygon(new Point(-100, 0, -100), new Point(100, 0, -100), new Point(100, 0, 100), new Point(-100, 0, 100))
//                        .setEmission(new Color(255, 255, 255)) //
//                        .setMaterial(new Material().setkt(0.5).setDiffusion(1)),
//                new Cylinder(50d, new Ray(new Vector(0, 0, 1), new Point(0, 100, -45)), 85d)
//                        .setEmission(new Color(255, 204, 0))
//                        .setMaterial(new Material().setKd(0.7).setKs(0.3).setShininess(4)),
//                new Sphere(25d, new Point(0, 100, 60))
//                        .setEmission(new Color(102, 255, 102))
//                        .setMaterial(new Material().setKd(0.6).setKs(0.4).setkt(0.8))
//        );
//
//        scene.lights.add( //
//                new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2)) //
//                        .setkL(0.0004).setkQ(0.0000006));
//
//        cameraBuilder
//                .setLocation(new Point(0, 0, 1000)) //
//                .setDirection(Point.ZERO, Vector.AXIS_Y) //
//                .setVpDistance(1000).setVpSize(150, 150) //
//                .setResolution(500, 500) //
//                .build() //
//                .renderImage() //
//                .writeToImage("diffused glass test");
//    }
}
