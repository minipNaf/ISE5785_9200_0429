//package renderer;
//
//import org.junit.jupiter.api.Test;
//import scene.Scene;
//
//import geometries.*;
//import lighting.*;
//import primitives.*;
//
//public class TwentyGeometriesTest {
//    /** Scene for the tests */
//    private final Scene scene = new Scene("Twenty Geometries Scene");
//    /** Camera builder for the tests */
//    private final Camera.Builder cameraBuilder = Camera.getBuilder()
//            .setRayTracer(scene, RayTracerType.SIMPLE);
//
//    @Test
//    void testTwentyGeometriesScene() {
//        scene.geometries.add(
//                // 1. Floor plane - reflective checkerboard
//                new Plane(new Point(0, 0, -100), new Vector(0, 0, 1))
//                        .setEmission(new Color(40, 40, 40))
//                        .setMaterial(new Material().setkr(0.8).setKd(0.3).setKs(0.6).setShininess(100)),
//
//                // 2. Back wall plane - matte gray
//                new Plane(new Point(0, -200, 0), new Vector(0, 1, 0))
//                        .setEmission(new Color(60, 60, 60))
//                        .setMaterial(new Material().setkr(0.4).setKd(0.7).setKs(0.2).setShininess(50)),
//
//                // 3. Central large sphere - glossy red with high reflection
//                new Sphere( 35d, new Point(0, 0, -20))
//                        .setEmission(new Color(200, 50, 50))
//                        .setMaterial(new Material().setkr(0.9).setKs(0.8).setShininess(120).setGlossure(2.0)),
//
//                // 4. Left sphere - refractive yellow/gold
//                new Sphere( 25d, new Point(-80, -40, 0))
//                        .setEmission(new Color(255, 215, 0))
//                        .setMaterial(new Material().setkt(0.8).setKs(0.6).setShininess(100)),
//
//                // 5. Right sphere - glossy blue
//                new Sphere( 28d, new Point(80, 30, -10))
//                        .setEmission(new Color(50, 150, 255))
//                        .setMaterial(new Material().setkr(0.7).setKs(0.7).setShininess(90)),
//
//                // 6. Small floating sphere - purple metallic
//                new Sphere( 18d, new Point(-30, 60, 40))
//                        .setEmission(new Color(150, 50, 200))
//                        .setMaterial(new Material().setkr(0.85).setKs(0.9).setShininess(150)),
//
//                // 7. Tall cylinder - refractive teal
//                new Cylinder(20d, new Ray(new Vector(0, 0, 1), new Point(-120, -80, -80)), 120d)
//                        .setEmission(new Color(0, 180, 180))
//                        .setMaterial(new Material().setkt(0.7).setKs(0.5).setShininess(80)),
//
//                // 8. Wide cylinder - diffuse orange
//                new Cylinder(25d, new Ray(new Vector(1, 0, 0), new Point(60, -120, -30)), 100d)
//                        .setEmission(new Color(255, 140, 0))
//                        .setMaterial(new Material().setKd(0.8).setKs(0.3).setShininess(40).setDiffusion(6.0)),
//
//                // 9. Angled cylinder - glossy green
//                new Cylinder(15d, new Ray(new Vector(1, 1, 0), new Point(120, 80, 20)), 80d)
//                        .setEmission(new Color(0, 200, 100))
//                        .setMaterial(new Material().setkr(0.6).setKs(0.8).setShininess(110)),
//
//                // 10. Main triangle - large reflective blue
//                new Triangle(new Point(-60, -60, 20), new Point(60, -60, 20), new Point(0, 40, 80))
//                        .setEmission(new Color(0, 100, 255))
//                        .setMaterial(new Material().setkr(0.75).setKd(0.4).setKs(0.6).setShininess(95)),
//
//                // 11. Left triangle - matte red
//                new Triangle(new Point(-150, -100, -20), new Point(-100, -50, -20), new Point(-125, -75, 40))
//                        .setEmission(new Color(255, 80, 80))
//                        .setMaterial(new Material().setKd(0.9).setKs(0.2).setShininess(30).setDiffusion(5.0)),
//
//                // 12. Right triangle - glossy yellow
//                new Triangle(new Point(100, 50, -30), new Point(150, 100, -30), new Point(125, 75, 30))
//                        .setEmission(new Color(255, 255, 0))
//                        .setMaterial(new Material().setkr(0.5).setKs(0.7).setShininess(85)),
//
//                // 13. Refractive circle - like glass window
//                new Circle(new Point(-50, -150, 50), 40d, new Vector(0, 1, 0))
//                        .setEmission(new Color(200, 255, 255))
//                        .setMaterial(new Material().setkt(0.9).setKs(0.4).setShininess(120)),
//
//                // 14. Glossy circle - metallic copper
//                new Circle(new Point(100, -100, 30), 35d, new Vector(1, 0, 0))
//                        .setEmission(new Color(184, 115, 51))
//                        .setMaterial(new Material().setkr(0.8).setKs(0.9).setShininess(140)),
//
//                // 15. Diffuse circle - matte pink
//                new Circle(new Point(40, 120, 10), 30d, new Vector(0, 0, 1))
//                        .setEmission(new Color(255, 192, 203))
//                        .setMaterial(new Material().setKd(0.8).setKs(0.3).setShininess(25).setDiffusion(7.0)),
//
//                // 16. Tube - long refractive purple
//                new Tube(12d, new Ray(new Vector(0, 1, 0), new Point(-80, -50, 60)))
//                        .setEmission(new Color(138, 43, 226))
//                        .setMaterial(new Material().setkt(0.6).setkr(0.3).setKs(0.6).setShininess(75)),
//
//                // 17. Tube - horizontal glossy cyan
//                new Tube(18d, new Ray(new Vector(1, 0, 0), new Point(-40, 80, -40)))
//                        .setEmission(new Color(0, 255, 255))
//                        .setMaterial(new Material().setkr(0.7).setKs(0.8).setShininess(105)),
//
//                // 18. Complex polygon - hexagonal base, reflective silver
//                new Polygon(
//                        new Point(-30, -30, -60), new Point(30, -30, -60),
//                        new Point(50, 0, -60), new Point(30, 30, -60),
//                        new Point(-30, 30, -60), new Point(-50, 0, -60))
//                        .setEmission(new Color(192, 192, 192))
//                        .setMaterial(new Material().setkr(0.9).setKs(0.8).setShininess(160)),
//
//                // 19. Square polygon - glossy emerald
//                new Polygon(
//                        new Point(60, 60, 60), new Point(120, 60, 60),
//                        new Point(120, 120, 60), new Point(60, 120, 60))
//                        .setEmission(new Color(0, 200, 0))
//                        .setMaterial(new Material().setkr(0.6).setKs(0.7).setShininess(100)),
//
//                // 20. Final triangle - refractive crystal
//                new Triangle(new Point(0, 0, 100), new Point(-40, -40, 60), new Point(40, -40, 60))
//                        .setEmission(new Color(220, 220, 255))
//                        .setMaterial(new Material().setkt(0.85).setKs(0.9).setShininess(200))
//        );
//
//        // Add lighting for better effects
//        scene.lights.add(
//                new DirectionalLight(new Color(400, 400, 400), new Vector(1, -1, -1))
//        );
//        scene.lights.add(
//                new PointLight(new Color(300, 300, 300), new Point(-100, 100, 100))
//        );
//        scene.lights.add(
//                new SpotLight(new Color(500, 400, 300), new Point(150, 150, 100), new Vector(-1, -1, -1))
//        );
//
//        cameraBuilder
//                .setLocation(new Point(0, 200, 50)) // Camera positioned to view the scene
//                .setDirection(new Point(0, 0, 0), Vector.AXIS_Z) // Looking towards origin
//                .setVpDistance(150).setVpSize(300, 300) // View plane settings
//                .setResolution(800, 800) // High resolution for quality
//                .setMultithreading(-1)
//                .build()
//                .renderImage()
//                .writeToImage("twenty_geometries_scene");
//    }
//}