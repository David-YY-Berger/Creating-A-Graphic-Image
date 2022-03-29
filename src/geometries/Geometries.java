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

    private List<Intersectable> intersectableList; //should NOT be final; we plan to add to this..

    //default CTOR
    public Geometries() {
        /*
        WE HAVE CHOSEN TO USE A LINKEDLIST INSTEAD OF AN ARRAYLIST, BECAUSE:
        (1) we will never need to access a particular element; rather we will always iterate thru them...
        (2) we need to add many shapes to our list, and a linkedList is faster in that regard..
         */
        intersectableList = new LinkedList<>();
    }
    //CTOR with parameters
    public Geometries(Intersectable ... geometries){

        intersectableList = new LinkedList<>(); //intialize...
        intersectableList.addAll(List.of(geometries));
    }

    public List<Intersectable> getIntersectableList() {
        return intersectableList;
    }
    public void add(Intersectable ... geoList) {
        intersectableList.addAll(List.of(geoList));
    }
    @Override
    public List<Point> findIntersections(Ray _ray) {

        List<Point> res = new LinkedList<>(); //initializes as null list...does this take up memory?? how should we check for points before making a list?
        //boolean firstPoint = true;

        for (Intersectable shape: intersectableList) {
            List<Point> thisShapeIntersections = shape.findIntersections(_ray);
            if(thisShapeIntersections != null)      //returns null if there are no intersections....
            {
//                if(firstPoint) {
//                    res = new LinkedList<>();
//                    firstPoint = false;
//                }
                res.addAll(thisShapeIntersections);
            }
        }
        if(!res.isEmpty())
            return res; //returns empty list if no points were returned...
        else
            return null;
    }
}
