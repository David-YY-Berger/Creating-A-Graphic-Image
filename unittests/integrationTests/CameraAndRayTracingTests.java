package integrationTests;
import geometries.Intersectable;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import primitives.*;
import geometries.Sphere;
import geometries.Plane;

import renderer.Camera;

import java.util.List;

/**
 * Integration tests for interaction between the Camera and finding intersections of rays and geometries
 */
public class CameraAndRayTracingTests {

    private final String errorMessage = "inaccurate num points!";

    /**
     * func computes all intersect points for each pixel given in parameter
     * @return num intersections with given shape (paramter)
     */
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

    /**
     * Integration test method for {@link renderer.Camera#constructRay(int, int, int, int)}
     * and {@link geometries.Sphere#findIntersections(Ray)}
     */
    @Test
    public void testWithSphere() {

        //TC01 - sphere is ahead the camera, only the middle ray (from the middle pixel) intersects the sphere
        Sphere sp1 = new Sphere(1, new Point(0, 0, -3));
        Camera cam1 = new Camera(new Point(0, 0, 0), new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setVPDistance(1)
                .setVPSize(3, 3);

        assertEquals(2, getNumIntersections(sp1, cam1, 3, 3), errorMessage);

        //TC02 - all the rays hits the sphere twice
        Sphere sp2 = new Sphere(2.5, new Point(0, 0, -2.5));
        Camera cam2 = new Camera(new Point(0, 0, 0.5), new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setVPDistance(1)
                .setVPSize(3, 3);

        assertEquals(18, getNumIntersections(sp2, cam2, 3, 3), errorMessage);

        //TC03 - some rays intersect the sphere once and some twice
        Sphere sp3 = new Sphere(2, new Point(0, 0, -2));
        assertEquals(10, getNumIntersections(sp3, cam2, 3, 3), errorMessage);

        //TC04 - view plain and camera are in the sphere
        Sphere sp4 = new Sphere(4, new Point(0, 0, -2));
        assertEquals(9, getNumIntersections(sp4, cam2, 3, 3), errorMessage);

        //TC05 - sphere is behind the view plain
        Sphere sp5 = new Sphere(0.5, new Point(0, 0, 1));
        assertEquals(0, getNumIntersections(sp5, cam2, 3, 3), errorMessage);

    }

    /**
     * Integration test method for {@link renderer.Camera#constructRay(int, int, int, int)} 
     * and {@link geometries.Plane#findIntersections(Ray)}
     */
    @Test
    public void testWithPlane() {

        //TC 01: all points intersected - plane parallel to y-axis:
        Camera cam = new Camera(new Point(0,0,0), new Vector(1, 0, 0), new Vector(0, 1, 0))
                .setVPSize(3, 3)
                .setVPDistance(1);

        Plane plane = new Plane(new Point(5, 0, -1), new Point(5, 0, 1), new Point(5, 2, 0));
        assertEquals(9, getNumIntersections(plane, cam, 3, 3), errorMessage);

        //TC02: all points intersected, - plane slanted
        plane = new Plane(new Point(5, 0, -1), new Point(5, 0, 1), new Point(5.1, 1, 0));
        assertEquals(9, getNumIntersections(plane, cam, 3, 3), errorMessage);

        //TC03: 6/9 points intersected, - plane slanted, camera's ray miss the plane (their angle is too sharp upward
        plane = new Plane(new Point(5, 0, -1), new Point(5, 0, 1), new Point(6, 1, 0));
        assertEquals(6, getNumIntersections(plane, cam, 3, 3), errorMessage);

        //TC04: - same test as triangle test "TC01" - but gets 9 points  bec it is a plane:
        plane = new Plane(new Point(2, -1, 1), new Point(2, -1, -1), new Point(2, 1, 0));
        assertEquals(9, getNumIntersections(plane, cam, 3, 3), errorMessage);

    }
    /**
     * Integration test method for {@link renderer.Camera#constructRay(int, int, int, int)}
     * and {@link geometries.Triangle#findIntersections(Ray)}
     */
    @Test
    public void testWithTriangle() {

        Camera cam = new Camera(new Point(0,0,0), new Vector(1, 0, 0), new Vector(0, 1, 0))
                .setVPSize(3, 3)
                .setVPDistance(1);
        //TC01: only center ray intersects with triangle (1 point)
        Triangle triangle = new Triangle(new Point(2, -1, 1), new Point(2, -1, -1), new Point(2, 1, 0));
        assertEquals(1, getNumIntersections(triangle, cam, 3, 3), errorMessage);

        //TC02: only center and upper-center ray intersects with triangle(2 points)
        triangle = new Triangle(new Point(2, -1, 1), new Point(2, -1, -1), new Point(-2, 20, 0));
        //triangle is very tall and skinny!
        assertEquals(2, getNumIntersections(triangle, cam, 3, 3), errorMessage);

    }

}
