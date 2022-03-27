package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;

/**
 * implements the "Composite" design pattern; this object can iterate thru a list of geometries
 *
 */
public class Geometries implements Intersectable {

    private List<Intersectable> lst;

    //default CTOR
    public Geometries() {
        lst = new LinkedList<Intersectable>();
        /** WE HAVE CHOSEN TO USE A LINKEDLIST INSTEAD OF AN ARRAYLIST, BECAUSE:
         * (1) we will never need to access a particular element; rather we will always iterate thru them...
         * (2) we need to add many shapes to our list, and a linkedList is faster in that regard..
         */
    }
    //CTOR with parameters
    public Geometries(Intersectable ... geometries){

        lst = new LinkedList<>(); //intialize...
        for (Intersectable geo : geometries) {
            lst.add(geo);
        }
    }

    public List<Intersectable> getLst() {
        return lst;
    }
    public void add(Intersectable geo) {
        lst.add(geo);
    }
    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }
}
