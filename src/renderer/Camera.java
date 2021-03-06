package renderer;
import java.util.stream.*;
import primitives.*;

import java.util.MissingResourceException;


/**
 * Class holds Camera; we trace rays from the camera to each object
 */
public class Camera {

    private Point p0;
    private Vector v_to;
    private Vector v_up;
    private Vector v_right;

    private double height;
    private double width;
    private ImageWriter imageWriter;
    private RayTracerBase rayTracerBase;

    private double distanceFromVP; //from camera to View Plane

    //CTOR with parameters
    public Camera(Point _p0, Vector _v_to, Vector _v_up) {

        //checks that Vectors are orthogonal (otherwise, throws ex),
        if (!Util.isZero(_v_to.dotProduct(_v_up))) //if vectors are not orthogonal
            throw new IllegalArgumentException("given vectors are not orthogonal!");

        p0 = _p0;
        v_to = _v_to.normalize();
        v_up = _v_up.normalize();
        v_right = _v_to.crossProduct(_v_up).normalize(); //<- do we need to normalize???
    }

    //SETTERS:
    public Camera setVPSize(double _width, double _height){

        width = _width;
        height = _height;
        return this;
    }
    public Camera setVPDistance(double distance){

        distanceFromVP = distance;
        return this; //?
    }
    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }
    public Camera setRayTracer(RayTracerBase rayTracerBase) {
        this.rayTracerBase = rayTracerBase;
        return this;
    }


    //METHODS:

    /**
     * constructs a ray from camera's p0 to the pixel given as a parameter
     * @param nX num of pixels in VP - horizontal
     * @param nY num of pixels in VP - vertical
     * @param j specific pixel - horizantal (x axis...)
     * @param i specific pixel - vertical (x=y axis...)
     */
    public Ray constructRayThruPixel(int nX, int nY, int j, int i){

        //see Diagram 2.2, 2.3 in ReadMe file
        Point pc = p0.add(v_to.scale(distanceFromVP)); //center of VP (represented as a 3d Point
        Point pIJ = pc;                                //center of desired pixel

        //(1) MOVING PIJ IN THE X DIRECTION
        double rx = width / nX;                        //resolution of pixels in x direction
        double xJ = (j - (nX-1)/2d) * rx;              //scalar value to get exact pixel in x direction
        if(xJ!=0)
            pIJ = pIJ.add(v_right.scale(xJ));          //move pij in x direction

        //(2) MOVING PIJ IN THE Y DIRECTION
        double rY = height / nY;                        //same as x direction...
        double yI = -(i - (nY-1)/2d)*rY;
        if(yI!=0)
            pIJ = pIJ.add(v_up.scale(yI));

        return new Ray(p0, pIJ.subtract(p0));
    }

    /**
     * renderImage() iterates thru pixels, coloring each one, and transfers data to ImageWriter
     */
    public Camera renderImage() {

        //(1) throw exceptions
        boolean unsupported = false;
        if (imageWriter == null) {
            unsupported = true;
            throw new MissingResourceException("missing resource!", "imageWriter", " ");
        }
        if (rayTracerBase == null) {
            unsupported = true;
            throw new MissingResourceException("missing resource!", "rayTracerBase", " ");
        }
        if (unsupported)
            throw new UnsupportedOperationException();

        //(2) iterate thru each pixel, write to imageWriter:
        int totalXPixels = imageWriter.getNx();
        int totalYPixels = imageWriter.getNy();

        //  WITH ONE THREAD::
//        for (int x = 0; x < totalXPixels; x++) {        //x = specific pixel in x direction (j)
//            for (int y = 0; y < totalYPixels; y++) {    //y = specific pixel in y direction (i)
//                Color pixelColor = castRay(totalXPixels, totalYPixels, x, y);
//                imageWriter.writePixel(x, y, pixelColor);
//            }
//        }

        //  WITH MULTI THREAD:
        Pixel.initialize(totalYPixels, totalXPixels, 3);

        IntStream.range(0, totalYPixels).parallel().forEach(i -> {
            IntStream.range(0, totalXPixels).parallel().forEach(j -> {
                Color pixelColor = castRay(totalXPixels, totalYPixels, j, i);
                imageWriter.writePixel(j, i, pixelColor);
                Pixel.pixelDone();
                Pixel.printPixel();
            });
        });


        return this;
    }

    private Color castRay(int totalXPixels, int totalYPixels, int x, int y) {
        Ray ray = constructRayThruPixel(totalXPixels, totalYPixels, x, y);
        return rayTracerBase.traceRay(ray); //returns color..
    }

    /**
     * prints grid, colors the grid with the given color, keeps background empty
     */
    public Camera printGrid(int interval, Color color) {
        if(imageWriter == null)
            throw new MissingResourceException("missing resource!", "imageWriter", " ");

        //(1) iterate thru each pixel acc to interval, write to imageWriter:
        int totalXPixels = imageWriter.getNx();
        int totalYPixels = imageWriter.getNy();

        for (int x = 0; x < totalXPixels; x++) {        //x = specific pixel in x direction (j)
            for (int y = 0; y < totalYPixels; y+=interval) {    //y = specific pixel in y direction (i)
                imageWriter.writePixel(x, y, color);
            }
        }

        for (int x = 0; x < totalXPixels; x+= interval) {        //x = specific pixel in x direction (j)
            for (int y = 0; y < totalYPixels; y++) {    //y = specific pixel in y direction (i)
                imageWriter.writePixel(x, y, color);
            }
        }

        return this;
    }

    //TO REPOSITION CAMERA AFTER BEING CONSTRUCTED (rotates Camera's "vector_to"):
    public Camera rotateAroundZ(double angleInDegrees)
    {
        this.v_to = v_to.rotateAroundZ(angleInDegrees);
        return this;
    }
    public Camera rotateAroundY(double angleInDegrees){
        //rotates counter-clockwise ("left").
        //to rotate clockwise , do 360 - angleInDegrees
        this.v_to = v_to.rotateAroundY(angleInDegrees);
        return this;
    }
    public Camera rotateAroundX(double angleInDegrees){
        this.v_to = v_to.rotateAroundX(angleInDegrees);
        return this;
    }
    public Camera resetP0(Point newP0){
        p0 = newP0;
        return this;
    }
    /**
     * writeToImage() activates the ImageWriter, and exports the image to the file specified there
     */
    public void writeToImage(){
        if (imageWriter == null) {
            throw new MissingResourceException("missing resource!", "imageWriter", " ");
        }
        imageWriter.writeToImage();
    }
}
