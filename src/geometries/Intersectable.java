package geometries;
import lighting.LightSource;
import primitives.*;
import java.util.List;
/**
 * The Intersectable interface defines a contract for geometries that can be intersected by rays.
 * It provides a method to find intersections between a ray and the geometry.
 */
public abstract class Intersectable {

    /**
     * default constructor for JavaDoc
     */
    Intersectable() {}

    /**
     * Finds the intersections between a given ray and the geometry.
     * @param maxDistance - the maximum distance between the intersection point and the ray head.
     * @param ray the ray to check for intersections
     * @return a list of intersection points, or null if there are no intersections
     */
    public final List<Point> findIntersections(Ray ray, double maxDistance) {
        var list = calculateIntersections(ray, maxDistance);
        return list == null ? null : list.stream().map(intersection -> intersection.point).toList();
    }

    /**
     * Finds the intersections between a given ray and the geometry.
     *
     * @param ray the ray to check for intersections
     * @return a list of intersection points, or null if there are no intersections
     */
    public final List<Point> findIntersections(Ray ray) {
        var list = calculateIntersections(ray, Double.POSITIVE_INFINITY);
        return list == null ? null : list.stream().map(intersection -> intersection.point).toList();
    }

    public static class Intersection {
        /**
         * geometry of intersection point
         */
        public final Geometry geometry;
        /**
         * intersection point
         */
        public final Point point;

        /**
         * material of intersection point
         */
        public final Material material;
        /**
         * normal vector at the intersection point
         */
        public Vector normal;
        /**
         * view vector at the intersection point
         */
        public Vector v;
        /**
         * scalar product of the view vector and the normal vector
         */
        public double vNormal;
        /**
         * light source at the intersection point
         */
        public LightSource light;
        /**
         * vector from light source to the intersection point
         */
        public Vector l;
        /**
         * scalar product of the light vector and the normal vector
         */
        public double lNormal;


        /**
         * Constructor for the Intersection class.
         *
         * @param geometry the geometry where the intersection occurs
         * @param point    the intersection point
         * @param material the material of the geometry
         */
        public Intersection(Geometry geometry, Point point, Material material) {
            this.geometry = geometry;
            this.point = point;
            this.material = material;
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

    protected abstract List<Intersection> calculateIntersectionsHelper(Ray ray, double maxDistance);


    public final List<Intersection> calculateIntersections(Ray ray) {
        return calculateIntersectionsHelper(ray);
//        if (intersections == null || intersections.isEmpty()) {
//            return null;
//        }
//        return intersections;
    }
}
