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
    public Double length() {return Math.sqrt(lengthSquared());}
    public Double lengthSquared() {
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

}
