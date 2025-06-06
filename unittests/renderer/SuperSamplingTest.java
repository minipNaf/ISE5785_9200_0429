//package renderer;
//
//import org.junit.jupiter.api.Test;
//import scene.Scene;
//
//import geometries.*;
//import lighting.*;
//import primitives.*;
//
//public class SuperSamplingTest {
//    /** Scene for the tests */
//    private final Scene scene         = new Scene("Test scene");
//    /** Camera builder for the tests with triangles */
//    private final Camera.Builder cameraBuilder = Camera.getBuilder()     //
//            .setRayTracer(scene, RayTracerType.SIMPLE);
//    @Test
//    void testSuperSampling() {
//        scene.geometries.add(
//                new Polygon(new Point(-35, 45, 20), new Point(0, -35, 20), new Point(35, 45, 20))
//                        .setEmission(new Color(255, 0, 0))
//                        .setMaterial(new Material().setkr(0.7).setKd(0.5).setShininess(90).setDiffusion(5.0).setGlossure(3.0)),
//
//                new Cylinder(14d, new Ray(new Vector(1, 0, 0), new Point(-55, 0, 45)), 75d)
//                        .setEmission(new Color(0, 102, 255))
//                        .setMaterial(new Material().setKs(0.5).setkt(0.3).setGlossure(4.0)),
//
//                new Tube( 12d, new Ray(new Vector(0, 1, 0), new Point(32, -18, 58)))
//                        .setEmission(new Color(0, 180, 100))
//                        .setMaterial(new Material().setkr(1).setShininess(80).setDiffusion(4.5)),
//
//                new Triangle(new Point(85, 0, 65), new Point(85, -22, 30), new Point(85, 22, 30))
//                        .setEmission(new Color(76, 153, 0))
//                        .setMaterial(new Material().setKd(0.3).setKs(0.3).setShininess(100)),
//
//                new Triangle(new Point(85, 0, 65), new Point(85, 42, 65), new Point(85, 22, 100))
//                        .setEmission(new Color(76, 153, 0))
//                        .setMaterial(new Material().setKd(0.3).setKs(0.3).setShininess(100)),
//
//                new Triangle(new Point(85, 0, 65), new Point(85, -42, 65), new Point(85, -22, 100))
//                        .setEmission(new Color(76, 153, 0))
//                        .setMaterial(new Material().setKd(0.3).setKs(0.3).setShininess(100)),
//
//                new Plane(new Point(0, 0, -105), new Vector(0, 0, 1))
//                        .setEmission(new Color(50, 50, 50))
//                        .setMaterial(new Material().setkr(0.6).setKd(0.4).setKs(0.7).setShininess(120)),
//
//                new Circle(new Point(-65, 25, 28), 32d, new Vector(0, 1, 0))
//                        .setEmission(new Color(255, 165, 0))
//                        .setMaterial(new Material().setkt(0.2).setKs(0.5).setShininess(95).setGlossure(3.5)),
//
//                new Polygon(new Point(-45, 55, -55), new Point(-45, -55, -55), new Point(45, -55, -55), new Point(45, 55, -55))
//                        .setEmission(new Color(60, 60, 60))
//                        .setMaterial(new Material().setkr(0.4).setKd(0.3).setKs(0.5).setShininess(110)),
//
//                new Cylinder(21d, new Ray(new Vector(0, 1, 0), new Point(-35, 0, 38)), 95d)
//                        .setEmission(new Color(0, 150, 150))
//                        .setMaterial(new Material().setKs(0.4).setkt(0.2)),
//
//                new Tube( 16d, new Ray(new Vector(1, 0, 0), new Point(-75, 0, 25)))
//                        .setEmission(new Color(150, 50, 50))
//                        .setMaterial(new Material().setkr(0.8).setShininess(85).setDiffusion(5.5)),
//
//                new Triangle(new Point(50, 0, 28), new Point(72, -30, 28), new Point(50, 30, 28))
//                        .setEmission(new Color(80, 130, 200))
//                        .setMaterial(new Material().setKd(0.3).setKs(0.4).setShininess(95)),
//
//                new Triangle(new Point(42, 0, 28), new Point(42, 50, 28), new Point(72, 25, 72))
//                        .setEmission(new Color(200, 130, 80))
//                        .setMaterial(new Material().setKd(0.2).setKs(0.3).setShininess(85)),
//
//                new Triangle(new Point(42, 0, 28), new Point(42, -50, 28), new Point(72, -25, 72))
//                        .setEmission(new Color(200, 80, 130))
//                        .setMaterial(new Material().setKd(0.2).setKs(0.3).setShininess(85)),
//
//                new Circle(new Point(28, -15, 28), 26d, new Vector(0, 1, 0))
//                        .setEmission(new Color(100, 200, 50))
//                        .setMaterial(new Material().setkt(0.3).setKs(0.4).setShininess(90)),
//
//                new Cylinder(19d, new Ray(new Vector(1, 0, 0), new Point(-32, 32, 85)), 72d)
//                        .setEmission(new Color(0, 180, 180))
//                        .setMaterial(new Material().setKs(0.5).setkt(0.3)),
//
//                new Tube( 11d, new Ray(new Vector(0, 1, 0), new Point(52, 22, 85)))
//                        .setEmission(new Color(120, 20, 200))
//                        .setMaterial(new Material().setkr(0.9).setShininess(75)),
//
//                new Polygon(new Point(-72, 63, -32), new Point(-72, -63, -32), new Point(72, -63, -32), new Point(72, 63, -32))
//                        .setEmission(new Color(40, 40, 40))
//                        .setMaterial(new Material().setkr(0.7).setKd(0.6).setKs(0.7).setShininess(100)),
//
//                new Plane(new Point(0, -105, 0), new Vector(0, 1, 0))
//                        .setEmission(new Color(30, 30, 30))
//                        .setMaterial(new Material().setkr(0.6).setKd(0.3).setKs(0.5).setShininess(110))
//        );
//        cameraBuilder
//                .setLocation(new Point(0, 0, 100)) // Set camera location
//                .setDirection(Point.ZERO, Vector.AXIS_Y) // Set camera direction
//                .setVpDistance(100).setVpSize(200, 200) // Set view plane distance and size
//                .setResolution(400, 400) // Set resolution
//                .setMultithreading(-1)
//                .setDebugPrint(10)// Enable multithreading
//                .build() // Build the camera
//                .renderImage() // Render the image
//                .writeToImage("super_sampling_test"); // Save the image
//    }
//}
