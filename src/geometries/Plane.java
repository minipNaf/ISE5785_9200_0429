package geometries;
import primitives.*;

/**
 * Represents a plane in a 3D Cartesian coordinate system.
 * A plane is defined by a point on the plane and a normal vector.
 * The normal vector is orthogonal to the plane and has a length of 1.
 */
public class Plane extends Geometry{
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
}