package geometries;
import primitives.*;
import java.util.List;
/**
 * The Intersectable interface defines a contract for geometries that can be intersected by rays.
 * It provides a method to find intersections between a ray and the geometry.
 */
public abstract class Intersectable {
    /**
     * Finds the intersections between a given ray and the geometry.
     *
     * @param ray the ray to check for intersections
     * @return a list of intersection points, or null if there are no intersections
     */
    public final List<Point> findIntersections(Ray ray) {
        var list = calculateIntersections(ray);
        return list == null ? null : list.stream().map(intersection -> intersection.point).toList();
    }

    public static class Intersection {
        public final Geometry geometry;
        public final Point point;

        /**
         * Constructor for the Intersection class.
         *
         * @param geometry the geometry where the intersection occurs
         * @param point    the intersection point
         */
        public Intersection(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public String toString() {
            return STR."Intersection{geometry=\{geometry}, point=\{point}}";
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof Intersection other && geometry==other.geometry && this.point.equals(other.point);
        }

    }

    protected abstract List<Intersection> calculateIntersectionsHelper(Ray ray);


    public final List<Intersection> calculateIntersections(Ray ray) {
        List<Intersection> intersections = calculateIntersectionsHelper(ray);
        if (intersections == null || intersections.isEmpty()) {
            return null;
        }
        return intersections;
    }
}
