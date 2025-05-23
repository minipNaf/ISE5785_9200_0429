package primitives;

/**
 * The Material class represents the material properties of a 3D object.
 * It includes coefficients for ambient, diffusive, and specular reflection,
 * as well as a shininess value.
 */
public class Material
{
    /**
     * Default constructor for JavaDoc
     */
    public Material() {}
    /**
     * The ambient coefficient of the material.
     */
    public Double3 ka = Double3.ONE;
    /**
     * The diffusive coefficient of the material.
     */
    public Double3 kd = Double3.ZERO;
    /**
     * The specular coefficient of the material.
     */
    public Double3 ks = Double3.ZERO;
    /**
     * The shininess of the material.
     */
    public int nsh = 0;

    /**
     * Setter for ka with Double3 parameter
     * @param ka - ambient coefficient
     * @return - this Material object
     */
    public Material setKa(Double3 ka) {
        this.ka = ka;
        return this;
    }

    /**
     * Setter for ka with double parameter
     * @param ka - ambient coefficient
     * @return - this Material object
     */
    public Material setKa(double ka) {
        this.ka = new Double3(ka);
        return this;
    }

    /**
     * Setter for kd with Double3 parameter
     * @param kd - diffusive coefficient
     * @return - this Material object
     */
    public Material setKd(Double3 kd) {
        this.kd = kd;
        return this;
    }

    /**
     * Setter for kd with double parameter
     * @param kd - diffusive coefficient
     * @return - this Material object
     */
    public Material setKd(double kd) {
        this.kd = new Double3(kd);
        return this;
    }

    /**
     * Setter for ks with Double3 parameter
     * @param ks - specular coefficient
     * @return - this Material object
     */
    public Material setKs(Double3 ks) {
        this.ks = ks;
        return this;
    }

    /**
     * Setter for ks with double parameter
     * @param ks - specular coefficient
     * @return - this Material object
     */
    public Material setKs(double ks) {
        this.ks = new Double3(ks);
        return this;
    }

    /**
     * Setter for shininess
     * @param nsh - shininess value
     * @return this Material object
     */
    public Material setShininess(int nsh) {
        this.nsh = nsh;
        return this;
    }
}
