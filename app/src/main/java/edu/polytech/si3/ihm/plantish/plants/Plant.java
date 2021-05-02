package edu.polytech.si3.ihm.plantish.plants;

import android.location.Location;
import android.media.Image;

import com.google.gson.annotations.SerializedName;

import org.osmdroid.util.GeoPoint;

import java.util.Date;


public  class Plant {
    @SerializedName("position")
    GeoPoint position;
    @SerializedName("description")
    String description;
    @SerializedName("family")
    String family;

    String TYPE;
    public static String FLOWER = "FLOWER";
    public static String TREE = "TREE";
    public static String BUSH = "BUSH";

    public static int FLOWERINT = 0;
    public static int BUSHINT = 1;
    public static int TREEINT = 2;


    public Plant(GeoPoint position, String description, String family, String type) {
        this.position = position;
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

    public GeoPoint getPosition(){
        return position;
    }

}
