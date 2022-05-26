package geometries;

import org.junit.jupiter.api.Test;
import static java.awt.Color.*;
import lighting.*;
import renderer.*;
import primitives.*;
import scene.Scene;

import java.util.LinkedList;
import java.util.List;

import static java.awt.Color.BLACK;

public class BoxTest {

    private Scene scene = new Scene.Builder("Test scene").build();


    /**
     * WE ADDED THIS TEST!!!
     */
    @Test
    public void ourNewTest() {
        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(200, 200).setVPDistance(500);

        scene.ambientLight = (new AmbientLight(new Color(WHITE), new Double3(0.15)));
        scene.lights.add(new SpotLight(new Vector(0, -1, -1), new Point(0, 100, 100), new Color(700, 400, 400)
        ));


        double radius_of_circle = 30d;
        double distance_btw_circle_and_background = -80;
        //background triangles...
        Point A = new Point(200, -200, distance_btw_circle_and_background);
        Point B = new Point(-200, -200, distance_btw_circle_and_background);
        Point C = new Point(200, 200, distance_btw_circle_and_background);
        Point D = new Point(-200, 200, distance_btw_circle_and_background);

        Sphere sphere1 = new Sphere(radius_of_circle, new Point(0, 0, 0));
        Geometry sphere1geometryForm = sphere1.setEmission(new Color(BLUE))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(10).setKt(0.5));


        Box box = new Box(sphere1);
        List<Geometry> res = new LinkedList();
        for (Point boxPoint: box.vertices
             ) {
            res.add(new Sphere(5, boxPoint).setEmission(new Color(0, 255, 0))
                    .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(10).setKt(0.5)));
        }

        scene.geometries.add( //

                //background triangles...
                new Triangle(A, B, C).setEmission(new Color(BLACK))
                        .setMaterial(new Material().setKd(.2).setKs(.2).setShininess(1)),
                new Triangle(D, B, C).setEmission(new Color(BLACK))
                        .setMaterial(new Material().setKd(.2).setKs(.2).setShininess(1)),
                //sphere#1 - partly transparent, low specular
                sphere1geometryForm,

                //box's vertices:
                res.get(0),
                res.get(1),
                res.get(2),
                res.get(3),
                res.get(4),
                res.get(5),
                res.get(6),
                res.get(7)

                );


        ImageWriter imageWriter = new ImageWriter("BoxTest", 600, 600);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();

        ImageWriter imageWriter2 = new ImageWriter("BoxTest_from_left", 600, 600);
        camera.resetP0(new Point(-300, 0, 800));
        //camera.rotateAroundZ(40);
        camera.rotateAroundY(-15);
        camera.setImageWriter(imageWriter2) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();

        ImageWriter imageWriter3 = new ImageWriter("BoxTest_from_right", 600, 600);
        camera.resetP0(new Point(450, 0, 900));
        camera.rotateAroundY(40);
        camera.setImageWriter(imageWriter3) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();



    }
}
