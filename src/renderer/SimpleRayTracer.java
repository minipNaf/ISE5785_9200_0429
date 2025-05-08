package renderer;

import primitives.*;
import scene.Scene;

import java.util.List;

/**
 * Simple ray tracer implementation that extends the RayTracerBase class.
 * This class provides a basic ray tracing algorithm that calculates the color
 * at the intersection point of a ray with the scene's geometries.
 */
public class SimpleRayTracer extends RayTracerBase{

    /**
     * Constructs a SimpleRayTracer object with the specified scene.
     * This constructor initializes the ray tracer with the given scene.
     *
     * @param scene the scene to be rendered
     */
    public SimpleRayTracer(Scene scene) {
        super(scene);
    }

    /**
     * Traces a ray through the scene and returns the color at the intersection point.
     * If there are no intersections, it returns the background color of the scene.
     *
     * @param ray the ray to trace
     * @return the color at the intersection point (or the background color if no intersection)
     */
    @Override
    public Color traceRay(Ray ray) {
        List<Point> intersections = scene.geometries.findIntersections(ray);
        if (intersections == null)
            return scene.background;

        return calcColor(ray.findClosestPoint(intersections));
    }

    /**
     * Calculates the color at a given point in the scene.
     * This method currently returns the ambient light intensity of the scene.
     *
     * @param p the point at which to calculate the color
     * @return the color at the specified point
     */
    private Color calcColor(Point p){
        return scene.ambientLight.getIntensity();
    }
}
