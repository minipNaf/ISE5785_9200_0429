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
     * Finds intersections between a ray and the geometries in the collection.
     *
     * @param ray The ray to check for intersections.
     * @return A list of intersection points.
     */
    public List<Point> findIntersections(Ray ray) {
        // Implementation for finding intersections
        List<Point> intersections = null;
        boolean flag = false;
        List<Point> temp;

        for (Intersectable geometry : geometries) {
            temp = geometry.findIntersections(ray);
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

}
