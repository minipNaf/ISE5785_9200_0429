package renderer;

import primitives.*;
import scene.Scene;

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
    private Point pTarget = null; // Optional target point for the camera
    private Vector vTo;
    private Vector vUp;
    private Vector vRight;
    private double viewPlaneDistance = 0.0;
    private double viewPlaneWidth = 0.0;
    private double viewPlaneHeight = 0.0;


    private ImageWriter imageWriter;
    private RayTracerBase rayTracer;
    private int nX = 1;
    private int nY = 1;
    /**
     * Default empty constructor for the Camera class.
     */
    private Camera(){}

    /**
     * Static method to create a new Builder instance.
     * @return a new Builder object for constructing a Camera object.
     */
    public static Builder getBuilder() {
        return new Builder();
    }

    /**
     * Builder class for constructing a Camera object.
     * This class provides methods to set the camera's properties
     * such as location, view plane distance, size, and direction.
     */
    @SuppressWarnings("default-constructor")
    public static class Builder{

        /**
         * Default constructor for the Builder class.
         * This constructor initializes the camera object.
         */
        Builder () {}// Default constructor

        private final Camera camera = new Camera();

        /**
         * Set the location of the camera.
         * @param location - the location of the camera in 3D space
         * @return this Builder object
         */
        public Builder setLocation(Point location) {
            camera.p0 = location;
            return this;
        }

        /**
         * Set the distance from the camera to the view plane.
         * @param distance - the distance from the camera to the view plane
         * @return this Builder object
         */
        public Builder setVpDistance(double distance) {
            if (distance <= 0) {
                throw new IllegalArgumentException("Distance must be positive");
            }

            camera.viewPlaneDistance = distance;
            return this;
        }

        /**
         * Set the size of the view plane.
         * This method sets the width and height of the view plane.
         * @param width - the width of the view plane
         * @param height - the height of the view plane
         * @return this Builder object
         */
        public Builder setVpSize(int width, int height) {
            if (width <= 0 || height <= 0) {
                throw new IllegalArgumentException("Width and viewPlaneHeight must be positive");
            }

            camera.viewPlaneWidth = width;
            camera.viewPlaneHeight = height;
            return this;
        }

        /** set the Vto and Vup vectors
         *
         * @param to - the direction vector
         * @param up - the up vector
         * @return this Builder object
         */
        public Builder setDirection(Vector to, Vector up) {
            if (!Util.isZero(to.dotProduct(up))) {
                throw new IllegalArgumentException("vTo and vUp must be orthogonal");
            }
            camera.vTo = to.normalize();
            camera.vUp = up.normalize();
            return this;
        }

        /**
         * Set the direction of the camera using a target point and an up vector.
         * This method calculates the vTo, vRight, and vUp vectors based on the target point and up vector.
         * @param target1 - the target point in 3D space (where the camera is looking at)
         * @param up - the up vector
         * @return this Builder object
         */
        public Builder setDirection(Point target1, Vector up) {
            if (target1.equals(camera.p0))
                throw new IllegalArgumentException("target point cannot be the same as camera location");

            camera.pTarget = target1; // Store the target point
            camera.vTo = target1.subtract(camera.p0).normalize();
            camera.vRight = camera.vTo.crossProduct(up).normalize();
            camera.vUp = camera.vRight.crossProduct(camera.vTo).normalize();

            return this;
        }

        /**
         * Set the direction of the camera using a target point.
         * This method calculates the vTo, vRight, and vUp vectors based on the target point.
         * @param target1 - the target point in 3D space (where the camera is looking at)
         * @return this Builder object
         */
        public Builder setDirection(Point target1) {
            if (target1.equals(camera.p0))
                throw new IllegalArgumentException("target point cannot be the same as camera location");
            camera.pTarget = target1; // Store the target point
            camera.vTo = target1.subtract(camera.p0).normalize();
            camera.vUp = Vector.AXIS_Y; // Default up vector: used to find the right vector
            camera.vRight = camera.vTo.crossProduct(camera.vUp).normalize();
            camera.vUp = camera.vRight.crossProduct(camera.vTo).normalize(); // Recalculate vUp based on vTo and vRight
            return this;
        }

        /**
         * Set the resolution of the camera.
         * This method sets the number of pixels in the x and y directions.
         * @param nX - number of pixels in the x direction
         * @param nY - number of pixels in the y direction
         * @return this Builder object (will be done in the future)
         */
        public Builder setResolution(int nX, int nY){
            if(nX <= 0 || nY <= 0) {
                throw new IllegalArgumentException("Width and viewPlaneHeight must be positive");
            }
            camera.nX = nX;
            camera.nY = nY;
            return this;
        }

        /**
         * Build the Camera object.
         * This method checks for missing values and validates the camera properties.
         * @param scene - the scene to be rendered
         * @param rayTracerType - the type of ray tracer to be used
         * @return a new Camera object
         * @throws MissingResourceException if any required values are missing
         */
        public Builder setRayTracer(Scene scene, RayTracerType rayTracerType) {
            if(rayTracerType == RayTracerType.SIMPLE) {
                camera.rayTracer = new SimpleRayTracer(scene);
            }
            else{
                camera.rayTracer = null;
            }
            return this;
        }

        public Builder move(Vector vector) {
            if (vector == null) {
                throw new IllegalArgumentException("Vector cannot be null");
            }
            camera.p0 = camera.p0.add(vector);
            camera.vTo = camera.pTarget.subtract(camera.p0).normalize();
            camera.vUp = camera.vTo.crossProduct(camera.vUp.crossProduct(camera.vTo)).normalize();
            return this;
        }

        public Builder rotate(double angle) {
            // Rotate the camera's position and direction vectors around vTo
            double radians = -Math.toRadians(angle);
            Vector vRight = camera.vTo.crossProduct(camera.vUp).normalize();
            camera.vUp = camera.vUp.scale(Math.cos(radians))
                    .add(vRight.scale(Math.sin(radians)));
            return this;
        }

        /**
         * Build the Camera object.
         * This method checks for missing values and validates the camera properties.
         * @return a new Camera object
         * @throws MissingResourceException if any required values are missing
         */
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
            if(camera.nX <= 0 || camera.nY <= 0) throw new IllegalArgumentException("Width and viewPlaneHeight must be positive");

            camera.vRight = camera.vTo.crossProduct(camera.vUp).normalize();

            camera.imageWriter = new ImageWriter(camera.nX, camera.nY);
            //if not set, use simple ray tracer and an empty scene
            if(camera.rayTracer == null) {
                camera.rayTracer = new SimpleRayTracer(null);
            }

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


    /**
     * Constructs a ray from the camera to a specific pixel on the view plane.
     * This method calculates the coordinates of the pixel and creates a ray
     * that points from the camera to that pixel.
     * each pixel is represented as [i,j].
     * @param nX - number of pixels in the x direction
     * @param nY - number of pixels in the y direction
     * @param j - pixel index in the x direction
     * @param i - pixel index in the y direction
     * @return a Ray object representing the ray from the camera to the specified pixel
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        double Xj = (j - (nX-1) / 2d) * (viewPlaneWidth / nX);
        double Yi = -(i - (nY-1) / 2d) * (viewPlaneHeight / nY);

        // calculate the point in the center of the view plane
        Point pCenter = p0.add(vTo.scale(viewPlaneDistance));
        Point pIJ = pCenter;

        // we are calculating the ray through the pixel in three stages so we won't have a problem of zero vector.
        if (Xj != 0) pIJ = pIJ.add(vRight.scale(Xj));
        if (Yi != 0) pIJ = pIJ.add(vUp.scale(Yi));
        return new Ray(pIJ.subtract(p0).normalize(), p0);
    }
    /**
     *the function casts rays through every pixel on the view plane
     * @return this camera object
     */
    public Camera renderImage() {
        for (int i = 0; i < nY; i++) {
            for (int j = 0; j < nX; j++) {
                castRay(j, i);
            }
        }
        return this;
    }
    /**
     * Prints a grid on the image.
     * The grid is drawn at specified intervals and uses the specified color.
     * @param interval - the interval at which to draw the grid lines
     * @param color - the color of the grid lines
     * @return this Camera object
     */
    public Camera printGrid(int interval, Color color) {
        for (int i = 0; i < nY; i++) {
            for (int j = 0; j < nX; j++) {
                // Check if the pixel is on the grid line
                if (i % interval == 0 || j % interval == 0) {
                    imageWriter.writePixel(j, i, color);
                }
            }
        }
        return this;
    }
    /**
     * Writes the image to a file with the specified name.
     * The image is saved in the specified format (e.g., PNG).
     * @param ImageName - the name of the image file
     * @return this Camera object
     */
    public Camera writeToImage(String ImageName) {
        imageWriter.writeToImage(ImageName);
        return this;
    }

    /**
     * Casts a ray through a specific pixel on the view plane and writes the color to the image.
     * This method constructs a ray for the specified pixel and uses the ray tracer to get the color.
     * @param j - pixel index in the x direction
     * @param i - pixel index in the y direction
     */
    private void castRay(int j, int i){
        Ray ray = constructRay(nX, nY, j, i);
        Color color = rayTracer.traceRay(ray);
        imageWriter.writePixel(j, i, color);
    }

    public Builder getBuilder(Camera old) {
        return new Builder()
                .setRayTracer(old.rayTracer.scene, RayTracerType.SIMPLE)
                .setLocation(old.p0)
                .setVpDistance(old.viewPlaneDistance)
                .setVpSize((int) old.viewPlaneWidth, (int) old.viewPlaneHeight)
                .setDirection(old.pTarget, old.vUp)
                .setResolution(old.nX, old.nY);
    }
}