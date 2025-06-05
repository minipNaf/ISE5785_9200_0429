package renderer;

import static java.awt.Color.*;

import org.junit.jupiter.api.Test;

import geometries.*;
import lighting.*;
import primitives.*;
import scene.Scene;

/**
 * Tests for reflection and transparency functionality, test for partial
 * shadows
 * (with transparency)
 * @author Dan Zilberstein
 */
class ReflectionRefractionTests {
    /** Default constructor to satisfy JavaDoc generator */
    ReflectionRefractionTests() { /* to satisfy JavaDoc generator */ }

    /** Scene for the tests */
    private final Scene          scene         = new Scene("Test scene");
    /** Camera builder for the tests with triangles */
    private final Camera.Builder cameraBuilder = Camera.getBuilder()     //
            .setRayTracer(scene, RayTracerType.SIMPLE);

    /** Produce a picture of a sphere lighted by a spot light */
    @Test
    void twoSpheres() {
        scene.geometries.add( //
                new Sphere( 50d, new Point(0, 0, -50)).setEmission(new Color(BLUE)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setkt(0.3)), //
                new Sphere( 25d, new Point(0, 0, -50)).setEmission(new Color(RED)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100))); //
        scene.lights.add( //
                new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2)) //
                        .setkL(0.0004).setkQ(0.0000006));

        cameraBuilder
                .setLocation(new Point(0, 0, 1000)) //
                .setDirection(Point.ZERO, Vector.AXIS_Y) //
                .setVpDistance(1000).setVpSize(150, 150) //
                .setResolution(500, 500) //
                .build() //
                .renderImage() //
                .writeToImage("refractionTwoSpheres");
    }

    /** Produce a picture of a sphere lighted by a spot light */
    @Test
    void twoSpheresOnMirrors() {
        scene.geometries.add( //
                new Sphere( 400d, new Point(-950, -900, -1000)).setEmission(new Color(0, 50, 100)) //
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20) //
                                .setkt(new Double3(0.5, 0, 0))), //
                new Sphere( 200d, new Point(-950, -900, -1000)).setEmission(new Color(100, 50, 20)) //
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)), //
                new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500), //
                        new Point(670, 670, 3000)) //
                        .setEmission(new Color(20, 20, 20)) //
                        .setMaterial(new Material().setkr(1)), //
                new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500), //
                        new Point(-1500, -1500, -2000)) //
                        .setEmission(new Color(20, 20, 20)) //
                        .setMaterial(new Material().setkr(new Double3(0.5, 0, 0.4))));
        scene.setAmbientLight(new AmbientLight(new Color(26, 26, 26)));
        scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point(-750, -750, -150), new Vector(-1, -1, -4)) //
                .setkL(0.00001).setkQ(0.000005));

        cameraBuilder
                .setLocation(new Point(0, 0, 10000)) //
                .setDirection(Point.ZERO, Vector.AXIS_Y) //
                .setVpDistance(10000).setVpSize(2500, 2500) //
                .setResolution(500, 500) //
                .build() //
                .renderImage() //
                .writeToImage("reflectionTwoSpheresMirrored");
    }

    /**
     * Produce a picture of a two triangles lighted by a spot light with a
     * partially
     * transparent Sphere producing partial shadow
     */
    @Test
    void trianglesTransparentSphere() {
        scene.geometries.add(
                new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135),
                        new Point(75, 75, -150))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),
                new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),
                new Sphere( 30d, new Point(60, 50, -50)).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setkt(0.6)));
        scene.setAmbientLight(new AmbientLight(new Color(38, 38, 38)));
        scene.lights.add(
                new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1))
                        .setkL(4E-5).setkQ(2E-7));

        cameraBuilder
                .setLocation(new Point(0, 0, 1000)) //
                .setDirection(Point.ZERO, Vector.AXIS_Y) //
                .setVpDistance(1000).setVpSize(200, 200) //
                .setResolution(600, 600) //
                .build() //
                .renderImage() //
                .writeToImage("refractionShadow");
    }


    /**
     * Test for creating a person figure with mirror scene including shadowing,
     * reflection and transparency with 15+ geometries
     * @author Generated Test
     */
    /**
     * Test recreating GeoGebra 3D scene with rectangular mirror and person figure
     * Features: reflection, transparency, shadows with 15+ geometries
     * @author Generated Test based on GeoGebra coordinates
     */
    @Test
    void windmill() {
        scene.geometries.add( //
                new Polygon( new Point(0,50,-100),
                        new Point(0,-50,-100),
                        new Point(0,-50,100),
                        new Point(0,50,100)) //
                        .setEmission(new Color(20, 20, 20)) //
                        .setMaterial(new Material().setkr(1)),//
                new Cylinder( 15d, new Ray(new Vector(1,0,0),new Point(0, 0, 65)),70d)
                        .setEmission(new Color(0, 102, 0)) //
                        .setMaterial(new Material()), //
                new Sphere(15d, new Point(85, 0, 65)) //
                        .setEmission(new Color(102, 51, 0)) //
                        .setMaterial(new Material().setkt(0.3)), //
                new Triangle(new Point(85,0,65),
                        new Point(85,-20,30), //
                        new Point(85,20,30)) //
                        .setEmission(new Color(76, 153, 0)) //
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(100)),
                new Triangle(new Point(85,0,65),
                        new Point(85,40,65), //
                        new Point(85,20,100)) //
                        .setEmission(new Color(76, 153, 0)) //
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(100)),
                new Triangle(new Point(85,0,65),
                        new Point(85,-40,65), //
                        new Point(85,-20,100)) //
                        .setEmission(new Color(76, 153, 0)) //
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(100)));
        scene.setAmbientLight(new AmbientLight(new Color(26, 26, 26)));
        scene.lights.add(new PointLight(new Color(1020, 400, 400), new Point(200,200 , 200)) //
                .setkL(0.00001).setkQ(0.000005));
        Camera camera = cameraBuilder
                .setLocation(new Point(1000, 200, 200)) //
                .setDirection(new Point(85,0,65), Vector.AXIS_Z) //
                .setVpDistance(10000).setVpSize(2500, 2500) //
                .setResolution(500, 500)
                .build();


        camera.renderImage() //
                .writeToImage("windmill");

        camera.getBuilder(camera).rotate(45)
                .build()
                .renderImage() //
                .writeToImage("windmillRotated45");

        camera.getBuilder(camera).move(new Vector(800,0,800))
                .build()
                .renderImage() //
                .writeToImage("windmillMovedDiagonally");
    }

    @Test
    void PersonMirrorScene() {
        // Large rectangular mirror
        scene.geometries.add(
                new Polygon(new Point(-100, 0, -100), new Point(100, 0, -100), new Point(100, 0, 100), new Point(-100, 0, 100))
                        .setEmission(new Color(20, 20, 20)) //
                        .setMaterial(new Material().setkr(1))
        );

        // Person's body
        scene.geometries.add(
                new Cylinder(50d, new Ray(new Vector(0, 0, 1), new Point(0, -100, -45)), 85d)
                        .setEmission(new Color(255, 204, 0))
                        .setMaterial(new Material().setKd(0.7).setKs(0.3).setShininess(4))
        );

        // Person's head
        scene.geometries.add(
                new Sphere(25d, new Point(0, -100, 60))
                        .setEmission(new Color(102, 255, 102))
                        .setMaterial(new Material().setKd(0.6).setKs(0.4))
        );

        // Left leg
        scene.geometries.add(
                new Cylinder(10d, new Ray(new Vector(0, 0, -1), new Point(-25, -100, -45)), 50d)
                        .setEmission(new Color(255, 204, 0)) // White left leg
                        .setMaterial(new Material().setKd(0.7).setKs(0.3).setShininess(4))
        );

        // Right leg
        scene.geometries.add(
                new Cylinder(10d, new Ray(new Vector(0, 0, -1), new Point(25, -100, -45)), 50d)
                        .setEmission(new Color(255, 204, 0)) // White right leg
                        .setMaterial(new Material().setKd(0.7).setKs(0.3).setShininess(4))
        );

        // Left arm
        scene.geometries.add(
                new Cylinder(10d, new Ray(new Vector(-1, -7, -2), new Point(-50, -100, 0)), 73.48d)
                        .setEmission(new Color(255, 204, 0)) //
                        .setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(5))
        );

        // Right arm
        scene.geometries.add(
                new Cylinder(10d, new Ray(new Vector(1, -7, -2), new Point(50, -100, 0)), 73.48d)
                        .setEmission(new Color(255, 204, 0)) //
                        .setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(5))
        );

        // Eyes - white part
        scene.geometries.add(
                new Sphere(5d, new Point(-8.54, -120.71, 71.1))
                        .setEmission(new Color(255, 255, 255)) // White eyes
                        .setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(80).setkt(0.6))
        );

        scene.geometries.add(
                new Sphere(5d, new Point(10.24, -120.33, 70.33))
                        .setEmission(new Color(255, 255, 255)) // White eyes
                        .setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(80).setkt(0.6))
        );

        // Eyes - pupils
        scene.geometries.add(
                new Sphere(2d, new Point(-10.15,-125.44,70.94))
                        .setEmission(Color.BLACK)
                        .setMaterial(new Material().setKd(0.6).setKs(0.9).setShininess(80))
        );

        scene.geometries.add(
                new Sphere(2d, new Point(9.03, -125.17, 70))
                        .setEmission(Color.BLACK)
                        .setMaterial(new Material().setKd(0.6).setKs(0.9).setShininess(80))
        );

        Point p1 = new Point(1,-130,64);
        Point p2 = new Point(1.47, -121.99, 71.81);
        Point p3 = new Point(4.8, -124.13, 64.45);
        Point p4 = new Point(-2.72, -124.53, 63.98);

        // Nose
        scene.geometries.add(
                new Triangle(p1, p2, p3)
                        .setEmission(new Color(0, 102, 255))
                        .setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(80))
        );
        scene.geometries.add(
                new Triangle(p1, p2, p4)
                        .setEmission(new Color(0, 102, 255))
                        .setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(80))
        );
        scene.geometries.add(
                new Triangle(p2, p3, p4)
                        .setEmission(new Color(0, 102, 255))
                        .setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(80))
        );

        // Mouth
        scene.geometries.add(
                new Sphere(3, new Point(-12.3, -121.09, 54.62))
                        .setEmission(new Color(255, 0, 0)) // red mouth
                        .setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(80))
        );
        scene.geometries.add(
                new Sphere(3, new Point(-10.73, -120.4, 50.31))
                        .setEmission(new Color(255, 0, 0)) // red mouth
                        .setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(80))
        );
        scene.geometries.add(
                new Sphere(3, new Point(-5.54, -122.14, 49.79))
                        .setEmission(new Color(255, 0, 0)) // red mouth
                        .setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(80))
        );
        scene.geometries.add(
                new Sphere(3, new Point(0.29, -122.09, 48.3))
                        .setEmission(new Color(255, 0, 0)) // red mouth
                        .setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(80))
        );
        scene.geometries.add(
                new Sphere(3, new Point(4.14, -122.48, 49.87))
                        .setEmission(new Color(255, 0, 0)) // red mouth
                        .setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(80))
        );
        scene.geometries.add(
                new Sphere(3, new Point(7.3, -122.3, 51.38))
                        .setEmission(new Color(255, 0, 0)) // red mouth
                        .setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(80))
        );
        scene.geometries.add(
                new Sphere(3, new Point(11.68, -121.56, 55.12))
                        .setEmission(new Color(255, 0, 0)) // red mouth
                        .setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(80))
        );

        // additional circles
        // circle for left arm
        scene.geometries.add(
                new Circle(new Point(-60,-170,-20), 10d, new Vector(-1,-7,-2))
                        .setEmission(new Color(204, 102, 0))
                        .setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(8))
        );

        // circle for right arm
        scene.geometries.add(
                new Circle(new Point(60,-170,-20), 10d, new Vector(1,-7,-2))
                        .setEmission(new Color(204, 102, 0))
                        .setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(8))
        );

        // circle for body top
        scene.geometries.add(
                new Circle(new Point(0, -100, 40.1), 50d, new Vector(0, 0, 1))
                        .setEmission(new Color(204, 102, 0))
                        .setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(8))
        );

        // circle for body bottom
        scene.geometries.add(
                new Circle(new Point(0, -100, -45.1), 50d, new Vector(0, 0, -1))
                        .setEmission(new Color(204, 102, 0))
                        .setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(8))
        );
        // spheres for hands and cylinders for fingers
        scene.geometries.add(
            new Sphere(9, new Point(-60,-175,-23))
                .setEmission(new Color(204, 102, 0))
                .setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(8)),
            new Sphere(9, new Point(60,-175,-23))
                    .setEmission(new Color(204, 102, 0))
                    .setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(8)),
            // right-hand fingers
            new Cylinder(2, new Point(-51.47,-177.87, -23.06), new Point(-43,-177.87,-23.06))
                    .setEmission(new Color(204, 102, 0))
                    .setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(8)),
            new Cylinder(2, new Point(-55.29, -182.6, -24.04), new Point(-54,-192,-24.04))
                    .setEmission(new Color(204, 102, 0))
                    .setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(8)),
            new Cylinder(2, new Point(-60.24, -183.35, -24.61), new Point(-61,-194,-24.61))
                    .setEmission(new Color(204, 102, 0))
                    .setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(8)),
            new Cylinder(2, new Point(-64.69, -182.61, -24.04), new Point(-68,-191,-24.04))
                    .setEmission(new Color(204, 102, 0))
                    .setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(8)),
            new Cylinder(2, new Point(-67.98, -179.14, -22.59), new Point(-74,-185,-22.59))
                    .setEmission(new Color(204, 102, 0))
                    .setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(8)),
            // left-hand fingers
            new Cylinder(2, new Point(51.47,-177.87, -23.06), new Point(43,-177.87,-23.06))
                    .setEmission(new Color(204, 102, 0))
                    .setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(8)),
            new Cylinder(2, new Point(55.29, -182.6, -24.04), new Point(54,-192,-24.04))
                    .setEmission(new Color(204, 102, 0))
                    .setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(8)),
            new Cylinder(2, new Point(60.24, -183.35, -24.61), new Point(61,-194,-24.61))
                    .setEmission(new Color(204, 102, 0))
                    .setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(8)),
            new Cylinder(2, new Point(64.69, -182.61, -24.04), new Point(68,-191,-24.04))
                    .setEmission(new Color(204, 102, 0))
                    .setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(8)),
            new Cylinder(2, new Point(67.98, -179.14, -22.59), new Point(74,-185,-22.59))
                    .setEmission(new Color(204, 102, 0))
                    .setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(8))
        );
        // hair
        scene.geometries.add(new Sphere(25, new Point(0,-97,63))
                .setEmission(new Color(102, 51, 0)));

        // Ground/floor
        scene.geometries.add(
                new Polygon(new Point(-119.39, -337.76, -100), new Point(158.54, -340.42, -100),
                        new Point(131.44, 58.81, -100), new Point(-129.1, 46, -100))
                        .setEmission(new Color(20, 20, 20))
                        .setMaterial(new Material().setKd(0.8).setKs(0.1).setShininess(10).setkr(0.2))
        );

        // Adjusting lights for better contrast
        scene.setAmbientLight(new AmbientLight(new Color(20, 20, 20))); // Lower ambient light for clarity

        scene.lights.add(
                new SpotLight(new Color(500, 500, 450), new Point(150, 50, 50), new Vector(-1, -0.5, -0.3))
                        .setkL(0.0003).setkQ(0.00001) // Reduced brightness to avoid washing out details
        );

        scene.lights.add(
                new SpotLight(new Color(400, 350, 300), new Point(-100, 20, 80), new Vector(1, -0.3, -0.8))
                        .setkL(0.0004).setkQ(0.00002) // Softer reflections
        );

        scene.lights.add(
                new DirectionalLight(new Color(80, 80, 90), new Vector(0, -1, -0.2)) // Gentle overhead lighting
        );

        cameraBuilder
                .setLocation(new Point(0, -300, 200))
                .setDirection(new Point(0, 0, -100), new Vector(0,3,-2))
                .setVpDistance(300)
                .setVpSize(400, 400)
                .setResolution(800, 800)
                .setMultithreading(-1)
                .build()
                .renderImage()
                .writeToImage("PersonMirrorScene");
    }
}



