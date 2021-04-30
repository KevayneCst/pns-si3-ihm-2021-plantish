package edu.polytech.si3.ihm.plantish.plants;

import android.location.Location;
import android.media.Image;

import com.google.gson.annotations.SerializedName;

import java.util.Date;


public  class Plant {
    @SerializedName("location")
    Location location;
    @SerializedName("description")
    String description;
    @SerializedName("family")
    String family;

    String TYPE;
    public static String FLOWER = "FLOWER";
    public static String TREE = "FLOWER";
    public static String BUSH = "FLOWER";


    public Plant(Location location, String description, String family, String type) {
        this.location = location;
        this.description = description;
        this.family = family;
        this.TYPE = type;
    }


    public Plant(){}

    public String getFamily(){
        return family;
    }

    public String getDescription(){
        return description;
    }

    public String getTYPE(){
        return TYPE;
    }

    public Location getLocation(){
        return location;
    }

}
