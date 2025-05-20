package geometries;
import primitives.*;

/**
 * Represents the geometric way to compute the normal vector at a specific point
 * on a geometric object in 3D space.
 *
 * Classes implementing this interface must provide a concrete definition of the method
 * to calculate the normal vector at a given point on their surface. The normal vector
 * is expected to be a unit vector perpendicular to the surface at the specified point.
 */
public abstract class Geometry extends Intersectable {
    /**default constructor to satisfy the compiler*/
    Geometry() {}// Default constructor

    protected Color emission = Color.BLACK; // Default emission color
    
    /**
     * Calculates the normal vector to the geometry at a given point.
     * The normal vector is a unit vector that is perpendicular to the surface
     * of the geometry at the specified point.
     * This is an abstract method so other classes need to implement this method.
     *
     * @param p the point on the geometry where the normal vector is requested
     * @return the normal vector at the given point
     */
    public abstract Vector getNormal(Point p);

    public Geometry setEmission(Color color) {
        emission = color;
        return this;
    }

    public Color getEmission() {
        return emission;
    }
}
