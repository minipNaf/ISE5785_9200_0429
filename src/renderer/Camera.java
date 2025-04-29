package renderer;
import primitives.*;

import java.util.MissingResourceException;

//import static primitives.Util.isZero;

/**
 * The Camera class represents a camera in a 3D space.
 * It is defined by a position point (p0), a direction vector (vTo),
 * an up vector (vUp), and a right vector (vRight).
 * The camera also has properties for distance, width, and viewPlaneHeight.
 */
// The camera is used to generate rays for rendering scenes in computer graphics.
public class Camera implements Cloneable{
    private Point p0;
    private Vector vTo;
    private Vector vUp;
    private Vector vRight;
    private double viewPlaneDistance = 0.0;
    private double viewPlaneWidth = 0.0;
    private double viewPlaneHeight = 0.0;
    // View plane center point to save CPU time – it’s always the same
    private Point viewPlanePC;

    private Camera(){}

    public static Builder getBuilder() {
        return new Builder();
    }

    public static class Builder{
        private final Camera camera = new Camera();

        public Builder setLocation(Point location) {
            camera.p0 = location;
            return this;
        }


        public Builder setVpDistance(double distance) {
            if (distance <= 0) {
                throw new IllegalArgumentException("Distance must be positive");
            }

            camera.viewPlaneDistance = distance;
            return this;
        }

        public Builder setVpSize(int width, int height) {
            if (width <= 0 || height <= 0) {
                throw new IllegalArgumentException("Width and viewPlaneHeight must be positive");
            }

            camera.viewPlaneWidth = width;
            camera.viewPlaneHeight = height;
            return this;
        }

        public Builder setDirection(Vector to, Vector up) {
            if (!Util.isZero(to.dotProduct(up))) {
                throw new IllegalArgumentException("vTo and vUp must be orthogonal");
            }
            camera.vTo = to.normalize();
            camera.vUp = up.normalize();
            return this;
        }

        public Builder setDirection(Point target1, Vector up) {
            if (target1.equals(camera.p0))
                throw new IllegalArgumentException("target point cannot be the same as camera location");

            camera.vTo = target1.subtract(camera.p0).normalize();
//            if (!Util.isZero(camera.vTo.dotProduct(up))) {
//                throw new IllegalArgumentException("vTo and vUp must be orthogonal");
//            }
            //camera.vUp = up.normalize();
            camera.vRight = camera.vTo.crossProduct(up).normalize();
            camera.vUp = camera.vRight.crossProduct(camera.vTo).normalize();
            return this;
        }

        public Builder setDirection(Point target1) {
            if (target1.equals(camera.p0))
                throw new IllegalArgumentException("target point cannot be the same as camera location");

            camera.vTo = target1.subtract(camera.p0).normalize();
            camera.vUp = Vector.AXIS_Y;

//            if (!Util.isZero(camera.vTo.dotProduct(camera.vUp))) {
//                throw new IllegalArgumentException("vTo and vUp must be orthogonal");
//            }

            camera.vRight = camera.vTo.crossProduct(camera.vUp).normalize();
            camera.vUp = camera.vRight.crossProduct(camera.vTo).normalize();
            return this;
        }

        public Builder setResolution(int nX, int nY){ return null;}

        public Camera build() {
            final String className = "Camera";
            final String description = "missing values: ";
            if (camera.p0 == null) throw new MissingResourceException(description, className, "p0");
            if (camera.vUp == null) throw new MissingResourceException(description, className, "vUp");
            if (camera.vTo == null) throw new MissingResourceException(description, className, "vTo");
            if (Util.alignZero(camera.viewPlaneWidth) <= 0) throw new IllegalArgumentException("Width must be positive");
            if (Util.alignZero(camera.viewPlaneHeight) <= 0) throw new IllegalArgumentException("Height must be positive");
            if (Util.alignZero(camera.viewPlaneDistance) <= 0) throw new IllegalArgumentException("Distance must be positive");
            if (!Util.isZero(camera.vTo.dotProduct(camera.vUp))) throw new IllegalArgumentException("vTo and vUp must be orthogonal");

            camera.vRight = camera.vTo.crossProduct(camera.vUp).normalize();
            camera.viewPlanePC = camera.p0.add(camera.vTo.scale(camera.viewPlaneDistance));

            return camera.clone(); // Cloneable – get a full shadow copy
        }
    }

    @Override
    public Camera clone() {
        try {
            return (Camera) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(); // Should never happen
        }
    }


//    public Camera(Point p0, Vector vTo, Vector vUp) {
//        this.p0 = p0;
//        this.vTo = vTo.normalize();
//        this.vUp = vUp.normalize();
//        this.vRight = this.vTo.crossProduct(this.vUp).normalize();
//    }

    public Ray constructRay(int nX, int nY, int j, int i) {
        double Xj = (j - (nX-1) / 2d) * (viewPlaneWidth / nX);
        double Yi = -(i - (nY-1) / 2d) * (viewPlaneHeight / nY);

        Point pCenter = p0.add(vTo.scale(viewPlaneDistance));
        Point pIJ = pCenter;
        if (Xj != 0) pIJ = pIJ.add(vRight.scale(Xj));
        if (Yi != 0) pIJ = pIJ.add(vUp.scale(Yi));
        return new Ray(pIJ.subtract(p0).normalize(), p0);
    }

}
