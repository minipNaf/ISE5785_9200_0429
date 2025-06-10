package renderer;

import primitives.*;
import scene.Scene;

import java.util.ArrayList;
import java.util.stream.*;
import java.util.LinkedList;
import java.util.List;
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
    private boolean antiAliasing = false; // Factor for anti-aliasing, default is infinity (no anti-aliasing) p
    private ImageWriter imageWriter;
    private RayTracerBase rayTracer;
    private double dOFdistance = 0; // Distance aperture window to focal plane
    private int nX = 1;
    private int nY = 1;
    /** Amount of threads to use fore rendering image by the camera */
    private int threadsCount = 0;
    /**
     * Amount of threads to spare for Java VM threads:<br>
     * Spare threads if trying to use all the cores
     */
    private static final int SPARE_THREADS = 2;
    /**
     * Debug print interval in seconds (for progress percentage)<br>
     * if it is zero - there is no progress output
     */
    private double printInterval = 0;
    /**
     * Pixel manager for supporting:
     * <ul>
     * <li>multi-threading</li>
     * <li>debug print of progress percentage in Console window/tab</li>
     * </ul>
     */
    private PixelManager pixelManager;
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

        /**
         * Move the camera by a specified vector.
         * This method updates the camera's position and recalculates the vTo and vUp vectors.
         * @param vector - the vector by which to move the camera
         * @return this Builder object
         * @throws IllegalArgumentException if the vector is null
         */
        public Builder move(Vector vector) {
            if (vector == null) {
                throw new IllegalArgumentException("Vector cannot be null");
            }
            camera.p0 = camera.p0.add(vector);
            camera.vTo = camera.pTarget.subtract(camera.p0).normalize();
            camera.vUp = camera.vTo.crossProduct(camera.vUp.crossProduct(camera.vTo)).normalize();
            return this;
        }

        /**
         * Rotate the camera around the vTo vector by a specified angle.
         * This method updates the vUp vector based on the rotation angle.
         * @param angle - the angle in degrees by which to rotate the camera
         * @return this Builder object
         */

        public Builder rotate(double angle) {
            // Rotate the camera's position and direction vectors around vTo
            double radians = Math.toRadians(angle);
            Vector vRight = camera.vTo.crossProduct(camera.vUp).normalize();
            camera.vUp = camera.vUp.scale(Math.cos(radians))
                    .add(vRight.scale(Math.sin(radians)));
            return this;
        }

        /**
         * Set anti-aliasing for the camera.
         * If anti-aliasing is enabled, the camera will generate multiple rays per pixel
         * to reduce aliasing artifacts in the rendered image.
         * @param antiAliasing - true to enable anti-aliasing, false to disable it
         * @return this Builder object
         */
        public Builder setAntiAliasing(boolean antiAliasing) {
            camera.antiAliasing = antiAliasing;
            return this;
        }
        /**
         * Set multi-threading <br>
         * Parameter value meaning:
         * <ul>
         * <li>-2 - number of threads is number of logical processors less 2</li>
         * <li>-1 - stream processing parallelization (implicit multi-threading) is used</li>
         * <li>0 - multi-threading is not activated</li>
         * <li>1 and more - literally number of threads</li>
         * </ul>
         * @param threads number of threads
         * @return builder object itself
         */
        public Builder setMultithreading(int threads) {
            if (threads < -3)
                throw new IllegalArgumentException("Multithreading parameter must be -2 or higher");
            if (threads == -2) {
                int cores = Runtime.getRuntime().availableProcessors() - SPARE_THREADS;
                camera.threadsCount = cores <= 2 ? 1 : cores;
            } else
                camera.threadsCount = threads;
            return this;
        }

        /**
         * Set the distance from the focal plane to the aperture window.
         * This method is used for depth of field effects in rendering.
         * @param distance - the distance from the focal plane to the aperture window
         * @return this Builder object
         * @throws IllegalArgumentException if the distance is not positive
         */
        public Builder setApertureWindow(double distance) { //The distance from the focal plain to the apertue window
            if(distance<=0) {
                throw new IllegalArgumentException("Distance must be positive");
            }
            camera.dOFdistance = distance;
            return this;
        }

        /**
         * Set debug printing interval. If it's zero - there won't be printing at all
         * @param interval printing interval in %
         * @return builder object itself
         */
        public Builder setDebugPrint(double interval) {
            if (interval < 0) throw new IllegalArgumentException("interval parameter must be non-negative");
            camera.printInterval = interval;
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
    public List<Ray> constructRay(int nX, int nY, int j, int i) {
        double Xj = (j - (nX-1) / 2d) * (viewPlaneWidth / nX);
        double Yi = -(i - (nY-1) / 2d) * (viewPlaneHeight / nY);
        List<Ray> rays;
        // calculate the point in the center of the view plane
        Point pCenter = p0.add(vTo.scale(viewPlaneDistance));
        Point pIJ = pCenter;

        // we are calculating the ray through the pixel in three stages so we won't have a problem of zero vector.
        if (Xj != 0) pIJ = pIJ.add(vRight.scale(Xj));
        if (Yi != 0) pIJ = pIJ.add(vUp.scale(Yi));
        Ray pixelRay = new Ray(pIJ.subtract(p0).normalize(), p0);
        if (!antiAliasing) {
            rays = List.of(pixelRay);
        }
        else{
            Vector pixelDirection = pixelRay.getDirection();
            BlackBoard blackBoard = new BlackBoard(p0, pIJ.distance(p0), vUp, pixelDirection).setSize(viewPlaneWidth/nX);
            rays = blackBoard.castRays();
        }
        // if there is depth of field, we need to adjust the rays to pass through the aperture window
        if(dOFdistance != 0) {
            BlackBoard apertureWindow = new BlackBoard(p0.add(vTo.scale(dOFdistance)),
                    dOFdistance, vUp.scale(-1), vTo.scale(-1))
                    .setCircular(true).setDoF(true).setSize(dOFdistance/2);
            List<Ray> apertureRays = new ArrayList<>();
            // for each ray, we cast rays through the aperture window
            for (Ray ray : rays){
                    apertureRays.addAll(apertureWindow.setSingle(ray.getPoint(dOFdistance / vTo.dotProduct(ray.getDirection()))).castRays());
            }
            return apertureRays;
        }
        return rays;
    }
    /**
     *the function casts rays through every pixel on the view plane
     * @return this camera object
     */
    public Camera renderImage() {
        pixelManager = new PixelManager(nY, nX, printInterval);
        return switch (threadsCount) {
            case 0 -> renderImageNoThreads();
            case -1 -> renderImageStream();
            default -> renderImageRawThreads();
        };
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
        List<Ray> pixelRays = constructRay(nX, nY, j, i);
        Color color = Color.BLACK; // Default color if no rays are traced
        // there are many rays if anti-aliasing is enabled or depth of field is enabled
        for (Ray pixelRay : pixelRays) {
            color = color.add(rayTracer.traceRay(pixelRay));
        }
        imageWriter.writePixel(j, i, color.scale(1d /pixelRays.size()));
        pixelManager.pixelDone();
    }

    /**
     * Returns a Builder object initialized with the properties of the given Camera object.
     * This method is useful for creating a new Camera object based on an existing one.
     * @param old - the Camera object to copy properties from
     * @return a Builder object with the properties of the old Camera
     */
    public Builder getBuilder(Camera old) {
        return new Builder()
                .setRayTracer(old.rayTracer.scene, RayTracerType.SIMPLE)
                .setLocation(old.p0)
                .setVpDistance(old.viewPlaneDistance)
                .setVpSize((int) old.viewPlaneWidth, (int) old.viewPlaneHeight)
                .setDirection(old.pTarget, old.vUp)
                .setResolution(old.nX, old.nY)
                .setAntiAliasing(old.antiAliasing);
    }
    /**
     * Render image using multi-threading by parallel streaming
     * @return the camera object itself
     */
    private Camera renderImageStream() {
        IntStream.range(0, nY).parallel()
                .forEach(i -> IntStream.range(0, nX).parallel()
                        .forEach(j -> castRay(j, i)));
        return this;
    }
    /**
     * Render image without multi-threading
     * @return the camera object itself
     */
    private Camera renderImageNoThreads() {
        for (int i = 0; i < nY; ++i)
            for (int j = 0; j < nX; ++j)
                castRay(j, i);
        return this;
    }
    /**
     * Render image using multi-threading by creating and running raw threads
     * @return the camera object itself
     */
    private Camera renderImageRawThreads() {
        var threads = new LinkedList<Thread>();
        while (threadsCount-- > 0)
            threads.add(new Thread(() -> {
                renderer.PixelManager.Pixel pixel;
                while ((pixel = pixelManager.nextPixel()) != null)
                    castRay(pixel.col(), pixel.row());
            }));
        for (var thread : threads) thread.start();
        try {
            for (var thread : threads) thread.join();
        } catch (InterruptedException ignored) {}
        return this;
    }
}