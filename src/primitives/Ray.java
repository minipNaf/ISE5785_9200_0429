package primitives;

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
     * calculates the point on the ray at a given distance t from the head.
     *
     * @param t - the distance from the head along the ray
     * @return the point on the ray at distance t from the head
     */
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

    /**
     * Finds the closest point from a list of points to the ray's head.
     * The method iterates through the list of points and calculates the squared distance
     * from the ray's head to each point, keeping track of the closest point found so far.
     *
     * @param points - a list of points on ray to search for the closest point
     * @return the closest point to the ray's head, or null if the list is null
     */
    public Point findClosestPoint(List<Point> points) {
        if (points == null) return null;
        Point closestPoint = points.get(0);
        double minDistance = head.distanceSquared(closestPoint);

        for (int i = 1; i < points.size(); i++) {
            Point currentPoint = points.get(i);
            double currentDistance = head.distanceSquared(currentPoint);
            if (currentDistance < minDistance) {
                minDistance = currentDistance;
                closestPoint = currentPoint;
            }
        }
        return closestPoint;
    }
}
