package primitives;

public class Point {

    Double3 xyz;

    public Point(double d1, double d2, double d3) { xyz = new Double3(d1, d2, d3); }
    public Point(Double3 _xyz)
    {
        xyz = _xyz;
    }

    @Override
    public String toString()
    {
        return "Point " + xyz.toString();
    }
    @Override
    public boolean equals(Object obj)
    {
        //if same object
        if(this == obj) //checks by address...
            return true;
        else if(obj == null  //if different classes, reject...
                || obj.getClass()!= this.getClass())
            return false;

        // cast to Point to check properly
        Point p = (Point) obj;
        return (p.xyz.equals(this.xyz)); //returns bool val
    }

    public Vector subtract(Point p) { return new Vector(this.xyz.subtract(p.xyz)); }
    public Point add(Vector v)
    {
        return new Point(v.xyz.add(this.xyz));
    }
    public double distanceSquared(Point p){
        Vector vec = p.subtract(this);
        return vec.xyz.d1 * vec.xyz.d1 + vec.xyz.d2 * vec.xyz.d2 + vec.xyz.d3 * vec.xyz.d3;
    }

    public double distance (Point p){
        return Math.sqrt(distanceSquared(p));
    }

    //end of class
}
