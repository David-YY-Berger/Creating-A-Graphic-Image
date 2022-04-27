package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * implements the "Composite" design pattern; this object can iterate thru a list of geometries
 *
 */
public class Geometries extends Intersectable {

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

//DELETE THIS
//    @Override
//    public List<Point> findIntersections(Ray _ray) {
//
//        var geoList = findGeoIntersections(_ray);
//        List<Point> res = null;
//        if (geoList != null) {
//            res = new LinkedList<>();
//            for (GeoPoint gP : geoList
//            ) {
//                res.add(gP.point);
//            }
////           return geoList == null ? null
////                    : geoList.stream().map(gp -> gp.point).toList(); //<doesnt work; i guess i dont have the right java?
//        }
//        return res; //returns null if no points..
//    }


        @Override
        public List<GeoPoint> findGeoIntersectionsHelper (Ray ray){

            List<GeoPoint> res = null;

            for (Intersectable shape : intersectableList) {
                List<GeoPoint> thisShapeIntersections = shape.findGeoIntersections(ray);
                if (thisShapeIntersections != null)      //returns null if there are no intersections....
                {
                    if (res == null) { //for first time that we add a list...
                        res = new LinkedList<>();
                    }
                    res.addAll(thisShapeIntersections);
                }
            }

            return res;
        }

    }

