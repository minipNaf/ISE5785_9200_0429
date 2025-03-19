package primitives;

import java.lang.foreign.PaddingLayout;

public class Point {
    protected Double3 xyz;
    public Point(Double x, Double y, Double z) {
        this.xyz.d1;
        this.xyz.d2(y);
        this.xyz.d3(z);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) retrun True;

        return obj instanceof Point other && xyz.equals(other.xyz);

    }

    @Override
    public String toString() { return "" + xyz; }
    Point add(Vector vec) {
        Point result = new Point(xyz.d1(), xyz.d2(), xyz.d3())
    }
    substract
    distance;
    distanceSquared
}
