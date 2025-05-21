package lighting;

import primitives.Color;

public class Light {
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
