package renderer;

import org.junit.jupiter.api.Test;

import lighting.AmbientLight;
import geometries.*;
import primitives.*;
import scene.Scene;

import static java.awt.Color.*;

/**
 * Test rendering a basic image
 *
 * @author Dan
 */
public class RendererTests {

    /**
     * Produce a scene with basic 3D model and render it into a png image with a
     * grid
     */
    @Test
    public void basicRenderTwoColorTest() {
        Scene scene = new Scene.Builder("Test scene")//
                .setAmbientLight(new AmbientLight(new Color(255, 191, 191), //
                        new Double3(1,1,1))) //
                .setBackground(new Color(75, 127, 90))
                        .build();

        scene.geometries.add(new Sphere(50, new Point(0, 0, -100)),
                new Triangle(new Point(-100, 0, -100), new Point(0, 100, -100), new Point(-100, 100, -100)), // up
                // left
                new Triangle(new Point(-100, 0, -100), new Point(0, -100, -100), new Point(-100, -100, -100)), // down
                // left
                new Triangle(new Point(100, 0, -100), new Point(0, -100, -100), new Point(100, -100, -100))); // down
        // right
        Camera camera = new Camera(Point.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPDistance(100) // sets distance from the camera to the view plane
                .setVPSize(500, 500) //sets number of pixels in the view plane
                .setImageWriter(new ImageWriter("base render test", 1000, 1000))
                .setRayTracer(new RayTracerBasic(scene));

        //FOR BONUS - Add these lines:
        {
            camera.rotateAroundY(0); //<<-- to rotate camera, right and left
            camera.rotateAroundZ(0); //in this case, doesnt do anything..
            camera.rotateAroundX(0); //<<-- to rotate camera, up and down

            camera.resetP0(new Point(0, 0, 0)); //<<-- to move to camera to the new position
            //(0, 0, _) -> if +, makes the image smaller... "zoom in/out"
            //(_, 0, 0) -> if +, moves the image left, else moves right
            //(0, _, 0) -> if +, moves the image down, else moves up
        }

        camera.renderImage();
        camera.printGrid(100, new Color(java.awt.Color.YELLOW));
        camera.writeToImage();
    }

    /**
     * Test for XML based scene - for bonus
     */
    @Test
    public void basicRenderXml() {
        Scene scene = new Scene.Builder("XML Test scene").build();
        // enter XML file name and parse from XML file into scene object
        // ...

        Camera camera = new Camera(Point.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPDistance(100) //
                .setVPSize(500, 500)
                .setImageWriter(new ImageWriter("xml render test", 1000, 1000))
                .setRayTracer(new RayTracerBasic(scene));

        //camera.rotateDown(90); //added!


        camera.renderImage();
        camera.printGrid(100, new Color(java.awt.Color.YELLOW));
        camera.writeToImage();
    }


    // For stage 6 - please disregard in stage 5
    /**
     * Produce a scene with basic 3D model - including individual lights of the
     * bodies and render it into a png image with a grid
     */
    @Test
    public void basicRenderMultiColorTest() {
        Scene scene = new Scene.Builder("Test scene")//
                .setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.2)))
                .build(); //

        scene.geometries.add( //
                new Sphere( 50, new Point(0, 0, -100)),
                // up left
                new Triangle(new Point(-100, 0, -100), new Point(0, 100, -100), new Point(-100, 100, -100))
                        .setEmission(new Color(GREEN)),
                // down left
                new Triangle(new Point(-100, 0, -100), new Point(0, -100, -100), new Point(-100, -100, -100))
                        .setEmission(new Color(RED)),
                // down right
                new Triangle(new Point(100, 0, -100), new Point(0, -100, -100), new Point(100, -100, -100))
                        .setEmission(new Color(BLUE)));

        Camera camera = new Camera(Point.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPDistance(100) //
                .setVPSize(500, 500) //
                .setImageWriter(new ImageWriter("color render test", 1000, 1000))
                .setRayTracer(new RayTracerBasic(scene)); //

        camera.renderImage();
        camera.printGrid(100, new Color(WHITE));
        camera.writeToImage();
    }

//    /**
//     * Test for XML based scene - for bonus
//     */
//    @Test
//    public void basicRenderXml() {
//        Scene scene = new Scene("XML Test scene");
//        // enter XML file name and parse from XML file into scene object
//        // ...
//
//        Camera camera = new Camera(Point.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0)) //
//                .setVPDistance(100) //
//                .setVPSize(500, 500).setImageWriter(new ImageWriter("xml render test", 1000, 1000))
//                .setRayTracer(new RayTracerBasic(scene));
//        camera.renderImage();
//        camera.printGrid(100, new Color(YELLOW));
//        camera.writeToImage();
//    }
//}



}
