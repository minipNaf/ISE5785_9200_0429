package geometries;
import primitives.*;

import java.util.List;

/**
 * Represents a sphere in 3D space. A sphere is defined by its center point
 * and radius. It extends the RadialGeometry class, which provides
 * functionality for radial shapes in 3D geometry.
 */
public class Sphere extends RadialGeometry{
    /**
     * Represents the center point of the sphere in 3D space.
     * It is a fixed point in the Cartesian coordinate system, specified by its x, y, and z coordinates.
     * The center is used to define the sphere's position and to calculate distances or normals in geometric computations.
     * This field is immutable once the sphere is constructed.
     */
    private final Point center;


    /**
     * Constructs a Sphere object with the specified radius and center point.
     * A sphere is a three-dimensional shape where all points on its surface
     * are equidistant from its center.
     *
     * @param radius the radius of the sphere, must be positive
     * @param center the center of the sphere, represented as a Point in 3D space
     */
    public Sphere(double radius, Point center) {
        super(radius);
        this.center = center;
    }
    /**
     * Returns the normal of a certain point on the sphere.
     * This method calculates the normal vector at a given point on the sphere's surface.
     * by subtracting the center point from the given point and normalizing the result.
     * @return the normal from that point
     */
    @Override
    public Vector getNormal(Point p){
        return p.subtract(center).normalize();
    }

    /**
     * Finds the intersections between a given ray and the sphere.
     * This method calculates the intersection points between a ray and the sphere.
     * It returns a list of intersection points, or null if there are no intersections.
     *
     * @param ray the ray to check for intersections
     * @return a list of intersection points, or null if there are no intersections
     */
    @Override
    public List<Point> findIntersections(Ray ray) {
        double tm,d;
        if(ray.getHead().equals(center)) {
            tm = 0;
            d = 0;
        }
        else{
            Vector u = center.subtract(ray.getHead());
            tm = u.dotProduct(ray.getDirection());
            d = Math.sqrt(u.lengthSquared() - tm * tm);
        }
        if(d >= radius) return null; // no intersection
        double th = Math.sqrt(radius * radius - d * d);

        /*The compareSign method has a bug - it doesn't take into account the fixed accuracy in Util,
        so I had to use the alignZero method to cover the case of zero

         */
        if(!Util.compareSign(Util.alignZero(tm - th),1)) {
            if(!Util.compareSign(Util.alignZero(tm + th),1)) return null; // no intersection
            return List.of(ray.getPoint(tm + th));
        }
        if(!Util.compareSign(Util.alignZero(tm + th),1)) {
            if(!Util.compareSign(Util.alignZero(tm - th),1)) return null; // no intersection
            return List.of(ray.getPoint(tm - th));
        }
        return List.of(ray.getPoint(tm - th), ray.getPoint(tm + th));


    }
}
