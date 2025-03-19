package geometries;
public class RadialGeometry implements Geometry{
    private final double radius;

    public RadialGeometry(double radius) {
        if(radius <= 0)
            throw new IllegalArgumentException("radius field must be positive");
        this.radius = radius;
    }
}
