package renderer;
import primitives.*;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class BlackBoard {
    private final int numSamples = 9;
    /**
     * The size of the blackboard in world units.
     * Default value is 0.5.
     */
    private double size = 0.5;
    private Vector vUp;
    private Vector vRight;
    private Point location;
    private Point head;
    private Vector normal = null; // if null, rays will not have a normal vector
    private boolean circular = false;

    public BlackBoard(Point head, double distance, Vector vUp, Vector vector) {
        this.vUp = vUp.normalize();
        vRight = vUp.crossProduct(vector).normalize();
        this.head = head;
        location = head.add(vector.scale(distance));
    }


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
                if(!circular || pIJ.distanceSquared(location) < size * size / 4) {
                    if(normal==null){
                        rays.add(new Ray(pIJ.subtract(head),head));
                    }
                    else {
                        rays.add(new Ray(pIJ.subtract(head),normal,head));
                    }

                }
            }
        }
        return rays;

    }

    public BlackBoard setCircular(boolean circular) {
        this.circular = circular;
        return this;
    }
    public BlackBoard setSize(double size) {
        this.size = size;
        return this;
    }

    public BlackBoard setNormal(Vector normal) {
        this.normal = normal;
        return this;
    }
}