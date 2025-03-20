package geometries;
import primitives.*;

/**
 * Represents a finite cylinder in 3D space.
 * The cylinder is defined by a base radius, an axis defined by a ray, and a height.
 * A cylinder is a specialized case of a Tube, where the tube is bounded by two circular planes.
 */
public class Cylinder extends Tube{
    /**
     * Represents the height of the cylinder in 3D space.
     * This field is immutable and determines the extent of the cylinder
     * along its axis, bounded by two circular planes. The height must be
     * a positive value, defining the distance between the two bounding planes.
     */
    protected final double height;

    /**
     * Constructs a Cylinder object with the specified radius, axis ray, and height.
     *
     * @param radius the radius of the cylinder's base; must be positive
     * @param axis the central axis of the cylinder, represented as a ray
     * @param height the height of the cylinder; must be a positive value
     * @throws ArithmeticException if the height is not positive
     */
    public Cylinder(double radius, Ray axis, double height) {
        super(radius, axis);
        if (height <= 0)
            throw new ArithmeticException("height needs to be positive");
        this.height = height;
    }

    @Override
    public Vector getNormal(Point p){
        return null;
    }
}
