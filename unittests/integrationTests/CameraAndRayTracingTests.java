package integrationTests;
import geometries.Intersectable;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import primitives.*;
import geometries.Triangle;
import geometries.Sphere;
import geometries.Plane;

import renderer.Camera;

import java.util.List;

/**
 * Integration tests for interaction between the Camera and finding intersections of rays and geometries
 */
public class CameraAndRayTracingTests {

    private final String message = "innacurate num points!";

    private int getNumIntersections(Intersectable shape, Camera cam, int nx, int ny){

        int res = 0;
        for (int i = 0; i < nx; i++) {
            for (int j = 0; j < ny; j++) {

                List<Point> nullableList = shape.findIntersections(cam.constructRay(nx, ny, j, i));
                if(nullableList == null)
                    continue;
                else
                    res += nullableList.size();
            }
        }
        return res;
    }


    @Test
    public void testWithSphere() {

    
    
    
    
    
    
    }

    /**
     * Integration test method for {@link renderer.Camera#constructRay(int, int, int, int)} 
     * and {@link geometries.Plane#findIntersections(Ray)}
     */
    @Test
    public void testWithPlane() {

        //test 1: all points intersected:
        Camera cam = new Camera(new Point(0,0,0), new Vector(1, 0, 0), new Vector(0, 1, 0))
                .setVPSize(3, 3)
                .setVPDistance(1);

        Plane plane = new Plane(new Point(2, 0, -1), new Point(2, 0, 1), new Point(2, 2, 0));
        assertEquals(9, getNumIntersections(plane, cam, 3, 3), message);

        //more tests....
    }
    /**
     * Integration test method for {@link renderer.Camera#constructRay(int, int, int, int)}
     * and {@link geometries.Triangle#findIntersections(Ray)}
     */
    @Test
    public void testWithTriangle() {

    }

}
