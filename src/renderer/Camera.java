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
    private double length;  //<- do we need this????

    private double distanceFromVP; //from camera to View Plane

    public Camera(Point _p0, Vector _v_to, Vector _v_up) {

        //checks that Vectors are orthogonal (otherwise, throws ex),
        if(!Util.isZero(_v_to.dotProduct(_v_up))) //if vectors are not orthogonal
            throw new IllegalArgumentException("given vectors are not orthogonal!");

        p0 = _p0;
        v_to = _v_to.normalize();
        v_up = _v_up.normalize();
        v_right = _v_to.crossProduct(_v_up).normalize(); //<- do we need to normalize???
    }

    public Camera setVPSize(double _width, double _height){

        width = _width;
        height = _height;
        return this;
    }
    public Camera setVPDistance(double distance){

        distanceFromVP = distance;
        return this; //?
    }

    public Ray constructRay(int nX, int nY, int j, int i){

        //code...


        Ray res = new Ray(new Point(0, 0, 0), new Vector(0, 0, 0));
        return res;
    }

}
