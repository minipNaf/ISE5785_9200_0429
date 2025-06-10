//package renderer;
//
//import lighting.DirectionalLight;
//import lighting.LightSource;
//import lighting.PointLight;
//import primitives.*;
//import scene.Scene;
//import geometries.Intersectable. Intersection;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Simple ray tracer implementation that extends the RayTracerBase class.
// * This class provides a basic ray tracing algorithm that calculates the color
// * at the intersection point of a ray with the scene's geometries.
// */
//public class SimpleRayTracer extends RayTracerBase{
//
//    /**
//     * Maximum level of color calculation for recursive effects.
//     * This value limits the depth of recursion when calculating color effects such as reflections and refractions.
//     */
//    private static final int MAX_CALC_COLOR_LEVEL = 10;
//    /**
//     * Minimum value for color calculation to avoid division by zero.
//     * This value is used to ensure that the color calculations do not result in zero or negative values.
//     */
//    private static final double MIN_CALC_COLOR_K = 0.001;
//    /**
//     * Initial value for the color calculation factor.
//     * This value is used as a starting point for color calculations in the ray tracing algorithm.
//     */
//    private static final Double3 INITIAL_K = Double3.ONE;
//
//    /**
//     * Constructs a SimpleRayTracer object with the specified scene.
//     * This constructor initializes the ray tracer with the given scene.
//     *
//     * @param scene the scene to be rendered
//     */
//    public SimpleRayTracer(Scene scene) {
//        super(scene);
//    }
//
//    /**
//     * Traces a ray through the scene and returns the color at the intersection point.
//     * If there are no intersections, it returns the background color of the scene.
//     *
//     * @param ray the ray to trace
//     * @return the color at the intersection point (or the background color if no intersection)
//     */
//    @Override
//    public Color traceRay(Ray ray) {
//        List<Intersection> intersections = scene.geometries.calculateIntersectionsHelper(ray, Double.POSITIVE_INFINITY);
//        if (intersections == null)
//            return scene.background;
//
//        return calcColor(findClosestIntersection(ray), ray);
//    }
//
//    /**
//     * Calculates the color at a given point in the scene.
//     * This method the color of a given point using the recursive calcColor
//     *
//     * @param intersection the intersection at which to calculate the color
//     * @return the color at the specified point
//     */
//    private Color calcColor(Intersection intersection, Ray ray) {
//        return calcColor(intersection, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K).
//                add(scene.ambientLight.getIntensity().scale(intersection.material.ka));
//    }
//
//    /**
//     * recursive calcColor. calculates the local effects color of intersection point and adds the global effects.
//     * @param intersection - intersection point on geometry
//     * @param ray - hitting ray
//     * @param level - level of recursive call
//     * @param k - current mekadem hanhata of global effect
//     * @return color of intersection point
//     */
//    private Color calcColor(Intersection intersection, Ray ray, int level, Double3 k){
//        if (!preprocessIntersection(intersection, ray.getDirection()))
//            return Color.BLACK;
//        Color color = calcColorLocalEffects(intersection, k);
//        return 1 == level ? color : color.add(calcGlobalEffects(intersection, ray, level, k));
//    }
//
//    /**
//     * Preprocesses the intersection by calculating the normal vector and the view vector.
//     * This method also checks if the intersection is valid (not zero).
//     *
//     * @param intersection the intersection to preprocess
//     * @param v the view vector
//     * @return true if the intersection is valid, false otherwise
//     */
//    private boolean preprocessIntersection(Intersection intersection, Vector v) {
//        intersection.v = v;
//        intersection.normal = intersection.geometry.getNormal(intersection.point);
//        intersection.vNormal = Util.alignZero(intersection.v.dotProduct(intersection.normal));
//
//        return intersection.vNormal != 0;
//    }
//
//    /**
//     * Sets the light source for the intersection and calculates the light vector.
//     * This method also checks if the light source is valid (not zero).
//     *
//     * @param intersection the intersection to set the light source for
//     * @param light the light source to set
//     * @return true if the light source is valid, false otherwise
//     */
//    private boolean setLightSource(Intersection intersection, LightSource light) {
//        intersection.light = light;
//        intersection.l = light.getL(intersection.point);
//        intersection.lNormal = Util.alignZero(intersection.l.dotProduct(intersection.normal));
//
//        return intersection.vNormal * intersection.vNormal <= 0;
//    }
//
//    /**
//     * Calculates the color at the intersection point based on local effects.
//     * This method considers the emission color of the geometry and the intensity
//     * of the light sources in the scene.
//     *
//     * @param gp the intersection point
//     * @return the calculated color at the intersection point
//     */
//    private Color calcColorLocalEffects(Intersection gp, Double3 k)
//    {
//        Color color = gp.geometry.getEmission(); // emission color of geometry
//
//        for (LightSource lightSource : scene.lights) {
//            if (!setLightSource(gp, lightSource) && gp.lNormal * gp.vNormal > 0) { // sign(nl) == sign(nv)
//                Double3 ktr = transparency(gp);
//                if (!ktr.product(k).lowerThan(MIN_CALC_COLOR_K)) {
//                    Color iL = lightSource.getIntensity(gp.point).scale(ktr); // intensity of color at point
//                    // adding diffusive and specular effects
//                    color = color.add(iL.scale(calcDiffusive(gp).add(calcSpecular(gp))));
//                }
//            }
//        }
//        return color;
//    }
//
//
//    /**
//     * The function calculates specular effects at the intersection point.
//     * @param intersection - the intersection point
//     * @return the specular color at the intersection point
//     */
//    private Double3 calcSpecular(Intersection intersection){
//        // r = l - (n * lNormal * 2)
//        Vector r = intersection.l.subtract(intersection.normal.scale(2 * intersection.lNormal));
//        double rv = Util.alignZero(r.dotProduct(intersection.v));
//        // check if the angle between the view vector and the reflection vector is acute
//        if (rv < 0d) {
//            return intersection.material.ks.scale(Math.pow(-rv, intersection.material.nsh));
//        }
//        // if the angle is obtuse, return zero
//        return Double3.ZERO;
//    }
//
//    /**
//     * The function calculates diffusive effects at the intersection point.
//     * @param intersection - the intersection point
//     * @return the diffusive color at the intersection point
//     */
//    private Double3 calcDiffusive(Intersection intersection){
//        return intersection.material.kd.scale(Math.abs(intersection.lNormal));
//    }
//
//
//    /**
//     * The function calculates how much of the light from the light source reaches the intersection.
//     * Each body shading over the intersection reduces the amount of light at intersection.
//     * @param intersection - intersection to check level of light
//     * @return ktr - final mekadem hanhata of transparency
//     */
//    private Double3 transparency(Intersection intersection) {
//        Vector pointToLight = intersection.l.scale(-1);
//        double lightDistance = intersection.light.getDistance(intersection.point);
//        List<Ray> shadowRays;
//        if (intersection.light.getRadius() == 0)
//            shadowRays = List.of(new Ray(pointToLight, intersection.normal, intersection.point));
//        else {
//            //Point offsetPoint = intersection.point.add(intersection.normal.scale(0.1));
//            BlackBoard blackBoard = new BlackBoard(intersection.point, lightDistance, pointToLight.getNormal(), pointToLight);
//            blackBoard.setSize(intersection.light.getRadius()*2).setNormal(intersection.normal).setCircular(true);
//            shadowRays = blackBoard.castRays();
//        }
//
//        Double3 ktr;
//        Double3 averageKtr = Double3.ZERO;
//        List<Intersection> intersections;
//        for (Ray shadowRay : shadowRays) {
//            intersections = scene.geometries.calculateIntersectionsHelper(shadowRay, lightDistance);
//            ktr = Double3.ONE;
//            if (intersections != null) {
//                for (Intersection i : intersections) {
//                    ktr = ktr.product(i.material.kt);
//                }
//            }
//            averageKtr = averageKtr.add(ktr);
//
//        }
//        return averageKtr.reduce(shadowRays.size());
//    }
//
////    private boolean unshaded(Intersection intersection){
////        Vector pointToLight = intersection.l.scale(-1); // from point to light source
////        private static final double DELTA = 0.1;
////        Vector delta = intersection.normal.scale(intersection.lNormal < 0 ? DELTA : -DELTA);
////        Ray shadowRay = new Ray(pointToLight, intersection.point.add(delta));
////        double lightDistance = intersection.light.getDistance(intersection.point);
////        var intersections = scene.geometries.calculateIntersectionsHelper(shadowRay, lightDistance);
////        if (intersections == null) return true;
////        intersections.removeIf(i -> !i.geometry.getMaterial().kt.lowerThan(MIN_CALC_COLOR_K));
////        return intersections == null; // no intersections, so the point is unshaded
////    }
//
//    /**
//     * Calculate refraction ray, and to it the right delta
//     * @param ray - hitting ray
//     * @param intersection - intersection point on geometry
//     * @return refraction ray
//     */
//    private List<Ray> refractionRay(Ray ray, Intersection intersection) {
//        if (intersection.material.diffusion == Double.POSITIVE_INFINITY) {
//            return List.of(new Ray(ray.getDirection(), intersection.normal, intersection.point));
//        }
//        BlackBoard blackBoard = new BlackBoard(intersection.point, intersection.material.diffusion,
//                ray.getDirection().getNormal(), ray.getDirection()).setNormal(intersection.normal);
//        return blackBoard.castRays();
//    }
//    /**
//     * Calculate reflection ray, and to it the right delta
//     * @param ray - hitting ray
//     * @param intersection - intersection point on geometry
//     * @return reflection ray
//     */
//    private List<Ray> reflectionRay(Ray ray, Intersection intersection) {
//        Vector v = ray.getDirection();
//        Vector r = v.subtract(intersection.normal.scale(2*v.dotProduct(intersection.normal)));
//
//        if (intersection.material.glossure == Double.POSITIVE_INFINITY) {
//            return List.of(new Ray(r, intersection.normal, intersection.point));
//        }
//
//        // For BlackBoard, pass the offset point
//        //Point offsetPoint = intersection.point.add(intersection.normal.scale(-0.1));
//        BlackBoard blackBoard = new BlackBoard(intersection.point, intersection.material.glossure,
//                r.getNormal(), r).setNormal(intersection.normal);
//        return blackBoard.castRays();
//    }
//
//
//    /**
//     * Calculate global effect for either reflection or transperancy.
//     * @param rays - ray hitting intersection point
//     * @param level - depth of recursive calls
//     * @param initialK - initial mekadem hanhata of reflection or transperancy
//     * @param kx - mekadem hanhata of currnent material
//     * @return color of intersection point from global effect
//     */
//    private Color calcGlobalEffect(List<Ray> rays, int level, Double3 initialK, Double3 kx) {
//        Double3 kkx = initialK.product(kx);
//        Intersection intersection;
//        if (kkx.lowerThan(MIN_CALC_COLOR_K)) {
//            return Color.BLACK;
//        }
//        Color global = new Color(); //Black final cannot be changed so
//
//        for (Ray ray : rays) {
//            intersection = findClosestIntersection(ray);
//            if (intersection == null) global = global.add(scene.background.scale(kx));
//            else if (preprocessIntersection(intersection, ray.getDirection())) {
//                global = global.add(calcColor(intersection, ray, level - 1, kkx).scale(kx));
//            }
//        }
//
//        return global.scale(1D / rays.size());
//    }
//
//    /**
//     * Calculates the color from global effects of intersection point
//     * @param intersection - intersection point on geometry
//     * @param ray - hitting ray
//     * @param level - level of recursive calls
//     * @param k - currnt mekadem hanhata of global effect
//     * @return  color from global effects of intersection point
//     */
//    private Color calcGlobalEffects(Intersection intersection, Ray ray, int level, Double3 k) {
//        return calcGlobalEffect(refractionRay(ray, intersection), level, k, intersection.material.kt)
//                .add(calcGlobalEffect(reflectionRay(ray, intersection), level, k, intersection.material.kr));
//    }
//
//    /**
//     * Calculates a list of intersections of a ray with geometries.
//     * Then finds the closest point from a list of points to the ray's head.
//     * The method iterates through the list of points and calculates the squared distance
//     * from the ray's head to each point, keeping track of the closest point found so far.
//     *
//     * @param ray - the ray to check for intersections
//     * @return the closest point to the ray's head, or null if the list is null
//     */
//    private Intersection findClosestIntersection(Ray ray) {
//        List<Intersection> intersections = scene.geometries.calculateIntersectionsHelper(ray, Double.POSITIVE_INFINITY);
//        if (intersections == null) {
//            return null; // No intersection found
//        }
//
//        Intersection closestPoint = intersections.getFirst();
//        double minDistance = ray.getHead().distanceSquared(closestPoint.point);
//
//        for (int i = 1; i < intersections.size(); i++) {
//            Intersection currentPoint = intersections.get(i);
//            double currentDistance = ray.getHead().distanceSquared(currentPoint.point);
//            // Update the closest point and minDistance if the current distance is smaller
//            if (currentDistance < minDistance) {
//                minDistance = currentDistance;
//                closestPoint = currentPoint;
//            }
//        }
//        // Return the closest point found
//        return closestPoint;
//    }
//}
