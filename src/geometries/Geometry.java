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

    /**
     * The emission color of the geometry.
     * This field represents the color emitted by the geometry when illuminated.
     * It is used in rendering to simulate the appearance of light emitted from
     * the surface of the geometry.
     */
    protected Color emission = Color.BLACK; // Default emission color

    private Material material = new Material(); // Default material
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

    /**
     * The method sets the emission color of the Geometry
     * @param color - the emission color of the geometry
     * @return - the geometry itself
     */
    public Geometry setEmission(Color color) {
        emission = color;
        return this;
    }

    /**
     * The method returns the emission color of the Geometry
     * @return - the emission color of the geometry
     */
    public Color getEmission() {
        return emission;
    }

    /**
     * The method returns the material of the Geometry
     * @return - the material of the geometry
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * The method sets the material of the Geometry
     * @param material - the material of the geometry
     * @return - the geometry itself
     */
    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }
}
