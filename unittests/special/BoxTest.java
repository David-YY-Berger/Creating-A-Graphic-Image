package special;

import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import scene.Scene;

import static java.awt.Color.*;
import static java.awt.Color.BLACK;

public class BoxTest {

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

        scene.geometries.add( //

                //background triangles...
                new Triangle(A, B, C).setEmission(new Color(BLACK))
                        .setMaterial(new Material().setKd(.2).setKs(.2).setShininess(1)),
                new Triangle(D, B, C).setEmission(new Color(BLACK))
                        .setMaterial(new Material().setKd(.2).setKs(.2).setShininess(1)),
                //sphere#1 - partly transparent, low specular
                new Sphere(radius_of_circle, new Point(0, 0, 0)).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(10).setKt(0.5)),
                //sphere#2 - opaque, high specular
//                new Sphere(radius_of_circle2, new Point(-(radius_of_circle + height_of_triangles + radius_of_circle2), 0, 0))
//                        .setMaterial(new Material().setKd(0.5).setKs(.9).setShininess(50).setKt(0)),

                // sphere 3, 4, 5, 6 are up in right

                //sphere#3
                new Sphere(radius_of_circle2, new Point(100, 120, 10)).setEmission(new Color(GREEN))
                        .setMaterial(new Material().setKd(0.7).setKs(0.5).setShininess(30).setKt(0.2)),

                //sphere#4
                new Sphere(radius_of_circle2, new Point(100, 90, 10)).setEmission(new Color(RED))
                .setMaterial(new Material().setKd(0.7).setKs(0.5).setShininess(30).setKt(0.6)),

                //sphere#5
                new Sphere(radius_of_circle2, new Point(70, 105, 10)).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKd(0.7).setKs(0.5).setShininess(30).setKt(0.4)),

                //sphere#6
                new Sphere(radius_of_circle2, new Point(70, 75, 10)).setEmission(new Color(YELLOW))
                        .setMaterial(new Material().setKd(0.7).setKs(0.5).setShininess(30).setKt(0.5)),

                //triangle behind them
                new Triangle(new Point(30, 65, -50), new Point(90, 160, -50), new Point(150, 65, -50)).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKd(.2).setKs(.2).setShininess(1).setKt(0.5).setKr(0.8)),


                //sphere 7, 8, 9, 10 are up in left

                //sphere#7
                new Sphere(radius_of_circle2, new Point(-70, 75, 5)).setEmission(new Color(YELLOW))
                        .setMaterial(new Material().setKd(0.7).setKs(0.5).setShininess(30).setKt(0.5)),

                //sphere#8
                new Sphere(radius_of_circle2, new Point(-100, 120, 5)).setEmission(new Color(GREEN))
                        .setMaterial(new Material().setKd(0.7).setKs(0.5).setShininess(30).setKt(0.2)),

                //sphere#9
                new Sphere(radius_of_circle2, new Point(-100, 90, 5)).setEmission(new Color(RED))
                        .setMaterial(new Material().setKd(0.7).setKs(0.5).setShininess(30).setKt(0.6)),

                //sphere#10
                new Sphere(radius_of_circle2, new Point(-70, 105, 5)).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKd(0.7).setKs(0.5).setShininess(30).setKt(0.4)),

                //triangle behind them
                new Triangle(new Point(30, -65, -70), new Point(90, -160, -70), new Point(150, -65, -70)).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKd(.2).setKs(.2).setShininess(1).setKt(0)),

                //sphere 11, 12, 13, 14 are down in left

                //sphere#11
                new Sphere(radius_of_circle2, new Point(-70, -75, -50)).setEmission(new Color(YELLOW))
                        .setMaterial(new Material().setKd(0.7).setKs(0.5).setShininess(30).setKt(0.5)),

                //sphere#12
                new Sphere(radius_of_circle2, new Point(-100, -120, -20)).setEmission(new Color(GREEN))
                        .setMaterial(new Material().setKd(0.7).setKs(0.5).setShininess(30).setKt(0.2)),

                //sphere#13
                new Sphere(radius_of_circle2, new Point(-100, -90, -20)).setEmission(new Color(RED))
                        .setMaterial(new Material().setKd(0.7).setKs(0.5).setShininess(30).setKt(0.6)),

                //sphere#14
                new Sphere(radius_of_circle2, new Point(-70, -105, -20)).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKd(0.7).setKs(0.5).setShininess(30).setKt(0.4)),

                //triangle behind them
                new Triangle(new Point(-30, -65, -70), new Point(-90, -160, -70), new Point(-150, -65, -70)).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKd(.2).setKs(.2).setShininess(1).setKt(0).setKr(0.5)),

                //sphere 15, 16, 17, 18 are down in right

                //sphere#15
                new Sphere(radius_of_circle2, new Point(70, -75, -50)).setEmission(new Color(YELLOW))
                        .setMaterial(new Material().setKd(0.7).setKs(0.5).setShininess(30).setKt(0.5)),

                //sphere#16
                new Sphere(radius_of_circle2, new Point(100, -120, -50)).setEmission(new Color(GREEN))
                        .setMaterial(new Material().setKd(0.7).setKs(0.5).setShininess(30).setKt(0.2)),

                //sphere#17
                new Sphere(radius_of_circle2, new Point(100, -90, -50)).setEmission(new Color(RED))
                        .setMaterial(new Material().setKd(0.7).setKs(0.5).setShininess(30).setKt(0.6)),

                //sphere#18
                new Sphere(radius_of_circle2, new Point(70, -105, -50)).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKd(0.7).setKs(0.5).setShininess(30).setKt(0.4)),

                //triangle behind them
                new Triangle(new Point(-30, 65, -50), new Point(-90, 160, -50), new Point(-150, 65, -50)).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKd(.2).setKs(.2).setShininess(1).setKt(0))

                //colored triangles
                //Tri #1 - not transparent, not shiny
//                new Triangle(a, b, c).setEmission(new Color(GREEN))
//                        .setMaterial(new Material().setKt(0).setKd(.1).setShininess(0)),
//                //Tri #2 - not transparent, shiny
//                new Triangle(a, d, e).setEmission(new Color(RED))
//                        .setMaterial(new Material().setKt(0).setKd(.1).setShininess(10).setKs(0)),
//                //Tri #3 - transparent, not shiny
//                new Triangle(b, f, g).setEmission(new Color(BLACK))
//                        .setMaterial(new Material().setKt(.6).setKd(.1))


        );


        ImageWriter imageWriter = new ImageWriter("ourNewImage-Box", 600, 600);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();

        ImageWriter imageWriter2 = new ImageWriter("ourNewImage_from_left-Box", 600, 600);
        camera.resetP0(new Point(-300, 0, 800));
        //camera.rotateAroundZ(40);
        camera.rotateAroundY(-15);
        camera.setImageWriter(imageWriter2) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();

        ImageWriter imageWriter3 = new ImageWriter("ourNewImage_from_right-Box", 600, 600);
        camera.resetP0(new Point(450, 0, 900));
        camera.rotateAroundY(40);
        camera.setImageWriter(imageWriter3) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();

    }
}
