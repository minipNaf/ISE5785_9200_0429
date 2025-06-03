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
     * The transparency of the material.
     */
    public Double3 kt = Double3.ZERO;



    /**
     * The diffusion of the material, which affects the appearance of object through glass.
     * The value represents the distance of the blackBoard of diffusion from the intersection point.
     * A higher value results in sharper refractions.
     */
    public double diffusion = Double.POSITIVE_INFINITY;
    /**
     * The reflection coefficient of the material.
     */
    public Double3 kr = Double3.ZERO;



    /**
     * The glossure of the material, which affects the appearance of reflections.
     * The value represents the distance of the blackBoard of glossure from the intersection point.
     * A higher value results in sharper reflections.
     */
    public double glossure = Double.POSITIVE_INFINITY;

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

    /**
     * Setter for kt with Double3 parameter
     * @param kt - specular coefficient
     * @return - this Material object
     */
    public Material setkt(Double3 kt) {
        this.kt = kt;
        return this;
    }

    /**
     * Setter for kt with double parameter
     * @param kt - specular coefficient
     * @return - this Material object
     */
    public Material setkt(double kt) {
        this.kt = new Double3(kt);
        return this;
    }


    /**
     * Setter for kr with Double3 parameter
     * @param kr - specular coefficient
     * @return - this Material object
     */
    public Material setkr(Double3 kr) {
        this.kr = kr;
        return this;
    }

    /**
     * Setter for kr with double parameter
     * @param kr - specular coefficient
     * @return - this Material object
     */
    public Material setkr(double kr) {
        this.kr = new Double3(kr);
        return this;
    }

    /**
     * Setter for diffusion
     * @param diffusion - diffusion value
     * @return this Material object
     */
    public Material setDiffusion(double diffusion) {
        this.diffusion = diffusion;
        return this;
    }

    /**
     * Setter for glossure
     * @param glossure - glossure value
     * @return this Material object
     */
    public Material setGlossure(double glossure) {
        this.glossure = glossure;
        return this;
    }
}
