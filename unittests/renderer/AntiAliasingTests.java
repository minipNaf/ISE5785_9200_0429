package renderer;

import geometries.Cylinder;
import lighting.DirectionalLight;
import lighting.PointLight;
import lighting.SpotLight;
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

    /**
     * Test for rendering a black circle with anti-aliasing
     */
    @Test
    public void AntiAliasingSurprize(){
        double stickLen = 200*Math.sqrt(2);

        scene.geometries.add(new Cylinder(10,
                new Ray(new Vector(0,-1,-1),new Point(-100,100,100)), stickLen)
                .setEmission(new Color(255, 0, 0))
                .setMaterial(new Material().setShininess(100)));
        scene.geometries.add(new Cylinder(10,
                new Ray(new Vector(0,-1,1),new Point(-100,100,-100)),stickLen)
                .setEmission(new Color(0, 0, 255))
                .setMaterial(new Material().setShininess(100)));

        scene.geometries.add(new Cylinder(10,
                new Ray(new Vector(0,-1,-1),new Point(100,100,100)), stickLen)
                .setEmission(new Color(0, 0, 255))
                .setMaterial(new Material().setShininess(100)));
        scene.geometries.add(new Cylinder(10,
                new Ray(new Vector(0,-1,1),new Point(100,100,-100)),stickLen)
                .setEmission(new Color(255, 0, 0))
                .setMaterial(new Material().setShininess(100)));

        scene.geometries.add(new Cylinder(10,
                new Ray(new Vector(-1,-1,0),new Point(100,100,-100)), stickLen)
                .setEmission(new Color(255, 0, 0))
                .setMaterial(new Material().setShininess(100)));
        scene.geometries.add(new Cylinder(10,
                new Ray(new Vector(-1,1,0),new Point(100,-100,-100)),stickLen)
                .setEmission(new Color(0, 0, 255))
                .setMaterial(new Material().setShininess(100)));

        scene.geometries.add(new Cylinder(10,
                new Ray(new Vector(-1,-1,0),new Point(100,100,100)), stickLen)
                .setEmission(new Color(0, 0, 255))
                .setMaterial(new Material().setShininess(100)));
        scene.geometries.add(new Cylinder(10,
                new Ray(new Vector(-1,1,0),new Point(100,-100,100)),stickLen)
                .setEmission(new Color(255, 0, 0))
                .setMaterial(new Material().setShininess(100)));

        scene.geometries.add(new Cylinder(10,
                new Ray(new Vector(-1,0,-1),new Point(100,-100,100)), stickLen)
                .setEmission(new Color(255, 0, 0))
                .setMaterial(new Material().setShininess(100)));
        scene.geometries.add(new Cylinder(10,
                new Ray(new Vector(-1,0,1),new Point(100,-100,-100)),stickLen)
                .setEmission(new Color(0, 0, 255))
                .setMaterial(new Material().setShininess(100)));

        scene.geometries.add(new Cylinder(10,
                new Ray(new Vector(-1,0,-1),new Point(100,100,100)), stickLen)
                .setEmission(new Color(0, 0, 255))
                .setMaterial(new Material().setShininess(100)));
        scene.geometries.add(new Cylinder(10,
                new Ray(new Vector(-1,0,1),new Point(100,100,-100)),stickLen)
                .setEmission(new Color(255, 0, 0))
                .setMaterial(new Material().setShininess(100)));

        scene.lights.add(
                new DirectionalLight(new Color(0,200,200), new Vector(0, -1, 0.5))
        );
        scene.lights.add(
                new PointLight(new Color(255,255,255), new Point(0, 0, 150)) //
                        .setkC(0.0001).setkL(0.00001).setkQ(0.000001) //)
        );
        scene.lights.add(
                new SpotLight(new Color(800,0,0), new Point(0, 200, -200), new Vector(0,-1,1)) //
                        .setkC(0.0001).setkL(0.00001).setkQ(0.000001) //
                        .setNarrowBeam(2)
        );


        scene.setBackground(new Color(255,255,255));
        cameraBuilder
                .setLocation(new Point(700, 600, 500)) //
                .setDirection(Point.ZERO, Vector.AXIS_Z) //
                .setVpDistance(100).setVpSize(100, 100) //
                .setResolution(200, 200)
                .setMultithreading(-1)
                .setAntiAliasing(true)//
                .build() //
                .renderImage() //
                .writeToImage("XcubeWithAntiAliasing");
        cameraBuilder.
                move(new Vector(0, -580, -480))
                .setMultithreading(-1)//
                .build()
                .renderImage()
                .writeToImage("3DstarOfDavidWithAntiAliasing");
    }

}

