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
    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }
}