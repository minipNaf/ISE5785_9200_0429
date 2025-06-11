
package renderer;


import geometries.Geometries;
import lighting.AmbientLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.Scene;
import geometries.Sphere;
import static org.junit.jupiter.api.Assertions.*;


import static java.awt.Color.*;

class BlackBoardTest {
    /** Scene for the tests */
    private final Scene scene = new Scene("Test scene");
    /** Camera builder for the tests with triangles */
    private final Camera.Builder cameraBuilder = Camera.getBuilder()     //
            .setRayTracer(scene, RayTracerType.SIMPLE);


    @Test
    void testConstructor(){
        assertDoesNotThrow(
                ()->new BlackBoard(new Point(1,0,0), 100, Vector.AXIS_Y, Vector.AXIS_X),
                "BlackBoard constructor should not throw an exception with valid parameters");

    }

    @Test
    void testCastRays() {
        Point position = new Point(100, 0, 0);
        // Test 1: making a scene that represents a blackboard with its cells and its intersection points with the rays
        BlackBoard blackBoard = new BlackBoard(position, 100, Vector.AXIS_Y, Vector.AXIS_X);
        blackBoard.setSize(450); //

        scene.setAmbientLight(new AmbientLight(new Color(255, 191, 191)));
        for (Ray ray : blackBoard.castRays()) {
            scene.geometries.add(new Sphere(1, ray.getPoint(100 / ray.getDirection().dotProduct(Vector.AXIS_X))).setMaterial(
                    new Material().setKd(0.4).setKs(0.3).setShininess(100)
            ));
        }

        cameraBuilder
                .setLocation(position) //
                .setDirection(position.add(Vector.AXIS_X.scale(100)), Vector.AXIS_Y) //
                .setVpDistance(100).setVpSize(450, 450) //
                .setResolution(850, 850) //
                .build() //
                .renderImage() //
                .printGrid(50, new Color(GREEN))//
                .writeToImage("BlackBoardTest");

        scene.setGeometries(new Geometries()); // Clear previous geometries

        //Test 2: making a scene that represents a round blackboard with its cells and its intersection points with the rays
        blackBoard.setCircular(true);
        for (Ray ray : blackBoard.castRays()) {
            scene.geometries.add(new Sphere(1, ray.getPoint(100 / ray.getDirection().dotProduct(Vector.AXIS_X))).setMaterial(
                    new Material().setKd(0.4).setKs(0.3).setShininess(100)
            ));
        }
        cameraBuilder.build().renderImage().printGrid(50, new Color(GREEN)) //
                .writeToImage("RoundBlackBoardTest");
    }
}