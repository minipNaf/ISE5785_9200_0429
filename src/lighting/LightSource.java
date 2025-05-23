package lighting;

import primitives.*;

/**
 * Interface representing a light source in a 3D scene.
 * This interface defines methods to get the intensity and direction of the light source.
 */
public interface LightSource {
    /**
     * Returns the intensity of the light at a given point in space.
     *
     * @param point The point in space where the intensity is calculated.
     * @return The intensity of the light at the specified point.
     */
    public Color getIntensity(Point point);

    /**
     * Returns the direction of the light source from a given point in space.
     *
     * @param point The point in space from which the direction is calculated.
     * @return The direction vector of the light source from the specified point.
     */
    public Vector getL(Point point);


}
