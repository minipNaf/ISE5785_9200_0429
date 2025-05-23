package renderer;

import lighting.LightSource;
import primitives.*;
import scene.Scene;
import geometries.Intersectable. Intersection;
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
        List<Intersection> intersections = scene.geometries.calculateIntersectionsHelper(ray);
        if (intersections == null)
            return scene.background;

        return calcColor(ray.findClosestIntersection(intersections), ray);
    }

    /**
     * Calculates the color at a given point in the scene.
     * This method currently returns the ambient light intensity of the scene.
     *
     * @param intersection the intersection at which to calculate the color
     * @return the color at the specified point
     */
    private Color calcColor(Intersection intersection, Ray ray) {
        if (!preprocessIntersection(intersection, ray.getDirection()))
            return Color.BLACK;
        return scene
                .ambientLight.getIntensity().scale(intersection.material.ka).
                add(calcColorLocalEffects(intersection));
    }

    /**
     * Preprocesses the intersection by calculating the normal vector and the view vector.
     * This method also checks if the intersection is valid (not zero).
     *
     * @param intersection the intersection to preprocess
     * @param v the view vector
     * @return true if the intersection is valid, false otherwise
     */
    private boolean preprocessIntersection(Intersection intersection, Vector v) {
        intersection.v = v;
        intersection.normal = intersection.geometry.getNormal(intersection.point);
        intersection.vNormal = Util.alignZero(intersection.v.dotProduct(intersection.normal));

        return intersection.vNormal != 0;
    }

    /**
     * Sets the light source for the intersection and calculates the light vector.
     * This method also checks if the light source is valid (not zero).
     *
     * @param intersection the intersection to set the light source for
     * @param light the light source to set
     * @return true if the light source is valid, false otherwise
     */
    private boolean setLightSource(Intersection intersection, LightSource light) {
        intersection.light = light;
        intersection.l = light.getL(intersection.point);
        intersection.lNormal = Util.alignZero(intersection.l.dotProduct(intersection.normal));

        return intersection.vNormal * intersection.vNormal <= 0;
    }

    /**
     * Calculates the color at the intersection point based on local effects.
     * This method considers the emission color of the geometry and the intensity
     * of the light sources in the scene.
     *
     * @param gp the intersection point
     * @return the calculated color at the intersection point
     */
    private Color calcColorLocalEffects(Intersection gp)
    {
        Color color = gp.geometry.getEmission(); // emission color of geometry

        for (LightSource lightSource : scene.lights) {
            if (!setLightSource(gp, lightSource) && gp.lNormal * gp.vNormal > 0) { // sign(nl) == sign(nv)
                Color iL = lightSource.getIntensity(gp.point); // intensity of color at point
                // adding diffusive and specular effects
                color = color.add(iL.scale(calcDiffusive(gp).add(calcSpecular(gp))));
            }
        }
        return color;
    }

    /**
     * The function calculates specular effects at the intersection point.
     * @param intersection - the intersection point
     * @return the specular color at the intersection point
     */
    private Double3 calcSpecular(Intersection intersection){
        // r = l - (n * lNormal * 2)
        Vector r = intersection.l.subtract(intersection.normal.scale(2 * intersection.lNormal));
        double rv = Util.alignZero(r.dotProduct(intersection.v));
        // check if the angle between the view vector and the reflection vector is acute
        if (rv < 0d) {
            return intersection.material.ks.scale(Math.pow(-rv, intersection.material.nsh));
        }
        // if the angle is obtuse, return zero
        return Double3.ZERO;
    }

    /**
     * The function calculates diffusive effects at the intersection point.
     * @param intersection - the intersection point
     * @return the diffusive color at the intersection point
     */
    private Double3 calcDiffusive(Intersection intersection){

        return intersection.material.kd.scale(Math.abs(intersection.lNormal));
    }
}
