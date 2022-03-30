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
    //private double length;  //<- do we need this????

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

        //see diagram.....!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
        Point pc = p0.add(v_to.scale(distanceFromVP)); //center of VP (respresented as a 3d Point
        Point pIJ = pc;
        double rx = width / nX;
        double xJ = (j - (nX-1)/2d) * rx;

        if(xJ!=0)
            pIJ = pIJ.add(v_right.scale(xJ));

        double rY = height / nY;
        double yI = -(i - (nY-1)/2d)*rY;

        if(yI!=0)
            pIJ = pIJ.add(v_up.scale(yI));

        Ray res = new Ray(p0, pIJ.subtract(p0));
        return res;
    }

}
