package renderer;
import primitives.*;


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
    private double length;

    private double distanceFromVP;

    public Camera(Point _p0, Vector _v_to, Vector _v_up) {

        //checks that Vectors are orthogonal (otherwise, throws ex),

//code...

        // creates v_right, saves all as normal vectors

    }

    public Camera setVPSize(double width, double height){

        //code...

        Camera res = new Camera(new Point(0, 0, 0), new Vector(0, 0, 0), new Vector(0, 0, 0));

        return res;
    }
    public Camera setVPDistance(double distance){
        //code...

        return this; //?
    }

    public Ray constructRay(int nX, int nY, int j, int i){

        //code...


        Ray res = new Ray(new Point(0, 0, 0), new Vector(0, 0, 0));
        return res;
    }

}
