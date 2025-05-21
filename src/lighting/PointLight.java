package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class PointLight extends Light implements LightSource{
    protected Point position;
    private double kC = 1d;
    private double kL = 0d;
    private double kQ = 0d;

    /**
     * Constructs a PointLight object with the specified position and intensity.
     *
     * @param intensity the color intensity of the light
     * @param position the position of the point light in 3D space
     */
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    @Override
    public Color getIntensity(Point point) {
        Double d = point.distance(position);
        return intensity.scale(1d / (kC + kL * d + kQ * d * d));
    }

    @Override
    public Vector getL(Point point) {
        return point.subtract(position).normalize();
    }

    public PointLight setkC(double kC) {
        this.kC = kC;
        return this;
    }

    public PointLight setkL(double kL) {
        this.kL = kL;
        return this;
    }

    public PointLight setkQ(double kQ) {
        this.kQ = kQ;
        return this;
    }
}



