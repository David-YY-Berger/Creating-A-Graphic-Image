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

public class efficiencyBoxTest {



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


            scene.geometries.add( //

                    //background triangles...
                    new Triangle(A, B, C).setEmission(new Color(BLACK))
                            .setMaterial(new Material().setKd(.2).setKs(.2).setShininess(1)),
                    new Triangle(D, B, C).setEmission(new Color(BLACK))
                            .setMaterial(new Material().setKd(.2).setKs(.2).setShininess(1)),

                    new BoundingBox(new Geometries(createSphereCube(new Point(-100, 0, 0)))),
                    new BoundingBox(new Geometries(createSphereCube(new Point(0, 0, 0)))),
                    new BoundingBox(new Geometries(createSphereCube(new Point(-50, -100, 0)))),
                    new BoundingBox(new Geometries(createSphereCube(new Point(50, 100, 0)))),
                    new BoundingBox(new Geometries(getRayCrown()))

//                    new Geometries(createSphereCube(new Point(-100, 0, 0))),
//                    new Geometries(createSphereCube(new Point(0, 0, 0))),
//                    new Geometries(createSphereCube(new Point(-50, -100, 0))),
//                    new Geometries(createSphereCube(new Point(50, 100, 0))),
//                    new Geometries(getRayCrown())




            );


            ImageWriter imageWriter = new ImageWriter("efficiency box test", 600, 600);
            camera.setImageWriter(imageWriter) //
                    .setRayTracer(new RayTracerBasic(scene)) //
                    .renderImage() //
                    .writeToImage();

        ImageWriter imageWriter2 = new ImageWriter("efficiency box test - Left", 600, 600);
        camera.resetP0(new Point(-300, 0, 800));
        //camera.rotateAroundZ(40);
        camera.rotateAroundY(-15);
        camera.setImageWriter(imageWriter2) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();

        ImageWriter imageWriter3 = new ImageWriter("efficiency box test - Right", 600, 600);
        camera.resetP0(new Point(450, 0, 900));
        camera.rotateAroundY(40);
        camera.setImageWriter(imageWriter3) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();

        }


        private List<Intersectable> createSphereCube(Point startPoint) {

            List<Intersectable> res = new LinkedList<>();
            int numRows = 10;
            int numColoumns = 5;
            int numDepth = 10;
            int distance = 8;
            int diff_color = 10;
            int radius_sphere = 3;
            Point p = startPoint;
            for (int i = 0; i < numColoumns; i++) {
                for (int j = 0; j < numRows; j++) {
                    for (int k = 0; k < numDepth; k++) {
                        res.add(
                                new Sphere(radius_sphere, new Point(p.xyz.d1 + i*distance, p.xyz.d2 + j*distance, p.xyz.d3 + k*distance))
                                        .setEmission(new Color(i*diff_color, j*diff_color, k*diff_color))
                                        .setMaterial(new Material().setKt(.9))
                        );
                    }
                }
            }
            return res;
        }

        private List<Intersectable> getRayCrown()
        {
            List<Intersectable> res = new LinkedList<>();

            Ray baseRay = new Ray(new Point(-100, 90, 1), new Vector(0001, 1, 5));
            //baseRay.getRandomRays(.1);
            Plane plane = new Plane(baseRay.getP0().add(baseRay.getDirVector().scale(100)), baseRay.getDirVector());

            Point startPoint = baseRay.getP0();
            double thickness = 2;
            Point startPoint2;// = startPoint.add(new Vector(thickness, 0, 1));
            List<Ray> rayList = baseRay.getRandomRays(.1);
            for (int i = 0; i < rayList.size(); i++) {

                Point intersect = plane.findGeoIntersections(rayList.get(i)).get(0).point;
                res.add(new Triangle(startPoint, intersect.add(new Vector(thickness, 0, 0)), intersect)
                        .setEmission(new Color(255 - i*5, i*10 ,0))
                        .setMaterial(new Material().setKt(.5)));
            }

            return res;
        }




    }


