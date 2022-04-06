package renderer;
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
        if(!Util.isZero(_v_to.dotProduct(_v_up))) //if vectors are not orthogonal
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
    public Camera setRayTracerBase(RayTracerBase rayTracerBase) {
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
     * @return
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
    public void renderImage() {

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
            throw new UnsupportedOperationException(); //what to do with this???? we will never get to this code block...

        //(2) iterate thru each pixel, write to imageWriter:
        int totalXPixels = imageWriter.getNx();
        int totalYPixels = imageWriter.getNy();

        for (int x = 0; x < totalXPixels; x++) {        //x = specific pixel in x direction (j)
            for (int y = 0; y < totalYPixels; y++) {    //y = specific pixel in y direction (i)
                Ray ray = constructRayThruPixel(totalXPixels, totalYPixels, x, y);
                Color pixelColor = rayTracerBase.traceRay(ray);
                imageWriter.writePixel(x, y, pixelColor);
            }
        }

        //(3) export bufferedImage to file (file given in image's constructor)
        // imageWriter.writeToImage();

    }

    /**
     * prints grid, colors the grid with the given color, keeps background empty
     */
    public void printGrid(int interval, Color color) {
        if(imageWriter == null)
            throw new MissingResourceException("missing resource!", "imageWriter", " ");

        //(1) iterate thru each pixel acc to interval, write to imageWriter:
        int totalXPixels = imageWriter.getNx();
        int totalYPixels = imageWriter.getNy();

        for (int x = 0; x < totalXPixels; x+=interval) {        //x = specific pixel in x direction (j)
            for (int y = 0; y < totalYPixels; y+=interval) {    //y = specific pixel in y direction (i)
                Ray ray = constructRayThruPixel(totalXPixels, totalYPixels, x, y);
                Color borderColor = new Color(10, 200, 48);
                imageWriter.writePixel(x, y, borderColor);
            }
        }
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
