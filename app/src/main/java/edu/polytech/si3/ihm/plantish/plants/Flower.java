package edu.polytech.si3.ihm.plantish.plants;

import android.graphics.Color;
import android.location.Location;

import org.osmdroid.util.GeoPoint;

import java.io.Serializable;

import edu.polytech.si3.ihm.plantish.plants.Plant;


public class Flower extends Plant implements Serializable {
    Color color;

    public Flower(GeoPoint position, String description, String family) {
        super(position, description, family, "FLOWER");
        this.color = color;
    }
}
