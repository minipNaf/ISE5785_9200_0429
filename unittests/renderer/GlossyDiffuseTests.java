package renderer;

import geometries.Cylinder;
import geometries.Polygon;
import lighting.AmbientLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.Scene;

import static java.awt.Color.*;

public class GlossyDiffuseTests {
    private final Scene scene         = new Scene("Test scene");
    /** Camera builder for the tests with triangles */
    private final Camera.Builder cameraBuilder = Camera.getBuilder()     //
            .setRayTracer(scene, RayTracerType.SIMPLE);
    @Test
    void DiffuseGlass() {
        scene.setBackground(new Color(WHITE)); // Set background color to blue
        scene.setAmbientLight(new AmbientLight(new Color(20, 20, 20))); // Lower ambient light for clarity
scene.geometries.add(
                new Polygon(new Point(40, 20, -40),
                    new Point(-40, 20, -40),
                    new Point(-40, 20, 100),
                    new Point(40, 20, 100))
                    .setEmission(new Color(20, 20, 20)) //(172, 178, 255)
                    .setMaterial(new Material().setDiffusion(1).setkt(0.7)),
                new Cylinder(2d, new Ray(new Vector(0, 0, 1), new Point(0, 0, -15)), 65d)
                        .setEmission(new Color(255, 255, 102)),
                new Cylinder(23d, new Ray(new Vector(0, -1, 0), new Point(0, 3, 70)), 6d)
                        .setEmission(new Color(204, 0, 0))
                        .setMaterial(new Material().setShininess(4)),
                new Cylinder(2d, new Ray(new Vector(0, 0, 1), new Point(-100, 0, -15)), 65d)
                        .setEmission(new Color(255, 255, 102)),
                new Cylinder(23d, new Ray(new Vector(0, -1, 0), new Point(-100, 3, 70)), 6d)
                        .setEmission(new Color(204, 0, 0))
                        .setMaterial(new Material().setShininess(4))
        );



        cameraBuilder
                .setLocation(new Point(100, 1000, 50)) //
                .setDirection(Point.ZERO, Vector.AXIS_Z) //
                .setVpDistance(500).setVpSize(150, 150) //
                .setResolution(500, 500) //
                .build() //
                .renderImage() //
                .writeToImage("lollipop with diffused glass test");
    }
    @Test
    void glossySurface() {
        scene.setBackground(new Color(WHITE)); // Set background color to blue
        scene.setAmbientLight(new AmbientLight(new Color(20, 20, 20))); // Lower ambient light for clarity
        scene.geometries.add(
                new Polygon(new Point(40, 20, 0),
                        new Point(-40, 20, 0),
                        new Point(-40, 20, 100),
                        new Point(40, 20, 100))
                        .setEmission(new Color(20, 20, 20)) //
                        .setMaterial(new Material().setkr(0.7)),
                new Cylinder(2d, new Ray(new Vector(0, 0, 1), new Point(0, 0, -15)), 65d)
                        .setEmission(new Color(255, 255, 102)),
                new Cylinder(23d, new Ray(new Vector(0, -1, 0), new Point(0, 3, 70)), 6d)
                        .setEmission(new Color(204, 0, 0))
                        .setMaterial(new Material().setShininess(4))
        );


        cameraBuilder
                .setLocation(new Point(500, -1000, 500)) //
                .setDirection(Point.ZERO, Vector.AXIS_Z) //
                .setVpDistance(500).setVpSize(150, 150) //
                .setResolution(500, 500) //
                .build() //
                .renderImage() //
                .writeToImage("lollipop with glossy surface test");
    }
}
