package primitives;

/**
 * Represents a point in a 3D Cartesian coordinate system.
 * A Point object is immutable and defined by three coordinates (x, y, z).
 */
public class Point {

    /**
     * A constant representing the origin point (0, 0, 0) in 3D Cartesian space.
     * This point serves as a reference for calculations and is often used
     * as a starting position or default value in geometric computations.
     */
    public static final Point ZERO = new Point(Double3.ZERO);

    /**
     * Represents the internal 3D Cartesian coordinates of a point using a Double3 object.
     * This field is immutable and encapsulates the x, y, and z coordinates.
     */
    protected final Double3 xyz;


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return obj instanceof Point other && xyz.equals(other.xyz);
    }

    @Override
    public String toString() { return "" + xyz; }

    /**
     * Constructs a new Point in a 3D Cartesian coordinate system.
     *
     * @param x the x-coordinate of the point
     * @param y the y-coordinate of the point
     * @param z the z-coordinate of the point
     */
    public Point(double x, double y, double z) { xyz = new Double3(x, y, z);}

    /**
     * Constructs a new Point object using the specified Double3 instance for its coordinates.
     *
     * @param xyz the Double3 object representing the x, y, and z coordinates of the point
     */
    public Point(Double3 xyz) {
        this.xyz = xyz;
    }

    /**
     * Subtracts the given point from the current point, resulting in a vector 
     * that represents the direction and distance from the given point to the current point.
     *
     * @param p the point to subtract from the current point
     * @return a new Vector representing the difference between the two points
     */
    public Vector subtract(Point p) {
        return new Vector(xyz.subtract(p.xyz));
    }

    /**
     * Adds the given vector to the current point and returns a new point representing the sum.
     *
     * @param v1 the vector to be added to the current point
     * @return a new Point resulting from the addition of the vector to the current point
     */
    public Point add(Vector v1) {
        return new Point(xyz.add(v1.xyz));
    }

    /**
     * Calculates the squared distance between this point and another point in 3D space.
     * The calculation is based on the differences of their respective coordinates.
     *
     * @param p the point to which the squared distance is calculated
     * @return the squared distance between this point and the given point
     */
    public double distanceSquared(Point p) {
        Double3 result = this.xyz.subtract(p.xyz);
        return result.d1()*result.d1() + result.d2()*result.d2() + result.d3()*result.d3();
    }

    /**
     * Calculates the distance between this point and another given point.
     *
     * @param p1 the point from which the distance is calculated
     * @return the distance between this point and the given point
     */
    public double distance(Point p1) {
        return Math.sqrt(distanceSquared(p1));
    }
}
