package geometries;
import primitives.*;
import java.util.List;

/**
 * Represents a triangle as a specialized polygon with exactly three vertices.
 * A triangle is defined in a 3D Cartesian coordinate system by three points
 * that represent its vertices. It is a convex(קמור) polygon and lies in a single plane.
 */
public class Triangle extends Polygon {
    /**
     * Constructs a Triangle object based on three points.
     * A triangle is a specialized polygon with exactly three vertices.
     *
     * @param p1 the first point of the triangle
     * @param p2 the second point of the triangle
     * @param p3 the third point of the triangle
     */
    public Triangle(Point p1, Point p2, Point p3) {
        super(p1, p2, p3); // Call the superclass constructor
    }


    /**
     * Returns the intersection point of the ray with triangle (if there is).
     * The function uses the barycentric coordinate algorithm to determine if the ray intersects.
     * The algorithm calculates the intersection point using the ray's direction and the triangle's edges.
     * In barycentric coordinates, the any point on triangle can be represented as:
     * w × vertex1 + u × vertex2 + v × vertex3 while w + u + v = 1.
     * Because w + u + v = 1, we can express w as: w 1 - u - v.
     * Therefor we can express the intersection point as:
     * intersectionPoint = (1 - u - v) × vertex1 + u × vertex2 + v × vertex3.
     *
     * @param ray - the ray to check for intersection with the triangle
     * @return the intersection point if it exists, otherwise null
     */
    @Override
    public List<Intersection> calculateIntersectionsHelper(Ray ray) {
        // compute vectors for two edges sharing vertex p1
        Vector edge1 = vertices.get(1).subtract(vertices.get(0));
        Vector edge2 = vertices.get(2).subtract(vertices.get(0));
        Vector normal = edge1.crossProduct(edge2);

        // if dotProduct is near zero, ray lies in plane of triangle
        double dotProduct = normal.dotProduct(ray.getDirection());
        if (Util.isZero(dotProduct)) {
            return null;
        }

        // Calculate the distance from ray origin to the plane
        double t = normal.dotProduct(vertices.get(0).subtract(ray.getHead())) / dotProduct;

        if (t < 0) {
            return null; // The intersection point is behind the ray's origin
        }

        // calculate the intersection point
        Point intersectionPoint = ray.getPoint(t);

        // Calculate vector from vertex1 to intersection point
        Vector v2 = intersectionPoint.subtract(vertices.get(0));

        /*
         * Calculate barycentric coordinates:
         * To determine whether a point lies within a triangle, we utilize barycentric coordinates.
         * This involves computing the barycentric coordinates of the point relative to the triangle.
         * Through derivations and proofs, we arrived at the following matrix equation:
         *
         * |d00  d01| |v| = |d02|
         * |d01  d11| |u| = |d12|
         *
         * By applying Cramer's rule, we can solve these equations to obtain the values of v, u, and w.
         */
        double d00 = edge1.dotProduct(edge1);
        double d01 = edge1.dotProduct(edge2);
        double d02 = edge1.dotProduct(v2);
        double d11 = edge2.dotProduct(edge2);
        double d12 = edge2.dotProduct(v2);

        double invDenom = 1 / (d00 * d11 - d01 * d01);
        double u = (d11 * d02 - d01 * d12) * invDenom;
        double v = (d00 * d12 - d01 * d02) * invDenom;
        double w = 1.0 - u - v;

        // Check if the point is inside the triangle
        if (Util.alignZero(u) > 0 && Util.alignZero(v) > 0 && Util.alignZero(w) > 0 &&
            Util.alignZero(u) < 1 && Util.alignZero(v) < 1 && Util.alignZero(w) < 1)
            return List.of(new Intersection(this, intersectionPoint)); // Return the intersection point

        return null; // The intersection point is outside the triangle

    }
}