package edu.polytech.si3.ihm.plantish.plants;

import android.location.Location;

import org.osmdroid.util.GeoPoint;

import java.io.Serializable;

public class Tree extends Plant implements Serializable {
    private int size;


    public Tree(GeoPoint position, String description, String family) {
        super(position, description, family, "TREE");
    }
}
