package edu.polytech.si3.ihm.plantish.plants;

import android.graphics.Bitmap;

import org.osmdroid.util.GeoPoint;

import java.util.Date;

import edu.polytech.si3.ihm.plantish.user.User;
import edu.polytech.si3.ihm.plantish.posts.Post;
import edu.polytech.si3.ihm.plantish.posts.PostBush;


public class BushFactory extends PlantFactory{

    public Plant build(GeoPoint position, String family, String description){
        return new Bush(position, description, family);
    }

    public Post build(User user, Date date, Bitmap image, Plant plant){
        PostBush postBush = new PostBush(user, date, image, (Bush) plant);
        super.savePost(postBush);
        return postBush;
    }

    public Post build(User user, Date date, Plant plant){
        PostBush postBush = new PostBush(user, date, (Bush) plant);
        super.savePost(postBush);
        return postBush;
    }

}
