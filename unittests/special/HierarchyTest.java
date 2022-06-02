package special;

import geometries.*;
import lighting.AmbientLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import scene.Scene;

import java.util.List;

import static java.awt.Color.*;
import static java.awt.Color.BLACK;

public class HierarchyTest {

    private Scene scene = new Scene.Builder("Test scene").build();

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

        Geometries sphere1 = new Geometries(
                new Sphere(radius_of_circle, new Point(0, 0, 0))
                .setEmission(new Color(BLUE))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(10).setKt(0.5))
        );

        Geometries sphere2 = new Geometries(
                new Sphere(radius_of_circle, new Point(100, 0, 0))
                .setEmission(new Color(BLUE))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(10).setKt(0.5))
        );

        Geometries sphere3 = new Geometries(
                new Sphere(radius_of_circle, new Point(0, -100, 0))
                .setEmission(new Color(BLUE))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(10).setKt(0.5))
        );

        Color colorOfBoxes = new Color(0, 50, 0);
        double transpOfBoxes = .9;
        Geometry boundingBox1 = new BoundingBox(sphere1).setColorAndTransp(colorOfBoxes, transpOfBoxes);
        Geometry boundingBox2 = new BoundingBox(sphere2).setColorAndTransp(colorOfBoxes, transpOfBoxes);
        Geometry boundingBox3 = new BoundingBox(sphere3).setColorAndTransp(colorOfBoxes, transpOfBoxes);

        Geometry allSphereBoxes = new BoundingBox(new Geometries(
                boundingBox1, boundingBox2, boundingBox3)
        ).setColorAndTransp(new Color(50, 0, 0), transpOfBoxes);


        Geometries tri1 = new Geometries(
                new Triangle(new Point(0, 80, 40), new Point(-10, 100, 50), new Point(10, 120, 60))
                .setEmission(new Color(BLUE))
        );
        Geometry boundingBox4 = new BoundingBox(tri1).setColorAndTransp(colorOfBoxes, transpOfBoxes);

        Geometry allBoxes = new BoundingBox(new Geometries(allSphereBoxes, boundingBox4))
                .setColorAndTransp(new Color(0, 25, 25), transpOfBoxes);

        scene.geometries.add( //

                //background triangles...
                new Triangle(A, B, C).setEmission(new Color(BLACK))
                        .setMaterial(new Material().setKd(.2).setKs(.2).setShininess(1)),
                new Triangle(D, B, C).setEmission(new Color(BLACK))
                        .setMaterial(new Material().setKd(.2).setKs(.2).setShininess(1)),

                sphere1, sphere2, sphere3, tri1,
                boundingBox1, boundingBox2, boundingBox3, boundingBox4,

                allSphereBoxes,
                allBoxes

        );


        ImageWriter imageWriter = new ImageWriter("HierarchyTest", 600, 600);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();

        ImageWriter imageWriter2 = new ImageWriter("HierarchyTest - Left", 600, 600);
        camera.resetP0(new Point(-300, 0, 800));
        //camera.rotateAroundZ(40);
        camera.rotateAroundY(-15);
        camera.setImageWriter(imageWriter2) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();

        ImageWriter imageWriter3 = new ImageWriter("HierarchyTest - Right", 600, 600);
        camera.resetP0(new Point(450, 0, 900));
        camera.rotateAroundY(40);
        camera.setImageWriter(imageWriter3) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();



    }









}
