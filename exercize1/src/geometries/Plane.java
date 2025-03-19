package geometries;

public class Plane {
    private Point point; // A point on the plane
    private Vector normal; // The normal vector to the plane (orthogonal with size 1)

    // Constructor
    public Plane(Point point, Vector normal) {
        this.point = point;
        this.normal = normal.;
    }

    // Overridden equals method
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Plane other = (Plane) obj;
        return point.equals(other.point) && normal.equals(other.normal);
    }

    // Overridden toString method
    @Override
    public String toString() {
        return "Plane[point=" + point + ", normal=" + normal + "]";
    }

    // Get normal vector
    public Vector getNormal() {
        return normal;
    }

    // Get normal vector through a specific point (always returns the plane's normal vector)
    public Vector getNormal(Point point) {
        return normal;
    }
}
