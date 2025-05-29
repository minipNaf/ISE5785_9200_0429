
package geometries;
import primitives.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


import static primitives.Util.*;

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
     * The bottom base of the cylinder, represented as a circle.
     */
    private final Circle bottomBase;

    /**
     * The top base of the cylinder, represented as a circle.
     */
    private final Circle topBase;

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
        Point baseCenter = axis.getHead();
        bottomBase = new Circle(baseCenter, radius, getNormal(baseCenter));
        Point topCenter = baseCenter.add(axis.getDirection().scale(height));
        topBase = new Circle(topCenter, radius, getNormal(topCenter));
    }

    /**
     * Returns the normal vector at a given point on the cylinder's surface.
     * The normal vector is orthogonal to the surface at the specified point.
     *
     * @return the normal vector at the specified point
     */
    @Override
    public Vector getNormal(Point p){
        // Calculate the centers of the cylinder's bases
        Point center1 = axis.getHead();
        Point center2 = axis.getHead().add(axis.getDirection().scale(height));
        /** Check if the point is on the top or bottom base of the cylinder
         * If the point is on the top base(not the one where the axis starts), return the direction of the axis
         * If the point is on the bottom base(the one where the axis starts), return the opposite direction of the axis
         * the check is done by calculating the dot product of the vector from the center to the point
         * if its zero, it means the point is on the base, because the vector is perpendicular to the axis
         * btw, the two centers of the bases are boundary cases, so we need to check them before the regular check
         * otherwise, the subtraction will be vector(0,0,0) and there will be an exception
        */
        if(p.equals(center1) || isZero(p.subtract(center1).dotProduct(axis.getDirection()))) {
            return axis.getDirection().scale(-1);
        }
        else if(p.equals(center2) || isZero(p.subtract(center2).dotProduct(axis.getDirection()))){
            return axis.getDirection().scale(1);
        }
        //if it is not on the bases, we do the regular calculation of tube
        else{
            return super.getNormal(p);
        }
    }

    /**
     * Calculates the intersections between a ray and the cylinder.
     * This method checks for intersections with the cylinder's surface and its two bases.
     * @param maxDistance - the maximum distance from the ray's head to consider for intersection
     * @param ray the ray to check for intersections
     * @return a list of intersection points, or null if there are no intersections
     */
    @Override
    public List<Intersection> calculateIntersectionsHelper(Ray ray, double maxDistance) {
        Point baseCenter = axis.getHead();
        List<Intersection> intersections = null;
        var list = super.calculateIntersectionsHelper(ray, maxDistance);
        if (list != null)
            for (Intersection intersection : list) {
                double distance = Util.alignZero(intersection.point.subtract(baseCenter).dotProduct(axis.getDirection()));
                if (distance > 0 && Util.alignZero(distance - height) < 0) {
                    if (intersections == null)
                        intersections = new LinkedList<>();
                    intersections.add(new Intersection(this, intersection.point, this.getMaterial()));
                }
            }

        // Check intersection with the bottom base
        intersections = getIntersections(ray, bottomBase, intersections, maxDistance);

        // Check intersection with top base
        intersections = getIntersections(ray, topBase, intersections, maxDistance);

        return intersections;
    }

    /**
     * A helper method to calculate intersections between a ray and a circular base of the cylinder.
     *
     * @param ray            the ray to intersect
     * @param circle         the circular base (either bottom or top)
     * @param intersections  the existing list of intersections
     * @param maxDistance the maximum allowed distance from the ray's origin to an intersection point
     * @return an updated list of intersections including any new intersection with the given circle
     */
    private List<Intersection> getIntersections(Ray ray, Circle circle, List<Intersection> intersections, double maxDistance) {
        var list = circle.calculateIntersections(ray, maxDistance);
        if (list != null) {
            if (intersections == null)
                intersections = new LinkedList<>();
            intersections.add(new Intersection(this, list.getFirst().point, this.getMaterial()));
        }
        return intersections;
    }
}
