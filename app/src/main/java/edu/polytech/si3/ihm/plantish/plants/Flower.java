package edu.marine.plantish1.plants;

import android.graphics.Color;
import android.location.Location;

public class Flower extends Plant{
    Color color;

    public Flower(String name, Location location, String description, String family) {
        super(name, location, description, family);
        this.color = color;
    }
}
