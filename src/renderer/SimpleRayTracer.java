package renderer;

import primitives.*;
import scene.Scene;

import java.util.List;

public class SimpleRayTracer extends RayTracerBase{
    public SimpleRayTracer(Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {
        List<Point> intersections = scene.geometries.findIntersections(ray);
        if (intersections == null) {
            return scene.background;
        }
        return calcColor(ray.findClosestPoint(intersections));
    }
    private Color calcColor(Point p){
        return scene.ambientLight.getIntensity();
    }
}
