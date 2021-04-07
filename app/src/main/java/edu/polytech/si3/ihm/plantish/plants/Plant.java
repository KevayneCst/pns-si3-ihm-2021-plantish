package edu.polytech.si3.ihm.plantish.plants;

import android.location.Location;
import android.media.Image;

import java.util.Date;


public abstract class Plant {
    String name;
    Location location;
    String description;
    String family;

    public Plant(String name, Location location, String description, String family) {
        this.name = name;
        this.location = location;
        this.description = description;
        this.family = family;
    }
}
