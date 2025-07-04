package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * Represents a directional light source in a 3D scene.
 * A directional light emits light in a specific direction, illuminating all objects in that direction.
 * This class encapsulates the intensity and direction of the directional light.
 */
public class DirectionalLight extends Light implements LightSource{
    /**
     * The direction of the light source.
     */
    private final Vector direction;

    /**
     * Constructs a DirectionalLight object with the specified intensity and direction.
     *
     * @param intensity the color intensity of the light
     * @param direction the direction of the light source
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction.normalize();
    }

    @Override
    public Color getIntensity(Point p){
        return intensity;
    }

    @Override
    public Vector getL(Point p) {
        return direction;
    }

    @Override
    public double getDistance(Point point) {
        return Double.POSITIVE_INFINITY; // Directional light is considered to be infinitely far away
    }

    @Override
    public double getRadius() {
        return 0; // Directional light does not have a radius
    }

}
