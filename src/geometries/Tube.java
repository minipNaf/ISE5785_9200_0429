package geometries;
import primitives.*;
import static java.lang.System.out;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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

    /**
     * Returns the normal vector at a given point on the tube's surface.
     * The normal vector is calculated by projecting the vector from the axis
     * head to the point onto the plane perpendicular to the axis direction.
     *
     * @param p the point on the tube where the normal vector is requested
     * @return the normal vector at the given point
     */

    @Override
    public Vector getNormal(Point p){
        Double t = (p.subtract(axis.getHead())).dotProduct(axis.getDirection());
        if(t == 0) {
            return p.subtract(axis.getHead()).normalize();
        }
        return p.subtract(axis.getHead()).subtract(axis.getDirection().scale(t)).normalize();    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        Vector direction = ray.getDirection();
        Point head = ray.getHead();
        Vector axisDirection = axis.getDirection();
        Vector vProg = direction.subtract(axisDirection.scale(direction.dotProduct(axisDirection)));
        Point center = axis.getPoint(ray.getHead().subtract(axis.getHead()).dotProduct(axisDirection));
        Sphere sphere = new Sphere(radius, center);
        List<Point> temp = sphere.findIntersections(new Ray(vProg,head));
        if(temp == null) return null;
        List<Point> intersections = new LinkedList<>(temp);
        for (int i = 0;i<intersections.size();i++) {
            intersections.set(i,ray.getPoint(intersections.get(i).subtract(head).length()/
                    vProg.length())); //Tales's law


        }
        return intersections;
    }
}
