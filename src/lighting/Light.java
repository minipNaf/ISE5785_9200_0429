package lighting;

import primitives.Color;

/**
 * Represents a light in a 3D scene.
 * This class encapsulates the intensity of the light.
 * It serves as a base class for different types of light sources.
 */
public class Light {
    /**
     * The intensity of the light.
     */
    protected final Color intensity;

    /**
     * Constructor for the Light class.
     *
     * @param intensity the intensity of the light
     */
    public Light(Color intensity) {
        this.intensity = intensity;
    }

    /**
     * Returns the intensity of the light.
     *
     * @return the intensity of the light
     */
    public Color getIntensity() {
        return intensity;
    }


}
