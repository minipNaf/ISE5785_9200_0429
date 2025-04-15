package geometries;

/**
 * Represents a base class for geometric objects that have a radial shape in 3D space.
 *
 * The class provides fundamental properties and functionality related to radial geometry,
 * specifically the radius, which is a defining characteristic of all radial geometries.
 * Subclasses of RadialGeometry must define additional behaviors or attributes specific
 * to their shape.
 *
 * This class is abstract and cannot be instantiated directly.
 */
abstract public class RadialGeometry extends Geometry {
    /**
     * The radius of the radial geometry.
     *
     * This field represents the distance from the central axis or point of the
     * geometric shape to its surface. It is a defining property of all radial
     * geometries and remains constant for any given instance of a radial shape.
     * The value must be positive and is immutable once the geometry object is created.
     */
    protected final double radius;

    /**
     * Constructs a RadialGeometry object with the specified radius.
     *
     * This constructor initializes the radial geometry with a given positive radius,
     * and ensures that the radius is valid.
     *
     * @param radius the radius of the geometry. Must be a positive value.
     * @throws IllegalArgumentException if the radius is less than or equal to zero.
     */
    public RadialGeometry(double radius){
        if(radius <= 0)
            throw new IllegalArgumentException("radius needs to be positive");
        this.radius = radius;
    }
}
