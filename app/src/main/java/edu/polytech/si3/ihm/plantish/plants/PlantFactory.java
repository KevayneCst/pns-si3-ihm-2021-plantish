package edu.polytech.si3.ihm.plantish.plants;

import android.graphics.Bitmap;
import android.location.Location;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import edu.polytech.si3.ihm.plantish.user.Session;
import edu.polytech.si3.ihm.plantish.user.User;
import edu.polytech.si3.ihm.plantish.posts.Post;


public abstract class PlantFactory  {

    public static String FLOWER = "Fleur";
    public static String BUSH = "Buisson";
    public static String TREE = "Arbre";

    String filePath = "plants.json";

    public abstract Plant build(Location location, String family, String description);

    public abstract Post build(User user, Date date, Bitmap image, Plant plant);

    public abstract Post build(User user, Date date, Plant plant);

    void savePost(Object object){
        Gson gson = new Gson();

        Log.d("POST", gson.toJson(object));
        Session.addPost((Post) object);
    }

    List<Plant> getJson() {
        return null;
    }

}
