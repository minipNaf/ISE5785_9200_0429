package geometries;
import primitives.*;
import java.util.List;
/**
 * The Intersectable interface defines a contract for geometries that can be intersected by rays.
 * It provides a method to find intersections between a ray and the geometry.
 */
public interface Intersectable {
    /**
     * Finds the intersections between a given ray and the geometry.
     *
     * @param ray the ray to check for intersections
     * @return a list of intersection points, or null if there are no intersections
     */
    List<Point> findIntersections(Ray ray);
}
