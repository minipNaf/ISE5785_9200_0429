package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import lighting.LightSource;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;

/**
 * Scene class represents a 3D scene with a name, background color, ambient light,
 * and geometries.
 */
public class Scene {

    /** Name of the scene*/
    public String name;

    /** Background color of the scene*/
    public Color background = Color.BLACK;
    /** Ambient light of the scene*/
    public AmbientLight ambientLight = AmbientLight.None;

    /** Geometries in the scene*/
    public Geometries geometries = new Geometries();

    /** List of light sources in the scene*/
    public List<LightSource> lights = new LinkedList<>();

    /**
     * Default constructor for the Scene class.
     * Initializes the scene with a default name.
     * @param name the name of the scene
     */
    public Scene(String name) {
        this.name = name;
    }

    /** setters for the scene properties
     *
     * @param background the background color of the scene
     * @return the current Scene object
     */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    /**
     * Sets the ambient light of the scene.
     *
     * @param ambientLight the ambient light to set
     * @return the current Scene object
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    /**
     * Sets the geometries of the scene.
     *
     * @param geometries the geometries to set
     * @return the current Scene object
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }

    /**
     * Sets the list of light sources in the scene.
     *
     * @param lights the list of light sources to set
     * @return the current Scene object
     */
    public Scene setLights(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }
}
