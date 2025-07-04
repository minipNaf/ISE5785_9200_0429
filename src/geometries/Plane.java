package geometries;
import primitives.*;
import static java.lang.System.out;
import java.util.List;
/**
 * Represents a plane in a 3D Cartesian coordinate system.
 * A plane is defined by a point on the plane and a normal vector.
 * The normal vector is orthogonal to the plane and has a length of 1.
 */
public class Plane extends Geometry {
    private final Point point; // A point on the plane
    private final Vector normal; // The normal vector to the plane (orthogonal with size 1)

    /**
     * Constructs a Plane object defined by three points in 3D space.
     * The plane is determined by the three points, which must not be on one line.
     * These points are used to define a point on the plane and its orientation.
     *
     * @param p1 the first point defining the plane
     * @param p2 the second point defining the plane
     * @param p3 the third point defining the plane
     */
    public Plane(Point p1, Point p2, Point p3){
        this.point = p1;
        this.normal = p2.subtract(p1).crossProduct(p3.subtract(p1)).normalize();
    }

    /**
     * Constructs a Plane object defined by a point on the plane and a normal vector that is orthogonal to the plane.
     * The normal vector is normalized to ensure it has a unit length.
     *
     * @param point a Point object representing a point on the plane
     * @param normal a Vector object representing the normal vector to the plane, which will be normalized
     */
    public Plane(Point point, Vector normal) {
        this.point = point;
        this.normal = normal.normalize();
    }

    /**
     * Returns the normal vector of the plane.
     * The normal vector is orthogonal to the plane and has a length of 1.
     *
     * @return the normal vector of the plane
     */
    public Vector getNormal() {return normal;}

    @Override// Get normal vector through a specific point (always returns the plane's normal vector)
    public Vector getNormal(Point point) {
        return normal;
    }

    /**
     * Returns the point on the plane.
     * This point is used to define the plane's position in 3D space.
     * @param maxDistance - the maximum distance from the ray's head to consider for intersection
     * @param ray - the ray to check for intersection with the plane
     * @return the point on the plane
     */
    @Override
    public List<Intersection> calculateIntersectionsHelper(Ray ray, double maxDistance) {
        double denominator = normal.dotProduct(ray.getDirection());
        if(point.equals(ray.getHead())) {
            return null; // The ray starts on the plane
        }
        double numerator = normal.dotProduct(point.subtract(ray.getHead()));
        if(Util.isZero(denominator)) {
            return null; // The ray is in the plane
        }
        double t = numerator/denominator;
        if(!Util.compareSign(t,1) ||
                Util.alignZero(ray.getPoint(t).distanceSquared(ray.getHead()) -
                        maxDistance*maxDistance) > 0) {
            return null; // The ray points away from it
        }

        return List.of(new Intersection(this, ray.getPoint(t), getMaterial()));
    }
}