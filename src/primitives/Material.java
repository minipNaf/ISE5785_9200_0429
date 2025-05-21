package primitives;

public class Material
{
    public Double3 ka = Double3.ONE;
    public Double3 kd = Double3.ZERO;
    public Double3 ks = Double3.ZERO;
    public int nsh = 0;
    public Material setKa(Double3 ka) {
        this.ka = ka;
        return this;
    }

    public Material setKa(double ka) {
        this.ka = new Double3(ka);
        return this;
    }
    // Setter for kd
    public Material setKd(Double3 kd) {
        this.kd = kd;
        return this;
    }

    public Material setKd(double kd) {
        this.kd = new Double3(kd);
        return this;
    }

    // Setter for ks
    public Material setKs(Double3 ks) {
        this.ks = ks;
        return this;

    }
    public Material setKs(double ks) {
        this.ks = new Double3(ks);
        return this;
    }

    // Setter for nsh
    public Material setShininess(int nsh) {
        this.nsh = nsh;
        return this;
    }
}
