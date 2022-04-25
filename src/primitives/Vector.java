package primitives;

/**
 *  VECTOR DESCRIPTION:
 *  holds an XYZ, but as opposed to a point (which is a specific place in the 3d space),
 *  a vector is a direction and magnitude - and has no specific spot
 */
public class Vector extends Point {

    //field point "xyz" - from Point...
    //use's Point's "equals()" function

    //CTOR's
    public Vector(double d1, double d2, double d3) {
        super(d1, d2, d3); //call's Point's CTOR
        Double3 newDouble3 = new Double3(d1, d2, d3);
        if(newDouble3.equals(Double3.ZERO)) {
            xyz = Double3.ZERO;
            throw new IllegalArgumentException("Zero Vector!");
        }
    }
    public Vector(Double3 _xyz) {
        super(_xyz); //call's Point's CTOR
        if(_xyz.equals(Double3.ZERO)) {
            xyz = Double3.ZERO;
            throw new IllegalArgumentException("Zero Vector!");
        }
    }

    @Override
    public String toString()    {
        return "The vector is: " + xyz.toString();
    }

    /**
     * override's point's"Add"....
     */
    @Override
    public Vector add (Vector v) { return new Vector(this.xyz.add(v.xyz)); }

    /**
     * scales vector by given double
     */
    public Vector scale(double n) {return new Vector(this.xyz.scale(n)); }

    /**
     * calculates dot product btw this vector and vector v
     * can be used to find length of projection of one vector on the other
     */
    public double dotProduct (Vector v) {
        Double3 d = this.xyz.product(v.xyz);
        return d.d1 + d.d2 + d.d3;
    }

    /**
     * calculates cross product
     * @return vector which is parrarel to this vector and vec2
     */
    public Vector crossProduct(Vector vec2){

        Vector res = new Vector((this.xyz.d2*vec2.xyz.d3) - (this.xyz.d3*vec2.xyz.d2),
                -((this.xyz.d1*vec2.xyz.d3) - (this.xyz.d3*vec2.xyz.d1)),
                (this.xyz.d1*vec2.xyz.d2) - (this.xyz.d2*vec2.xyz.d1));
        //if result is a "zero vector", throws exception...
        if(res.xyz == Double3.ZERO)
            throw new IllegalArgumentException();
        else
            return res;
    }

    /**
     * @return length of vector
     */
    public double length() {return Math.sqrt(lengthSquared());}
    public double lengthSquared() {
        return this.xyz.d1*this.xyz.d1
                + this.xyz.d2*this.xyz.d2
                + this.xyz.d3*this.xyz.d3;
    }

    /**
     * @return normalized vector, with each triad btw 0 and 1
     */
    public Vector normalize() {
        return new Vector(this.xyz.reduce(this.length()));
    }

    /** projection() @return vector projection of vec2 onto this Vector
     * @param vec2
    */
    public Vector projection(Vector vec2){
        //find scalar projection:
        double scalarProj = (vec2.dotProduct(this))/ this.lengthSquared();
        if(Util.isZero(scalarProj))
            throw new IllegalArgumentException("Vectors are perpendicular!");

        return this.scale(scalarProj);
    }

    /**
     * rotates vector around the z axis...
     * @param angleInDegrees number of degrees to rotate...
     * @return the rotated vector
     */
    public Vector rotateAroundZ(double angleInDegrees) {
        //based on theory found here: https://stackoverflow.com/questions/14607640/rotating-a-vector-in-3d-space
        double angleInRadians = Math.toRadians(angleInDegrees);

        double[][] vectorMatrix = new double[3][1];
        vectorMatrix[0][0] = getX();
        vectorMatrix[1][0] = getY();
        vectorMatrix[2][0] = getZ();

        double[][] rotatingMatrix = new double[3][3];
        rotatingMatrix[0][0] = Math.cos(angleInRadians);
        rotatingMatrix[0][1] = -Math.sin(angleInRadians);
        rotatingMatrix[0][2] = 0;

        rotatingMatrix[1][0] = Math.sin(angleInRadians);
        rotatingMatrix[1][1] = Math.cos(angleInRadians);
        rotatingMatrix[1][2] = 0;

        rotatingMatrix[2][0] = 0;
        rotatingMatrix[2][1] = 0;
        rotatingMatrix[2][2] = 1;

        double[][] res = MatrixOperations.multiplyMatrix(3, 3, rotatingMatrix,
                3, 1, vectorMatrix);
        for (int i = 0; i < 3; i++) {
            if(Util.isZero(res[i][0])) res[i][0] = 0;
        }

        this.xyz = new Double3(res[0][0], res[1][0], res[2][0]);
        return this;
    }

    /**
     * rotates vector around the y axis...
     * @param angleInDegrees number of degrees to rotate...
     * @return the rotated vector
     */
    public Vector rotateAroundY(double angleInDegrees) {
        //based on theory found here: https://stackoverflow.com/questions/14607640/rotating-a-vector-in-3d-space
        double angleInRadians = Math.toRadians(angleInDegrees);

        double[][] vectorMatrix = new double[3][1];
        vectorMatrix[0][0] = getX();
        vectorMatrix[1][0] = getY();
        vectorMatrix[2][0] = getZ();

        double[][] rotatingMatrix = new double[3][3];
        rotatingMatrix[0][0] = Math.cos(angleInRadians);
        rotatingMatrix[0][1] = 0;
        rotatingMatrix[0][2] = Math.sin(angleInRadians);

        rotatingMatrix[1][0] = 0;
        rotatingMatrix[1][1] = 1 ;
        rotatingMatrix[1][2] = 0;

        rotatingMatrix[2][0] = -Math.sin(angleInRadians);
        rotatingMatrix[2][1] = 0;
        rotatingMatrix[2][2] = Math.cos(angleInRadians);

        double[][] res = MatrixOperations.multiplyMatrix(3, 3, rotatingMatrix,
                3, 1, vectorMatrix);
        for (int i = 0; i < 3; i++) {
            if(Util.isZero(res[i][0])) res[i][0] = 0;
        }



        this.xyz = new Double3(res[0][0], res[1][0], res[2][0]);
        return this;
    }

    /**
     * rotates vector around the x axis...
     * @param angleInDegrees number of degrees to rotate...
     * @return the rotated vector
     */
    public Vector rotateAroundX(double angleInDegrees) {
        //based on theory found here: https://stackoverflow.com/questions/14607640/rotating-a-vector-in-3d-space
        double angleInRadians = Math.toRadians(angleInDegrees);

        double[][] vectorMatrix = new double[3][1];
        vectorMatrix[0][0] = getX();
        vectorMatrix[1][0] = getY();
        vectorMatrix[2][0] = getZ();

        double[][] rotatingMatrix = new double[3][3];
        rotatingMatrix[0][0] = 1;
        rotatingMatrix[0][1] = 0;
        rotatingMatrix[0][2] = 0;

        rotatingMatrix[1][0] = 0;
        rotatingMatrix[1][1] = Math.cos(angleInRadians);
        rotatingMatrix[1][2] = -Math.sin(angleInRadians);

        rotatingMatrix[2][0] = 0;
        rotatingMatrix[2][1] = Math.sin(angleInRadians);
        rotatingMatrix[2][2] = Math.cos(angleInRadians);

        double[][] res = MatrixOperations.multiplyMatrix(3, 3, rotatingMatrix,
                3, 1, vectorMatrix);
        for (int i = 0; i < 3; i++) {
            if(Util.isZero(res[i][0])) res[i][0] = 0;
        }

        this.xyz = new Double3(res[0][0], res[1][0], res[2][0]);
        return this;
    }
}
