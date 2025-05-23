package lighting;

import primitives.*;

/**
 * Represents a spotlight in a 3D scene.
 * A spotlight emits light in a specific direction and has a narrow beam.
 * The intensity of the light decreases with the angle between the light direction and the point being illuminated.
 */
public class SpotLight extends PointLight{
    /**
     * The direction of the light beam.
     */
    private final Vector direction;
    /**
     * The narrowness of the beam.
     * A higher value results in a narrower beam.
     */
    private double narrowBeam = 1d;

    /**
     * Constructs a SpotLight object with the specified color, position, and direction.
     *
     * @param color the color of the light
     * @param position the position of the light in 3D space
     * @param direction the direction of the light beam
     */
    public SpotLight(Color color, Point position, Vector direction) {
        super(color, position);
        this.direction = direction.normalize();
    }

    @Override
    public Color getIntensity(Point point) {
        return super.getIntensity(point)
                .scale(Math.max(0,Math.pow(direction.dotProduct(point.subtract(position).normalize()), narrowBeam)));
    }

    @Override
    public SpotLight setkC(double kC) {
        return (SpotLight)super.setkC(kC);
    }

    @Override
    public SpotLight setkL(double kL) {
        return (SpotLight)super.setkL(kL);
    }

    @Override
    public SpotLight setkQ(double kQ) {
        return (SpotLight)super.setkQ(kQ);
    }

    /**
     * Sets the narrowness of the beam.
     *
     * @param narrowBeam the narrowness of the beam
     * @return this SpotLight object
     */
    public SpotLight setNarrowBeam(double narrowBeam) {
        this.narrowBeam = narrowBeam;
        return this;
    }

}
