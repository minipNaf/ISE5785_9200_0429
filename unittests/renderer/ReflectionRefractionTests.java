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


    @Test
    void windmill() {
        scene.geometries.add( //
                new Polygon( new Point(0,-100,-100),
                        new Point(0,100,-100),
                        new Point(0,100,100),
                        new Point(0,-100,100))
                        .setEmission(new Color(255, 255, 255)) //
                        .setMaterial(new Material().setkr(1)), //
                new Cylinder( 15d, new Ray(new Vector(1,0,0),new Point(0, 0, 65)),70d)
                        .setEmission(new Color(0, 102, 0)) //
                        .setMaterial(new Material().setkt(0.7)), //
                new Sphere(15d, new Point(85, 0, 65)) //
                        .setEmission(new Color(102, 51, 0)) //
                        .setMaterial(new Material().setkr(0.3)), //
                new Triangle(new Point(85,0,65),
                        new Point(85,-20,30), //
                        new Point(85,20,30)) //
                        .setEmission(new Color(229, 204, 255)) //
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(100)),
                new Triangle(new Point(85,0,65),
                        new Point(85,40,65), //
                        new Point(85,20,100)) //
                        .setEmission(new Color(229, 204, 255)) //
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(100)),
                new Triangle(new Point(85,0,65),
                        new Point(85,-40,65), //
                        new Point(85,-20,100)) //
                        .setEmission(new Color(229, 204, 255)) //
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(100)));
        scene.setAmbientLight(new AmbientLight(new Color(255, 255, 51)));
        scene.lights.add(new PointLight(new Color(1020, 400, 400), new Point(200,200 , 200)) //
                .setkL(0.00001).setkQ(0.000005));

        cameraBuilder
                .setLocation(new Point(1000, 200, 150)) //
                .setDirection(Point.ZERO, Vector.AXIS_Z) //
                .setVpDistance(10000).setVpSize(2500, 2500) //
                .setResolution(500, 500) //
                .build() //
                .renderImage() //
                .writeToImage("windmill");
    }


    /**
     * Test for creating a person figure with mirror scene including shadowing,
     * reflection and transparency with 15+ geometries
     * @author Generated Test
     */
    @Test
    void personWithMirrorScene() {
        // Person's head (sphere)
        scene.geometries.add(
                new Sphere(25d, new Point(0, 100, -100))
                        .setEmission(new Color(255, 220, 177)) // skin tone
                        .setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(50))
        );

        // Person's body (cylinder)
        scene.geometries.add(
                new Cylinder(20d,  new Ray(new Vector(0, 1, 0), new Point(0, 40, -100)), 60d)
                        .setEmission(new Color(100, 150, 200)) // blue shirt
                        .setMaterial(new Material().setKd(0.7).setKs(0.3).setShininess(30))
        );

        // Person's left arm (tube/infinite cylinder)
        scene.geometries.add(
                new Tube(8d, new Ray( new Vector(-1, -0.5, 0), new Point(-30, 70, -100)))
                        .setEmission(new Color(255, 220, 177)) // skin tone
                        .setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(50))
        );

        // Person's right arm (tube/infinite cylinder)
        scene.geometries.add(
                new Tube(8d, new Ray(new Vector(1, -0.5, 0), new Point(30, 70, -100)))
                        .setEmission(new Color(255, 220, 177)) // skin tone
                        .setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(50))
        );

        // Person's left leg (cylinder)
        scene.geometries.add(
                new Cylinder(10d, new Ray(new Vector(0, -1, 0), new Point(-15, -20, -100)), 40d)
                        .setEmission(new Color(50, 50, 150)) // dark blue pants
                        .setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(20))
        );

        // Person's right leg (cylinder)
        scene.geometries.add(
                new Cylinder(10d, new Ray(new Vector(0, -1, 0), new Point(15, -20, -100)), 40d)
                        .setEmission(new Color(50, 50, 150)) // dark blue pants
                        .setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(20))
        );

        // Mirror (large triangle)
        scene.geometries.add(
                new Triangle(new Point(-80, 150, -200), new Point(80, 150, -200), new Point(80, -80, -200))
                        .setEmission(new Color(10, 10, 10))
                        .setMaterial(new Material().setkr(0.9).setKd(0.1).setKs(0.9).setShininess(100).setkr(1))
        );

        // Mirror frame bottom (triangle)
        scene.geometries.add(
                new Triangle(new Point(-80, 150, -200), new Point(-80, -80, -200), new Point(80, -80, -200))
                        .setEmission(new Color(10, 10, 10))
                        .setMaterial(new Material().setkr(0.9).setKd(0.1).setKs(0.9).setShininess(100))
        );

        // Mirror frame (polygon - hexagon around mirror)
        scene.geometries.add(
                new Polygon(new Point(-85, 155, -199), new Point(85, 155, -199),
                        new Point(90, 35, -199), new Point(85, -85, -199),
                        new Point(-85, -85, -199), new Point(-90, 35, -199))
                        .setEmission(new Color(139, 69, 19)) // brown frame
                        .setMaterial(new Material().setKd(0.7).setKs(0.3).setShininess(40))
        );

        // Floor (large triangle)
        scene.geometries.add(
                new Triangle(new Point(-200, -80, -300), new Point(200, -80, -300), new Point(200, -80, 50))
                        .setEmission(new Color(160, 160, 160)) // gray floor
                        .setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(10).setkr(0.1))
        );

        // Floor continuation (triangle)
        scene.geometries.add(
                new Triangle(new Point(-200, -80, -300), new Point(200, -80, 50), new Point(-200, -80, 50))
                        .setEmission(new Color(160, 160, 160)) // gray floor
                        .setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(10).setkr(0.1))
        );

        // Transparent glass sphere (decorative element)
        scene.geometries.add(
                new Sphere(15d, new Point(-60, -50, -80))
                        .setEmission(new Color(200, 250, 255))
                        .setMaterial(new Material().setKd(0.1).setKs(0.9).setShininess(100).setkt(0.8))
        );

        // Small decorative sphere
        scene.geometries.add(
                new Sphere(8d, new Point(70, -55, -90))
                        .setEmission(new Color(255, 100, 100)) // red
                        .setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(80))
        );

        // Wall behind (triangle)
        scene.geometries.add(
                new Triangle(new Point(-300, 200, -250), new Point(300, 200, -250), new Point(300, -100, -250))
                        .setEmission(new Color(220, 220, 180)) // cream wall
                        .setMaterial(new Material().setKd(0.8).setKs(0.1).setShininess(5))
        );

        // Wall behind continuation (triangle)
        scene.geometries.add(
                new Triangle(new Point(-300, 200, -250), new Point(300, -100, -250), new Point(-300, -100, -250))
                        .setEmission(new Color(220, 220, 180)) // cream wall
                        .setMaterial(new Material().setKd(0.8).setKs(0.1).setShininess(5))
        );

        // Ceiling light fixture (semi-transparent sphere)
        scene.geometries.add(
                new Sphere(20d, new Point(0, 180, -120))
                        .setEmission(new Color(255, 255, 200))
                        .setMaterial(new Material().setKd(0.2).setKs(0.8).setShininess(100).setkt(0.5))
        );

        // Set ambient lighting
        scene.setAmbientLight(new AmbientLight(new Color(40, 40, 40)));

        // Main spotlight from above
        scene.lights.add(
                new SpotLight(new Color(80, 80, 70), new Point(0, 200, -50), new Vector(0, -1, -0.5))
                        .setkL(0.0001).setkQ(0.000002)
        );

        // Side light for shadows and reflections
        scene.lights.add(
                new SpotLight(new Color(60, 40, 40), new Point(-100, 50, 0), new Vector(1, 0, -1))
                        .setkL(0.0002).setkQ(0.000005)
        );

        // Soft ambient directional light
        scene.lights.add(
                new DirectionalLight(new Color(10, 10, 12), new Vector(0.5, -1, -1))
        );

        cameraBuilder
                .setLocation(new Point(0, 50, 300))
                .setDirection(new Point(0, 50, -100), Vector.AXIS_Y)
                .setVpDistance(400)
                .setVpSize(300, 300)
                .setResolution(800, 800)
                .build()
                .renderImage()
                .writeToImage("personWithMirrorScene");
    }
}
