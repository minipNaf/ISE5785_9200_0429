package lighting;

import primitives.*;

public class SpotLight extends PointLight{
    private final Vector direction;

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
                .scale(Math.max(0, direction.dotProduct(point.subtract(position).normalize())));
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

//    @Override
//    public Color getIntensity(Point p) {
//        double d = position.distance(p);
//        double attenuation = kC + kL * d + kQ * d * d;
//        return super.getIntensity(p).reduce(attenuation);
//    }
}
