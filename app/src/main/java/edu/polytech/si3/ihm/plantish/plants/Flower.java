package edu.polytech.si3.ihm.plantish.plants;

import android.graphics.Color;
import android.location.Location;

import java.io.Serializable;

import edu.polytech.si3.ihm.plantish.plants.Plant;


public class Flower extends Plant implements Serializable {
    Color color;

    public Flower(Location location, String description, String family) {
        super(location, description, family, "FLOWER");
        this.color = color;
    }
}
