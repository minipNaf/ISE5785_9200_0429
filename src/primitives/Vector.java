package primitives;
import static java.lang.System.out;
import static primitives.Util.isZero;

/**
 * Represents a three-dimensional vector extending from the Point class.
 * This class provides methods for vector operations, such as scaling, addition,
 * normalization, cross product, and dot product.
 *
 * All vector objects are immutable.
 */
public class Vector extends Point {
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return obj instanceof Vector other && super.equals(other);
    }

    @Override
    public String toString() { return "->" + super.toString(); }

    /**
     * Constructs a new vector with the specified x, y, and z components.
     * The provided components must not represent the zero vector.
     *
     * @param x the x component of the vector
     * @param y the y component of the vector
     * @param z the z component of the vector
     * @throws IllegalArgumentException if the provided components represent the zero vector
     */
    public Vector(double x, double y, double z) {
        super(x,y,z);
        if(this.xyz.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("invalid: vector zero");
        }
    }

    /**
     * Constructs a new Vector object using a Double3 representation of its components.
     * The provided Double3 must not represent the zero vector.
     *
     * @param xyz The Double3 representing the x, y, and z components of the vector.
     *            Must not be equal to Double3.ZERO.
     * @throws IllegalArgumentException if the provided Double3 equals Double3.ZERO.
     */
    public Vector(Double3 xyz) {
        super(xyz);
        //out.println(toString());
        if(xyz.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("invalid: vector zero");
        }
    }

    /**
     * Scales the vector by a given factor. The scaling is applied to all components of the vector.
     *
     * @param factor the scaling factor to multiply each component of the vector by
     * @return a new Vector object representing the scaled vector
     */
    public Vector scale(double factor){
        return new Vector(xyz.scale(factor));
    }

    /**
     * Adds the given vector to the current vector and returns a new vector representing the sum.
     *
     * @param v1 the vector to add to the current vector
     * @return a new vector that represents the sum of the current vector and the given vector
     */
    public Vector add(Vector v1) {
        return new Vector(xyz.add(v1.xyz));
    }

    /**
     * Computes the squared length of the current vector.
     * This value is the result of the vector's dot product with itself.
     *
     * @return the squared length of the vector
     */
    public double lengthSquared() {
        return dotProduct(this);
    }

    /**
     * Calculates the length of the vector.
     * The length is computed as the square root of the sum of the squares of the vector's components.
     *
     * @return the length of the vector
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * Normalizes this vector, converting it to a unit vector (a vector with length of 1)
     * while maintaining the same direction.
     *
     * @return A new normalized vector in the same direction as this vector.
     */
    public Vector normalize() {
        return scale(1/length());
    }

    /**
     * Computes the cross product of this vector with another vector.
     * The cross product is a vector that is perpendicular to both input vectors.
     *
     * @param u the vector to compute the cross product with
     * @return a new vector representing the cross product of this vector and vector u
     * @throws ArithmeticException if the resulting vector is the zero vector
     */
    public Vector crossProduct(Vector u) {
        Vector normal = new Vector(xyz.d2() * u.xyz.d3() - xyz.d3() * u.xyz.d2(), xyz.d3() * u.xyz.d1() - xyz.d1() * u.xyz.d3(), xyz.d1() * u.xyz.d2() - xyz.d2() * u.xyz.d1());
        if (isZero(normal.xyz.d1()) && isZero(normal.xyz.d2()) && isZero(normal.xyz.d3()))
            throw new ArithmeticException();
        return normal.normalize();
    }

    /**
     * Computes the dot product of this vector with another vector.
     * The dot product is a scalar value calculated as the sum of the pairwise products
     * of the corresponding components of the two vectors.
     *
     * @param u the vector to compute the dot product with
     * @return the dot product (scalar value) of this vector and the provided vector
     */
    public double dotProduct(Vector u) {
        return xyz.d1()*u.xyz.d1()+ xyz.d2()*u.xyz.d2()+ xyz.d3()*u.xyz.d3();
    }

}

