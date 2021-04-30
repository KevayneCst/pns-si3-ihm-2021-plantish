package edu.polytech.si3.ihm.plantish.plants;

import android.location.Location;

import java.io.Serializable;

public class Tree extends Plant implements Serializable {
    private int size;


    public Tree(Location location, String description, String family) {
        super(location, description, family, "TREE");
    }
}
