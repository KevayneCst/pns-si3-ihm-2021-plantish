package edu.polytech.si3.ihm.plantish.user;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable {
    @SerializedName("name")
    private String name;

    public User(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
