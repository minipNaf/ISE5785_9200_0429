package geometries;
import primitives.*;

/**
 * Represents a tube in 3D space, defined as an infinite cylinder
 * with a given radius and an axis represented as a ray.
 *
 * A tube is a specialization of RadialGeometry and extends its functionality
 * to include a specific orientation defined by the axis ray.
 */
public class Tube extends RadialGeometry{
    /**
     * Represents the central axis of the tube in 3D space.
     * The axis is defined as a ray, which consists of a starting point and a
     * normalized direction vector. This ray determines the infinite line
     * along which the tube is oriented.
     */
    protected final Ray axis;

    /**
     * Constructs a Tube object with the specified radius and axis ray.
     *
     * @param radius the radius of the tube, must be positive
     * @param axis the central axis of the tube, represented as a ray
     */
    public Tube(double radius, Ray axis) {
        super(radius);
        this.axis = axis;
    }

    @Override
    public Vector getNormal(Point p){
        return p.subtract(axis.getHead()).normalize();
    }
}
