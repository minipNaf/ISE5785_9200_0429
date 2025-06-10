package renderer;
import primitives.*;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;

/**
 * The BlackBoard class represents a virtual blackboard in 3D space.
 * It is used to cast rays for rendering purposes, such as glossy and diffuse surfaces, anti-aliasing,
 * soft shadows, and depth of field.
 * The blackboard is defined by a single point, an up vector, a direction vector, and a size.
 */
public class BlackBoard {
    /**
     * The number of samples per pixel.
     * Default value is 17, which means 17x17=289 grid of samples.
     */
    private final int numSamples = 17;
    /**
     * The size of the blackboard in world units.
     * Default value is 0.5.
     */
    private double size = 0.5;
    /**
     * The up vector of the blackboard.
     * This vector is used to determine the orientation of the blackboard in 3D space.
     */
    private Vector vUp;
    /**
     * The right vector of the blackboard, calculated as the cross product of the up vector and the direction vector.
     * This vector is used to determine the horizontal orientation of the blackboard in 3D space.
     */
    private Vector vRight;
    /**
     * The location of the blackboard in 3D space.
     * This point represents the center of the blackboard.
     */
    private Point location;
    /**
     * The single point from which rays will be cast or rays will be cast to.
     * This point is the focal point or origin of blackboard.
     */
    private Point single;
    /**
     * The normal vector of the blackboard.
     * This vector is used to determine if to move single on the normal vector or not.
     */
    private Vector normal = null;
    /**
     * A flag indicating whether the blackboard is circular.
     * If true, rays will only be cast within a circular area defined by the size.
     */
    private boolean circular = false;
    /**
     * A flag indicating whether to use depth of field.
     * If true, rays will be cast with depth of field effect, meaning they start from blackboard and not from single.
     */
    private boolean dof = false; // if true, rays will be cast with depth of field

    /**
     * Constructs a BlackBoard object with the specified parameters.
     * The blackboard is defined by a head point, distance from the head, up vector, and a direction vector.
     *
     * @param single    This point is the focal point or origin of blackboard.
     * @param distance  The distance from the head to the blackboard.
     * @param vUp       The up vector of the blackboard, used to determine its orientation.
     * @param vector    The direction vector used to calculate the right vector of the blackboard.
     */
    public BlackBoard(Point single, double distance, Vector vUp, Vector vector) {
        this.vUp = vUp.normalize();
        vRight = vUp.crossProduct(vector).normalize();
        this.single = single;
        location = single.add(vector.normalize().scale(distance));

    }


    /**
     * Casts rays from the blackboard based on the specified parameters.
     * The rays are cast in a grid pattern defined by the number of samples and the size of the blackboard.
     * If circular is true, rays are only cast within a circular area defined by the size.
     * If dof is true, rays are cast with depth of field effect.
     *
     * @return a list of Ray objects representing the rays cast from the blackboard or to the blackboard.
     */
    public List<Ray> castRays() {

        List<Ray> rays = new ArrayList<>();
        Point pIJ;

        double cellSize = size / numSamples;
        double Xj, Yi;
        double jitteration;
        for (int i = 0; i < numSamples; i++) {
            for(int j = 0; j < numSamples; j++) {

                jitteration = Double.POSITIVE_INFINITY;
                Xj = (j - (numSamples-1) / 2d) * (cellSize);
                Random rand = new Random();

                while(Math.abs(jitteration) > cellSize/2){
                    jitteration = rand.nextGaussian() * cellSize / 4;
                }
                Xj += jitteration;

                Yi = -(i - (numSamples-1) / 2d) * (cellSize);

                jitteration = Double.POSITIVE_INFINITY;
                while(Math.abs(jitteration) > cellSize/2){
                    jitteration = rand.nextGaussian() * cellSize / 4;
                }
                Yi += jitteration;

                pIJ = location;

                // we are calculating the ray through the pixel in three stages so we won't have a problem of zero vector.
                if (Xj != 0) pIJ = pIJ.add(vRight.scale(Xj));
                if (Yi != 0) pIJ = pIJ.add(vUp.scale(Yi));

                // if the blackboard is circular, we check if the point is inside the circle
                if(!circular || pIJ.distanceSquared(location) < size * size / 4) {
                    if (dof)
                        rays.add(new Ray(single.subtract(pIJ), pIJ));
                    else if (normal == null) { // this is the case of antialiasing
                        rays.add(new Ray(pIJ.subtract(single), single));
                    }
                         else // this is the case of soft shadows, glossy and diffuse surfaces
                            rays.add(new Ray(pIJ.subtract(single), normal, single));
                }
            }
        }
        return rays;
    }

    /**
     * setter function for the circular flag.
     * @param circular - if true, rays will only be cast within a circular area defined by the size.
     * @return this - for chaining method calls.
     */
    public BlackBoard setCircular(boolean circular) {
        this.circular = circular;
        return this;
    }
    /**
     * setter function for the size of the blackboard.
     * @param size - the size of the blackboard in world units.
     * @return this - for chaining method calls.
     */
    public BlackBoard setSize(double size) {
        this.size = size;
        return this;
    }
    /**
     * setter function for the normal vector of surface from which we send a ray to blackboard.
     * @param normal - normal vector of surface from which we send a ray to blackboard.
     * @return this - for chaining method calls.
     */
    public BlackBoard setNormal(Vector normal) {
        this.normal = normal;
        return this;
    }
    /**
     * setter function for the depth of field flag.
     * @param dof - if true, rays will be cast with depth of field effect.
     * @return this - for chaining method calls.
     */
    public BlackBoard setDoF(boolean dof) {
        this.dof = dof;
        return this;
    }

    /**
     * setter function for the single point from which rays will be cast or rays will be cast to.
     * @param single - the single point from which rays will be cast or rays will be cast to.
     * @return this - for chaining method calls.
     */
    public BlackBoard setSingle(Point single){
        this.single = single;
        return this;
    }
}