package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * Represents a point light source in a 3D scene.
 * A point light source emits light uniformly in all directions from a specific point in space.
 * This class encapsulates the position of the point light and its intensity.
 */
public class PointLight extends Light implements LightSource{
    /**
     * The position of the point light in 3D space.
     */
    protected Point position;
    /**
     * The attenuation coefficients for the light intensity.
     * kC - constant attenuation coefficient
     * kL - linear attenuation coefficient
     * kQ - quadratic attenuation coefficient
     */
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

    @Override
    public double getDistance(Point point) {
        return point.distance(position);
    }

    /**
     * setter for kC
     * @param kC - first Attenuation coefficient
     * @return PointLight object
     */
    public PointLight setkC(double kC) {
        this.kC = kC;
        return this;
    }

    /**
     * setter for kL
     * @param kL - second Attenuation coefficient
     * @return PointLight object
     */
    public PointLight setkL(double kL) {
        this.kL = kL;
        return this;
    }

    /**
     * setter for kQ
     * @param kQ - third Attenuation coefficient
     * @return PointLight object
     */
    public PointLight setkQ(double kQ) {
        this.kQ = kQ;
        return this;
    }
}



