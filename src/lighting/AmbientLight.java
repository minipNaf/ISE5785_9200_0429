package lighting;
import primitives.Color;
/**
 * Represents ambient light in a scene.
 * Ambient light is a type of light that is uniformly distributed in all directions,
 * illuminating all objects equally without casting shadows.
 * This class encapsulates the intensity of the ambient light in a scene.
 */
public class AmbientLight extends Light{


    /**A constant representing no ambient light*/
    public static final AmbientLight None = new AmbientLight(Color.BLACK);

    /**
     * Constructs an AmbientLight object with the specified intensity.
     *
     * @param intensity the intensity of the ambient light
     */
    public AmbientLight(Color intensity) {
        super(intensity);
    }



}
