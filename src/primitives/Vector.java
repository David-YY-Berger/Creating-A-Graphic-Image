package primitives;

public class Vector extends Point {

    //field point "xyz"

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
    @Override
    public Vector add (Vector v) { return new Vector(this.xyz.add(v.xyz)); }
    public Vector scale(double n) {return new Vector(this.xyz.scale(n)); }
    public double dotProduct (Vector v) {
        Double3 d = this.xyz.product(v.xyz);
        return d.d1 + d.d2 + d.d3;
    }
    @Override
    public boolean equals(Object obj)    {
        //if same object
        if(this == obj) //checks by address...
            return true;
        else if(obj == null  //if different classes, reject...
                || obj.getClass()!= this.getClass())
            return false;

        // cast to Vector to check properly
        Vector vec = (Vector) obj;
        return (vec.xyz.equals(this.xyz)); //returns bool val
    }
    public Vector crossProduct(Vector vec2){
        return new Vector(
                (this.xyz.d2*vec2.xyz.d3) - (this.xyz.d3*vec2.xyz.d2),
                -((this.xyz.d1*vec2.xyz.d3) - (this.xyz.d3*vec2.xyz.d1)),
                (this.xyz.d1*vec2.xyz.d2) - (this.xyz.d2*vec2.xyz.d1)
        );
    }
    public Double lengthSquared() {
        return this.xyz.d1*this.xyz.d1
                + this.xyz.d2*this.xyz.d2
                + this.xyz.d3*this.xyz.d3;
    }
    public Double length() {return Math.sqrt(lengthSquared());}
    public Vector normalize() {
        return new Vector(this.xyz.reduce(this.length()));
    }







}
