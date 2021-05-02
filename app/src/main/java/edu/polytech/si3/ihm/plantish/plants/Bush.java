package edu.polytech.si3.ihm.plantish.plants;

import android.location.Location;

import org.osmdroid.util.GeoPoint;

import java.io.Serializable;

public class Bush extends Plant implements Serializable {


    public Bush(GeoPoint position, String description, String family) {
        super(position, description, family,"BUSH");
    }
}
