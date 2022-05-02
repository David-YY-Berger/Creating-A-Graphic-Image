package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;

/**
 * abstract class - bec we want this each child class to hold a field,
 * and also implement the general functions of getNormal()...
 */
public abstract class Geometry extends Intersectable {

    protected Color emission = Color.BLACK;
    private Material material = new Material();

    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    /**
     * @return the material of the geometry
     */
    public Material getMaterial() {
        return material;
    }

    public Color getEmission() {
        return emission;
    }
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    public abstract Vector getNormal(Point pointOnSurface);


}
