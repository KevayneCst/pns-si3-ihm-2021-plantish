package edu.polytech.si3.ihm.plantish.plants;

import android.graphics.Color;
import android.location.Location;
import edu.polytech.si3.ihm.plantish.plants.Plant;


public class Flower extends Plant {
    Color color;

    public Flower(String name, Location location, String description, String family) {
        super(name, location, description, family);
        this.color = color;
    }
}
