package primitives;
import primitives.Util;
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

    public Point getPoint(double t) {
        if (Util.isZero(t)) return head;
        return head.add(direction.scale(t));
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
