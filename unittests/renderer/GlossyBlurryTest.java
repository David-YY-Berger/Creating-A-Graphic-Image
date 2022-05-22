package renderer;
import org.junit.jupiter.api.Test;

import static java.awt.Color.*;

import lighting.*;
import geometries.*;
import primitives.*;
import scene.Scene;
public class GlossyBlurryTest {

    private Scene scene = new Scene.Builder("Test scene").build();

    /**
     * puts blurry triangles in front of image, to show blurry parameter
     */
    @Test
    public void blurryWindowTest() {
        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(200, 200).setVPDistance(500);

        scene.ambientLight = (new AmbientLight(new Color(WHITE), new Double3(0.15)));
//        scene.lights.add(new SpotLight( new Vector(0, 0, -1), new Point(60, 50, 0),new Color(700, 400, 400)) //
//                .setKl(4E-5).setKq(2E-7));
        scene.lights.add(new SpotLight(new Vector(0, -1, -1), new Point(0, 100, 100), new Color(900, 500, 500)
        ));


        double radius_of_circle = 30d;
        double radius_of_circle2 = 10d;
        double distance_btw_circle_and_background = -80;

        //glass window triangles..
        Point AA = new Point(200, -200, -distance_btw_circle_and_background);
        Point BB = new Point(-200, -200, -distance_btw_circle_and_background);
        Point CC = new Point(200, 200, -distance_btw_circle_and_background);
        Point DD = new Point(-200, 200, -distance_btw_circle_and_background);


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

                //window triangles...
                new Triangle(AA, BB, CC).setEmission(new Color(new java.awt.Color(0, 1, 0)))
                        .setMaterial(new Material().setKt(1).setkBlurry(0.025)), //<--blurry triangle.setkBlurry(0)
                new Triangle(DD, BB, CC).setEmission(new Color(new java.awt.Color(0, 1, 0)))
                        .setMaterial(new Material().setKt(1).setkBlurry(0.025)),


                //sphere#1 - partly transparent, low specular
                new Sphere(radius_of_circle, new Point(0, 0, 0)).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(10).setKt(0.5)),
                //sphere#2 - opaque, high specular
                new Sphere(radius_of_circle2, new Point(-(radius_of_circle + height_of_triangles + radius_of_circle2), 0, 0))
                        .setMaterial(new Material().setKd(0.5).setKs(.9).setShininess(50).setKt(0)),

                //colored triangles
                //Tri #1 - not transparent, not shiny
                new Triangle(a, b, c).setEmission(new Color(GREEN))
                        .setMaterial(new Material().setKt(0).setKd(.1).setShininess(0)),
                //Tri #2 - not transparent, shiny
                new Triangle(a, d, e).setEmission(new Color(RED))
                        .setMaterial(new Material().setKt(0).setKd(.1).setShininess(10).setKs(0)),
                //Tri #3 - transparent, not shiny
                new Triangle(b, f, g).setEmission(new Color(BLACK))
                        .setMaterial(new Material().setKt(.6).setKd(.1))


        );


        ImageWriter imageWriter = new ImageWriter("ZT1window test", 600, 600);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();

//        ImageWriter imageWriter2 = new ImageWriter("ourNewImage_from_left", 600, 600);
//        camera.resetP0(new Point(-300, 0, 800));
//        //camera.rotateAroundZ(40);
//        camera.rotateAroundY(-15);
//        camera.setImageWriter(imageWriter2) //
//                .setRayTracer(new RayTracerBasic(scene)) //
//                .renderImage() //
//                .writeToImage();
//
//        ImageWriter imageWriter3 = new ImageWriter("ourNewImage_from_right", 600, 600);
//        camera.resetP0(new Point(450, 0, 900));
//        camera.rotateAroundY(40);
//        camera.setImageWriter(imageWriter3) //
//                .setRayTracer(new RayTracerBasic(scene)) //
//                .renderImage() //
//                .writeToImage();

    }

    /**
     * makes mirroring triangle blurry to show blurry parameter
     */
    @Test
    public void blurryMirrorTest() {
        Camera camera = new Camera(new Point(0, 0, 10000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(2500, 2500).setVPDistance(10000); //

        scene.ambientLight = (new AmbientLight(new Color(255, 255, 255), new Double3(0.1)));

        scene.geometries.add( //
                new Sphere( 400d, new Point(-950, -900, -1000)).setEmission(new Color(0, 0, 100)) //
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20).setKt(0.5)),
                new Sphere( 200d, new Point(-950, -900, -1000)).setEmission(new Color(100, 20, 20)) //
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),

                new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500), new Point(670, 670, 3000)) //
                        .setEmission(new Color(20, 20, 20)) //<<--meant to be the blurry mirror!
                        .setMaterial(new Material().setKr(1).setkBlurry(0.025)), //.setkBlurry(0.1)
                new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                        new Point(-1500, -1500, -2000)) //
                        .setEmission(new Color(20, 20, 20)) //
                        .setMaterial(new Material().setKr(0.5)));

        scene.lights.add(new SpotLight(new Vector(-1, -1, -4), new Point(-750, -750, -150),new Color(4020, 1600, 1600)) //
                .setKl(0.00001).setKq(0.000005));

        ImageWriter imageWriter = new ImageWriter("ZT2blurryMirrorTest", 500, 500);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();

    }
}
