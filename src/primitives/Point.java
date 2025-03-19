package primitives;

public class Point {

    public static final Point ZERO = new Point(Double3.ZERO);

    protected final Double3 xyz;


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return obj instanceof Point other && xyz.equals(other.xyz);
    }

    @Override
    public String toString() { return "" + xyz; }




    public Point(double x, double y, double z) {

        xyz = new Double3(x, y, z);
    }

    public Point(Double3 xyz) {
        this.xyz = xyz;
    }

    public Vector subtract(Point p) {

        return new Vector(xyz.subtract(p.xyz));
    }

    public Point add(Vector v1) {
        return new Point(xyz.add(v1.xyz));
    }

    public double distanceSquared(Point p) {
        Double3 result = this.xyz.subtract(p.xyz);
        return result.d1()*result.d1() + result.d2()*result.d2() + result.d3()*result.d3();
    }

    public double distance(Point p1) {
        return Math.sqrt(distanceSquared(p1));
    }
}
