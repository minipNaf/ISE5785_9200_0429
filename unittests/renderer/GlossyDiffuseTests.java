package renderer;

import geometries.*;
import lighting.AmbientLight;
import lighting.DirectionalLight;
import lighting.PointLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.Scene;

import static java.awt.Color.*;

/**
 * Tests for glossy and diffuse surfaces
 */
public class GlossyDiffuseTests {
    /** Default constructor to satisfy JavaDoc generator */
    GlossyDiffuseTests() { /* to satisfy JavaDoc generator */ }

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
                .setLocation(new Point(100, 1000, 50)) // Set camera location
                .setDirection(Point.ZERO, Vector.AXIS_Z)
                .setVpDistance(500).setVpSize(150, 150)
                .setResolution(500, 500)
                .setMultithreading(-1)
                .build()
                .renderImage()
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
                        .setMaterial(new Material().setkr(0.7).setGlossure(1)),
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
                .setMultithreading(-1)
                .setDebugPrint(50)
                .build() //
                .renderImage() //
                .writeToImage("lollipop with glossy surface test");
    }

    @Test
    void testTwentyGeometriesScene() {
        scene.setBackground(new Color(20,20,20)); // White background

        scene.geometries.add(

                new Tube(30,new Ray(new Vector(1,0,0),new Point(0,-250,660)))
                        .setEmission(new Color(java.awt.Color.ORANGE))
                        .setMaterial(new Material().setKd(0.3).setKs(0.6)),
                new Tube(30,new Ray(new Vector(1,0,0),new Point(0,-250,540)))
                        .setEmission(new Color(java.awt.Color.ORANGE))
                        .setMaterial(new Material().setKd(0.3).setKs(0.6)),

                // Planes
                new Plane(new Point(0, 1, 0), new Vector(0, 0, 1)) // Floor
                        .setEmission(new Color(204, 255, 255))
                        .setMaterial(new Material().setKd(0.3).setKs(0.6)),
                new Plane(new Point(0, 0, 1000), new Vector(0, 0, -1)) // Ceiling
                        .setEmission(new Color(100, 100, 120))
                        .setMaterial(new Material().setKd(0.7).setKs(0.2).setShininess(50)),
                new Plane(new Point(0, -300, 0), new Vector(0, 1, 0)) // Back wall
                        .setEmission(new Color(0, 0, 255))
                        .setMaterial(new Material().setKd(0.7).setKs(0.2).setShininess(50)),
                new Plane(new Point(-1000, 0, 0), new Vector(1, 0, 1)) // Left wall
                        .setEmission(new Color(0, 255, 0))
                        .setMaterial(new Material().setKd(0.7).setKs(0.2).setShininess(50)),
                new Plane(new Point(1000, 0, 0), new Vector(-1, 0, 0)) // Right wall
                        .setEmission(new Color(0, 255, 0))
                        .setMaterial(new Material().setKd(0.7).setKs(0.2).setShininess(50)),

                // Left box
                new Polygon(new Point(-400,40,0), new Point(-280,40,0),
                        new Point(-280,40,500), new Point(-400,40,500))
                        .setEmission(new Color(255, 51, 255))
                        .setMaterial(new Material().setKd(0.7).setKs(0.2).setShininess(50)),
                new Polygon(new Point(-400,40,500), new Point(-280,40,500),
                        new Point(-280,-40,500), new Point(-400,-40,500))
                        .setEmission(new Color(255, 102, 178))
                        .setMaterial(new Material().setKd(0.7).setKs(0.2).setShininess(50)),
                new Polygon(new Point(-400,40,0), new Point(-400,40,500),
                        new Point(-400,-40,500), new Point(-400,-40,0))
                        .setEmission(new Color(255, 102, 178))
                        .setMaterial(new Material().setKd(0.7).setKs(0.2).setShininess(50)),
                new Polygon(new Point(-280,40,0), new Point(-280,40,500),
                        new Point(-280,-40,500), new Point(-280,-40,0))
                        .setEmission(new Color(255, 102, 178))
                        .setMaterial(new Material().setKd(0.7).setKs(0.2).setShininess(50)),

                // Right box
                new Polygon(new Point(400,40,0), new Point(280,40,0),
                        new Point(280,40,500), new Point(400,40,500))
                        .setEmission(new Color(255, 51, 255))
                        .setMaterial(new Material().setKd(0.7).setKs(0.2).setShininess(50)),
                new Polygon(new Point(400,40,500), new Point(280,40,500),
                        new Point(280,-40,500), new Point(400,-40,500))
                        .setEmission(new Color(255, 102, 178))
                        .setMaterial(new Material().setKd(0.7).setKs(0.2).setShininess(50)),
                new Polygon(new Point(400,40,0), new Point(400,40,500),
                        new Point(400,-40,500), new Point(400,-40,0))
                        .setEmission(new Color(255, 102, 178))
                        .setMaterial(new Material().setKd(0.7).setKs(0.2).setShininess(50)),
                new Polygon(new Point(280,40,0), new Point(280,40,500),
                        new Point(280,-40,500), new Point(280,-40,0))
                        .setEmission(new Color(255, 102, 178))
                        .setMaterial(new Material().setKd(0.7).setKs(0.2).setShininess(50)),

                // Pentagon between the boxes
                new Polygon(new Point(0.0,   -198.0,  198.0+200),
                        new Point(265.2, -61.2,   61.2+200),
                        new Point(164.0, 160.0,  -160.0+200),
                        new Point(-164.0, 160.0, -160.0+200),
                        new Point(-265.2, -61.2,  61.2+200)
                )
                        .setEmission(new Color(20, 20, 20))
                        .setMaterial(new Material().setkt(0.8).setKd(0.3).setKs(0.6).setDiffusion(2)),

                // Stick
                new Cylinder(10, new Ray(new Vector(0, -1, 1), new Point(0, -50, 50)), 200)
                        .setEmission(new Color(150, 200, 150))
                        .setMaterial(new Material().setKd(0.2).setKs(0.4).setShininess(10)),

                // Ball
                new Sphere(100, new Point(0, -200, 200))
                        .setEmission(new Color(255, 20, 20))
                        .setMaterial(new Material().setKd(0.4).setKs(0.6).setShininess(100)),
                new Polygon(
                        new Point(-400, -299, 0),     // Bottom-left
                        new Point(-280, -299, 0),     // Bottom-right
                        new Point(-280, -299, 500),   // Top-right
                        new Point(-400, -299, 500)    // Top-left
                )
                        .setEmission(new Color(20, 20, 20)) // Slight mirror tint (optional)
                        .setMaterial(new Material()
                                .setkr(0.8)  // Fully reflective
                                .setKd(0.0)
                                .setKs(1.0)
                                .setShininess(300).setGlossure(5)),
                new Polygon(
                        new Point(500, -299, 0),     // Bottom-left
                        new Point(380, -299, 0),     // Bottom-right
                        new Point(380, -299, 500),   // Top-right
                        new Point(500, -299, 500)    // Top-left
                )
                        .setEmission(new Color(20, 20, 20)) // Slight mirror tint (optional)
                        .setMaterial(new Material()
                                .setkr(0.8)  // Fully reflective
                                .setKd(0.0)
                                .setKs(1.0)
                                .setShininess(300).setGlossure(5))
        );
        scene.lights.add(
                new PointLight(new Color(1000,1000,1000), new Point(-700,-200,400))
                        .setkL(0.00005).setkQ(0.000009));
        scene.lights.add(
                new SpotLight(new Color(2000,200,200),new Point(700,-200,400), new Vector(-700,160,-800))
                        .setkL(0.00005).setkQ(0.000009));
        scene.lights.add(
                new DirectionalLight(new Color(1000, 1000, 1000), new Vector(0, 1, -1))
        );


// Camera facing the pentagon from behind the room
        cameraBuilder
                .setLocation(new Point(0, 1300, 900))
                .setDirection(new Point(0, 0, 1), new Vector(0, 0, 1))
                .setVpDistance(300)
                .setVpSize(400, 400)
                .setResolution(400, 400)
                .setMultithreading(-1)
                .build()
                .renderImage()
                .writeToImage("RoomWithPillarsBoxesPentagonAndLollipop");
    }

}
