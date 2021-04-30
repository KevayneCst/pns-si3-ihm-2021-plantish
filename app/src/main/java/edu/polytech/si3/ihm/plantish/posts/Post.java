package edu.polytech.si3.ihm.plantish.posts;

import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

import edu.polytech.si3.ihm.plantish.user.User;
import edu.polytech.si3.ihm.plantish.plants.Plant;


public  class Post implements Serializable {
    @SerializedName("user")
    User user;
    @SerializedName("date")
    Date date;
    @SerializedName("image")
    Bitmap image;
    @SerializedName("plant")
    Plant plant;

    public Post(User user, Date date, Bitmap image, Plant plant) {
        this.user = user;
        this.date = date;
        this.image = image;
        this.plant = plant;
    }

    @Override
    public String toString(){
        return "Post by "+user.getName()+" on "+date.toString();
    }

    public Plant getPlant(){
        return plant;
    }

    public User getUser(){ return user;}

    public Bitmap getImage(){ return image;}

    public Date getDate(){ return date;}
}
