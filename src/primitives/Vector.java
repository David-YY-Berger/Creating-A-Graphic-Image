package primitives;

public class Vector extends Point {

    //includes point...

    public Vector(double d1, double d2, double d3) {
        super(d1, d2, d3); //call's Point's CTOR
    }
    public Vector(Double3 _xyz) {
            super(_xyz); //call's Point's CTOR
    }

    @Override
    public String toString()
    {
        return "The vector is: " + xyz.toString();
    }

    public Vector add (Vector v) { return new Vector(this.xyz.add(v.xyz)); }

    public Vector Scale (double n) {return new Vector(this.xyz.scale(n)); }

    public double dotProduct (Vector v) {
        Double3 d = this.xyz.product(v.xyz);
        return d.d1 + d.d2 + d.d3;
    }





}
