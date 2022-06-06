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

import java.util.LinkedList;
import java.util.List;

import static java.awt.Color.*;
import static java.awt.Color.BLACK;

public class AllEfectsTest {

    private Scene scene = new Scene.Builder("Test scene").build();


    /**
     * WE ADDED THIS TEST!!!
     * Produce a picture of a sphere and three triangles, each one with different levels of reflection, refraction
     */
    @Test
    public void ourNewTest() {
        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(200, 200).setVPDistance(500);

        scene.ambientLight = (new AmbientLight(new Color(WHITE), new Double3(0.15)));

//        scene.lights.add(new SpotLight( new Vector(0, 0, -1), new Point(60, 50, 0),new Color(700, 400, 400)) //
//                .setKl(4E-5).setKq(2E-7));
        scene.lights.add(new SpotLight(new Vector(0, -1, -1), new Point(0, 100, 100), new Color(700, 400, 400)
        ));


        double radius_of_circle = 30d;
        double radius_of_circle2 = 10d;
        double distance_btw_circle_and_background = -80;
        //background triangles...
        Point A = new Point(200, -200, distance_btw_circle_and_background);
        Point B = new Point(-200, -200, distance_btw_circle_and_background);
        Point C = new Point(200, 200, distance_btw_circle_and_background);
        Point D = new Point(-200, 200, distance_btw_circle_and_background);

        //colored triangles
        double height_of_triangles = 30;
        Point a = new Point(radius_of_circle, radius_of_circle, 0);
        Point b = new Point(-radius_of_circle, radius_of_circle, 0);
        Point c = new Point(0, radius_of_circle + height_of_triangles, 0);

        Point d = new Point(radius_of_circle, -radius_of_circle, 0);
        Point e = new Point(radius_of_circle + height_of_triangles, 0, 0);

        Point f = new Point(-radius_of_circle, -radius_of_circle, 0);
        Point g = new Point(-(radius_of_circle + height_of_triangles), 0, 0);

        int numRows = 10;
        int distance = 4;
        int diff_color = 20;
        int radius_sphere = 1;
        Point p = new Point(-20, 0, 0);
        List<Intersectable> CubeOfCirlces = new LinkedList<>();
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numRows; j++) {
                for (int k = 0; k < numRows; k++) {
                   CubeOfCirlces.add(
                           new Sphere(radius_sphere, new Point(p.xyz.d1 + i*distance, p.xyz.d2 + j*distance, p.xyz.d3 + k*distance))
                                   .setEmission(new Color(i*diff_color, j*diff_color, k*diff_color))
                                   .setMaterial(new Material().setKt(.5))
                   )       ;
                }
            }
        }






        scene.geometries.add( //

                //background triangles...
                new Triangle(A, B, C).setEmission(new Color(BLACK))
                        .setMaterial(new Material().setKd(.2).setKs(.2).setShininess(1)),
                new Triangle(D, B, C).setEmission(new Color(BLACK))
                        .setMaterial(new Material().setKd(.2).setKs(.2).setShininess(1)),
                //sphere#1 - partly transparent, low specular
//                new Sphere(radius_of_circle, new Point(0, 0, 0)).setEmission(new Color(BLUE))
//                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(10).setKt(0.5)),

                new BoundingBox(new Geometries(CubeOfCirlces)),
                //new Geometries(lst),


                // (1) SPHERE 3, 4, 5, 6  UP -RIGHT

                //sphere#3
                new Sphere(radius_of_circle2, new Point(100, 120, 10)).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKd(0.2).setKs(0).setShininess(0).setKt(0.4)),
                //sphere#4
                new Sphere(radius_of_circle2, new Point(100, 90, 10)).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKd(0.2).setKs(0).setShininess(0).setKt(0.8)),
//                //sphere#5
//                new Sphere(radius_of_circle2, new Point(10, 105, 10)).setEmission(new Color(BLUE))
//                        .setMaterial(new Material().setKd(0.2).setKs(0).setShininess(0).setKt(0.2)),
                //sphere#6
                new Sphere(radius_of_circle2, new Point(70, 75, 10)).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKd(0.2).setKs(0).setShininess(0).setKt(0)),

                //triangle behind them
                new Triangle(new Point(10, 60, -50), new Point(90, 170, -50), new Point(180, 60, -50))
                        .setEmission(new Color(RED))
                        .setMaterial(new Material().setKd(.2).setKs(.2).setShininess(1)),


                //(2) SPHERE 7, 8, 9, 10 UP LEFT
                //sphere#7
                new Sphere(radius_of_circle2, new Point(-70, 75, 5)).setEmission(new Color(YELLOW))
                        .setMaterial(new Material().setKd(0.7).setKs(0.5).setShininess(30)),
                //sphere#8
                new Sphere(radius_of_circle2, new Point(-100, 120, 5)).setEmission(new Color(GREEN))
                        .setMaterial(new Material().setKd(0.7).setKs(0.5).setShininess(30)),
                //sphere#9
                new Sphere(radius_of_circle2, new Point(-100, 90, 5)).setEmission(new Color(RED))
                        .setMaterial(new Material().setKd(0.7).setKs(0.5).setShininess(30)),
                //sphere#10
                new Sphere(radius_of_circle2, new Point(-70, 105, 5)).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKd(0.7).setKs(0.5).setShininess(30)),

                //triangle behind them
                new Triangle(new Point(-10, 60, -50), new Point(-90, 170, -50), new Point(-180, 60, -50))
                        .setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKd(.2).setKs(.2).setShininess(1)),




                //SPHERE 11, 12, 13, 14 ARE DOWN  LEFT
                //sphere#11
                new Sphere(radius_of_circle2, new Point(-70, -75, -50)).setEmission(new Color(YELLOW))
                        .setMaterial(new Material().setKd(0.7).setKs(0.5).setShininess(30)),
                //sphere#12
                new Sphere(radius_of_circle2, new Point(-100, -120, -20)).setEmission(new Color(GREEN))
                        .setMaterial(new Material().setKd(0.7).setKs(0.5).setShininess(30)),
                //sphere#13
                new Sphere(radius_of_circle2, new Point(-100, -90, -20)).setEmission(new Color(RED))
                        .setMaterial(new Material().setKd(0.7).setKs(0.5).setShininess(30)),
                //sphere#14
                new Sphere(radius_of_circle2, new Point(-70, -105, -20)).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKd(0.7).setKs(0.5).setShininess(30)),

                //triangle behind them
                new Triangle(new Point(-10, -60, -70), new Point(-90, -170, -70), new Point(-180, -60, -70))
                        .setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKd(.2).setKs(.2).setShininess(1).setKr(1).setkBlurry(0.1)),

                //SPHERE 15, 16, 17, 18 ARE DOWN RIGHT
                //sphere#15
                new Sphere(radius_of_circle2, new Point(70, -75, 30)).setEmission(new Color(YELLOW))
                        .setMaterial(new Material().setKd(0.7).setKs(0.5).setShininess(30)),
                //sphere#16
                new Sphere(radius_of_circle2, new Point(100, -120, 30)).setEmission(new Color(GREEN))
                        .setMaterial(new Material().setKd(0.7).setKs(0.5).setShininess(30)),
                //sphere#17
                new Sphere(radius_of_circle2, new Point(100, -90, 30)).setEmission(new Color(RED))
                        .setMaterial(new Material().setKd(0.7).setKs(0.5).setShininess(30)),
                //sphere#18
                new Sphere(radius_of_circle2, new Point(70, -105, 30)).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKd(0.7).setKs(0.5).setShininess(30)),


                //triangle behind them
                new Triangle(new Point(10, -60, -79), new Point(90, -170, -79), new Point(180, -60, -79))
                        .setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKd(.2).setKs(.2).setShininess(1).setKr(1))




        );



















        ImageWriter imageWriter = new ImageWriter("finalBoxTest", 600, 600);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();

        ImageWriter imageWriter2 = new ImageWriter("finalBoxTest - left", 600, 600);
        camera.resetP0(new Point(-300, 0, 800));
        //camera.rotateAroundZ(40);
        camera.rotateAroundY(-15);
        camera.setImageWriter(imageWriter2) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();

        ImageWriter imageWriter3 = new ImageWriter("finalBoxTest - right", 600, 600);
        camera.resetP0(new Point(450, 0, 900));
        camera.rotateAroundY(40);
        camera.setImageWriter(imageWriter3) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();

    }
}
