package renderer;

import primitives.*;
import scene.Scene;

/**
 * Abstract base class for ray tracing algorithms.
 * This class provides a common interface for different ray tracing implementations.
 * It contains a reference to the scene being rendered and defines the traceRay method
 * that subclasses must implement to perform ray tracing.
 */
public abstract class RayTracerBase {

    /**
     * The scene being rendered.
     * This field holds a reference to the scene object that contains the geometry and lighting information.
     */
    protected final Scene scene;

    /**
     * Constructs a RayTracerBase object with the specified scene.
     *
     * @param scene the scene to be rendered
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }
    /**
     * Traces a ray through the scene and returns the color at the intersection point.
     *
     * @param ray the ray to trace
     * @return the color at the intersection point
     */
    public abstract Color traceRay(Ray ray);

}
