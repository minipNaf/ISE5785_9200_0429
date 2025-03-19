package primitives;

public class Ray {
    private final Vector direction;
    private final Point head;

    public Ray(Vector direction, Point head) {
        this.direction = direction.normalize();
        this.head = head;
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
