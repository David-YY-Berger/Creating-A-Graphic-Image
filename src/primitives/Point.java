package primitives;

public class Point {

    protected Double3 xyz;
    /*protected Double3 x;
    protected Double3 y;
    protected Double3 z;
    public Point(Double3 _x, Double3 _y, Double3 _z)
    {
        x = _x;
        y = _y;
        z = _z;
    }*/

    public Point(Double x, Double y, Double z)
    {
        xyz = new Double3(x, y, z);
    }

    @Override
    public String toString()
    {
        return xyz.toString();
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
        return (p.xyz == this.xyz); //returns bool val
    }

    public Point add(Vector v)
    {
        return new Point(0.0,0.0,0.0); //delete this line...
    }
    public Vector subtract(Point p)
    {
        return new Vector(0.0, 0.0, 0.0); //delete this line..
    }


    //end of class
}
