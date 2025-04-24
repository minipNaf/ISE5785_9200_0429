
package geometries;
import primitives.*;

import java.util.ArrayList;

import java.util.List;
import static java.lang.System.out;

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
         * otherwise, the substaction will be vector(0,0,0) and there will be an exeption
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


    @Override
    public List<Point> findIntersections(Ray ray) {
        Point rayHead = ray.getHead();
        Plane base1 = new Plane(axis.getHead(), axis.getDirection());
        Plane base2 = new Plane(axis.getHead().add(axis.getDirection().scale(height)), axis.getDirection());
        List<Point> intersections = new ArrayList<>();
        List<Point> temp; //to check if a list is null beforehand
        temp = super.findIntersections(ray);
        if(temp != null) intersections.addAll(temp);
        temp = base1.findIntersections(ray);
        if(temp != null) intersections.addAll(temp);
        temp = base2.findIntersections(ray);
        if(temp != null) intersections.addAll(temp);
        double t, s;
        Vector v;
        Point p;
        int size = intersections.size();
        for(int i = 0; i < size; i++) {

            // if t < 0, the point is below base1, if t > height, the point is above base2
        // t represents the difference in height between base1 and the first intersection point
            p = intersections.get(i);
            if(p.equals(axis.getHead())) {
                t = 0;
                s = 0;
            }
            else{
                v = p.subtract(axis.getHead());
                t = axis.getDirection().dotProduct(v);
                s = Math.sqrt(alignZero(v.lengthSquared() - t * t));
            }
            if (alignZero(t) < 0 || alignZero(t - height) > 0 || alignZero(s - radius) > 0) {
                intersections.remove(p);
                i--;
                size--;
            }

        }
        if(intersections.isEmpty()) {
            return null;
        }
        if(intersections.getFirst().equals(intersections.getLast())) return List.of(intersections.getFirst());
        if(intersections.getFirst().distanceSquared(rayHead) > intersections.getLast().distanceSquared(rayHead)) {
            Point temp1 = intersections.getFirst();
            intersections.set(0, intersections.getLast());
            intersections.set(1, temp1);
        }
        return intersections;
//        if(tubeIntersections == null) {
//            return null;
//        }
//        if(tubeIntersections.size() == 1) {//ray starts inside the tube that contains the cylinder
//            Point p = tubeIntersections.getFirst();
//            // Check if the point is on the cylinder's surface
//            double t = axis.getDirection().dotProduct(p.subtract(axis.getHead()));
//            if (Util.alignZero(t) < 0 || alignZero(t - height) > 0) { //intesection point is outside the cylinder
//                double u = rayHead.subtract(axis.getHead()).dotProduct(axis.getDirection());
//                if(Util.alignZero(u) < 0 || alignZero(u - height) > 0) //ray starts outside the cylinder
//                    return null;
//
//                if(Util.alignZero(ray.getDirection().dotProduct(axis.getDirection()))>0) {
//                    return base2.findIntersections(ray); // The point is on the top base
//                }
//                else {
//                    return base1.findIntersections(ray); // The point is on the bottom base
//                }
//
//            }
//            return List.of(p); // The point is on the cylinder's surface
//        }
//        // if t1 < 0, the point is below base1, if t1 > height, the point is above base2
//        // t1 represents the difference in height between base1 and the first intersection point
//        double t1 = axis.getDirection().dotProduct(tubeIntersections.getFirst().subtract(axis.getHead()));
//        // if t2 < 0, the point is below base1, if t2 > height, the point is above base2
//        // t2 represents the difference in height between base1 and the second intersection point
//        double t2 = axis.getDirection().dotProduct(tubeIntersections.getLast().subtract(axis.getHead()));
//        // check if both points are below base1 or above base2
//        if(Util.alignZero(t1) < 0 && Util.alignZero(t2) < 0 ||
//                Util.alignZero(t1 - height) > 0 && Util.alignZero(t2 - height) > 0) {
//            return null;
//        }
//        // check if one point is below base1 and the other is above base2
//        // if it is that way, the ray intersects the cylinder in both bases
//        if(Util.alignZero(t1) < 0 && Util.alignZero(t2 - height) > 0) {
//            return List.of(base1.findIntersections(ray).getFirst(), base2.findIntersections(ray).getFirst());
//        }
//        // check if one point is below base1 and the other is above base2
//        // if it is that way, the ray intersects the cylinder in both bases
//        if(Util.alignZero(t2) < 0 && Util.alignZero(t1 - height) > 0) {
//            return List.of(base2.findIntersections(ray).getFirst(), base1.findIntersections(ray).getFirst());
//        }
//
//        return null;

    }
}