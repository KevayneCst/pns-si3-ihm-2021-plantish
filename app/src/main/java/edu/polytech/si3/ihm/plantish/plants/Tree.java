package edu.polytech.si3.ihm.plantish.plants;

import android.location.Location;

public class Tree extends Plant {
    private int size;

    public Tree(String name, Location location, String description, String family) {
        super(name, location, description, family);
    }
}
