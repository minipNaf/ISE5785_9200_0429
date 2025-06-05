package primitives;
import geometries.Intersectable.Intersection;
import java.util.List;

/**
 * Represents a ray in a 3D space, defined by a starting point (head) and a direction vector.
 * The direction vector is normalized to ensure consistent direction representation.
 */
public class Ray {
    /**
     * The normalized direction vector of the ray, representing the direction in
     * which the ray extends from its origin point.
     * This vector ensures consistent representation of the ray's orientation.
     */
    private final Vector direction;

    /**
     * A small delta value used to avoid an intersection of ray with geometry.
     * This value is used to offset the intersection point slightly in the direction of the normal vector.
     */
    private static final double DELTA = 0.1;

    /**
     * the direction of the ray
     * @return direction vector of the ray
     */
    public Vector getDirection() {
        return direction;
    }

    /**
     * Represents the starting point of the ray in 3D space.
     * This is the position from which the ray originates.
     */
    private final Point head;

    /**
     * return the head of the ray
     * @return the head of the ray
     */
    public Point getHead() {
        return head;
    }

    /**
     * Constructs a new Ray with a specified direction vector and starting point (head).
     * The provided direction vector is normalized to ensure it has a constant length of 1.
     *
     * @param direction the direction vector of the ray, normalized during construction
     * @param head the starting point (head) of the ray
     */
    public Ray(Vector direction, Point head) {
        this.direction = direction.normalize();
        this.head = head;
    }

    /**
     * Constructs a new Ray with a specified direction vector, and starting point which is moved by delta
     * @param direction - the direction vector of the ray, normalized during construction
     * @param normal - the normal vector to the surface at the head point, used to adjust the head position
     * @param head - the starting point (head) of the ray
     */
    public Ray(Vector direction, Vector normal, Point head) {
        // If the direction is parallel to the normal, we need to adjust the head position
        if (Util.isZero(direction.dotProduct(normal))) {
            this.head = head;
        } else {
            double nv = normal.dotProduct(direction);
            this.head = head.add(normal.scale(nv > 0 ? DELTA : -DELTA));
        }
        this.direction = direction.normalize();
    }



    /**
     * calculates the point on the ray at a given distance t from the head.
     *
     * @param t - the distance from the head along the ray
     * @return the point on the ray at distance t from the head
     */
    public Point getPoint(double t) {
        try {
            return head.add(direction.scale(t));
        }
        catch (IllegalArgumentException e) {
            return head;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Ray other)
                && direction.equals(other.direction)
                && head.equals(other.head);
    }

    @Override
    public String toString() { return "head:" + head + "\ndirection:" + direction; }


}
