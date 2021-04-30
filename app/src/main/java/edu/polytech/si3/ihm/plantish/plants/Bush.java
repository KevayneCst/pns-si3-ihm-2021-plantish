package edu.polytech.si3.ihm.plantish.plants;

import android.location.Location;

import java.io.Serializable;

public class Bush extends Plant implements Serializable {


    public Bush(Location location, String description, String family) {
        super(location, description, family,"BUSH");
    }
}
