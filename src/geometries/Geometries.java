package geometries;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import primitives.*;
/**
 * This class represents a collection of geometries.
 * It provides methods to add geometries, find intersections with rays,
 * and get the bounding box of the collection.
 */
public class Geometries {

    List<Intersectable> geometries = new LinkedList<>();

    /**
     * Constructor for Geometries class.
     * Initializes the collection with the given geometries.
     *
     * @param geometries The geometries to be added to the collection.
     */
    public Geometries(Intersectable ... geometries) {
        for (Intersectable geometry : geometries) {
            add(geometry);
        }
    }
    /**
     * Default constructor for Geometries class.
     * Initializes an empty collection of geometries.
     */
    public Geometries() {}

    /**
     * Adds a geometry to the collection.
     *
     * @param geometries The geometry to be added.
     */
    public void add(Intersectable ... geometries) {
        this.geometries.addAll(Arrays.asList(geometries));
    }

    /**
     * Finds intersections between a ray and the geometries in the collection with maxDistance.
     * @param maxDistance - the maximum distance from the ray's head to consider for intersection
     * @param ray The ray to check for intersections.
     * @return A list of intersection points.
     */
    public List<Intersectable.Intersection> calculateIntersectionsHelper(Ray ray, double maxDistance) {
        // Implementation for finding intersections
        List<Intersectable.Intersection> intersections = null;
        boolean flag = false;
        List<Intersectable.Intersection> temp;

        for (Intersectable geometry : geometries) {
            temp = geometry.calculateIntersections(ray, maxDistance);
            if (temp != null) {
                if (!flag) {  // we don't want to create a new list if there aren't any intersections
                    intersections = new LinkedList<>();
                    flag = true;
                }
                intersections.addAll(temp);
            }
        }

        return intersections;
    }

    /**
     * Finds intersections between a ray and the geometries in the collection.
     * @param ray The ray to check for intersections.
     * @return A list of intersection points.
     */
    public List<Intersectable.Intersection> calculateIntersectionsHelper(Ray ray) {
        return calculateIntersectionsHelper(ray, Double.POSITIVE_INFINITY);
    }

}
