package primitives;

import static primitives.Util.isZero;

public class Vector extends Point {
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return obj instanceof Vector other && super.equals(other);
    }

    @Override
    public String toString() { return "->" + super.toString(); }

    public Vector(double x, double y, double z) {
        super(x,y,z);
        if(equals(ZERO)) {
            throw new IllegalArgumentException("invalid: vector zero");
        }
    }

    public Vector scale(double factor){
        return new Vector(xyz.scale(factor));
    }

    public Vector(Double3 xyz) {
        super(xyz);
    }

    public double lengthSquared() {
        return dotProduct(this);
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    public Vector normalize() {
        return scale(1/length());
    }

    public Vector crossProduct(Vector u) {
        Vector normal = new Vector(xyz.d2() * u.xyz.d3() - xyz.d3() * u.xyz.d2(), xyz.d3() * u.xyz.d1() - xyz.d1() * u.xyz.d3(), xyz.d1() * u.xyz.d2() - xyz.d2() * u.xyz.d1());
        if (isZero(normal.xyz.d1()) && isZero(normal.xyz.d2()) && isZero(normal.xyz.d3()))
            throw new ArithmeticException();
        return normal;
    }

    public double dotProduct(Vector u) {
        return xyz.d1()*u.xyz.d1()+ xyz.d2()*u.xyz.d2()+ xyz.d3()*u.xyz.d3();
    }

}

