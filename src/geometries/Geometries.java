package geometries;

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
    public Geometries(Intersectable ... geometries) {
        for (Intersectable geometry : geometries) {
            add(geometry);
        }
    }
    public Geometries() {}

    /**
     * Adds a geometry to the collection.
     *
     * @param geometry The geometry to be added.
     */
    public void add(Intersectable geometry) {
        geometries.add(geometry);
    }

    /**
     * Finds intersections between a ray and the geometries in the collection.
     *
     * @param ray The ray to check for intersections.
     * @return A list of intersection points.
     */
    public List<Point> findIntersections(Ray ray) {
        // Implementation for finding intersections
        List<Point> intersections = new LinkedList<>();
        List<Point> temp;
        for (Intersectable geometry : geometries) {
            temp = geometry.findIntersections(ray);
            if (temp != null) {
                intersections.addAll(temp);
            }
        }
        if(intersections.isEmpty()){
            return null;
        }
        return intersections;
    }

}
