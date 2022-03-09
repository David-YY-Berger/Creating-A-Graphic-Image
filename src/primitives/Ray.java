package primitives;

/**
 * this class is sometimes used to represent a line...
 */
public class Ray {

    protected Point p0; // beginning point
    protected Vector dirVector; //direction vector

    public Ray(Point p, Vector directionVec){
        p0 = p;
        dirVector = directionVec.normalize();
    }
    @Override
    public boolean equals(Object obj) {
        //if same object
        if(this == obj) //checks by address...
            return true;
        else if(obj == null  //if different classes, reject...
                || obj.getClass()!= this.getClass())
            return false;

        // cast to vector to check properly
        Ray r = (Ray)obj;
        return (r.p0.equals(this.p0)
        && r.dirVector.equals(this.dirVector)); //returns bool val
    }
    @Override
    public String toString()
    {
        return "Ray, p0 = " + p0.toString() + " direction vector = " + dirVector.toString();
    }

    public Point getP0() {
        return p0;
    }
    public Vector getDirVector() {
        return dirVector;
    }
}
