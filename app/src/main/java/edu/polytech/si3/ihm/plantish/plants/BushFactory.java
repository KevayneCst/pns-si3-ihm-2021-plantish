package edu.polytech.si3.ihm.plantish.plants;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;

import java.io.IOException;
import java.util.Date;

import edu.polytech.si3.ihm.plantish.MainActivity;
import edu.polytech.si3.ihm.plantish.R;
import edu.polytech.si3.ihm.plantish.user.User;
import edu.polytech.si3.ihm.plantish.posts.Post;
import edu.polytech.si3.ihm.plantish.posts.PostBush;


public class BushFactory extends PlantFactory{

    public Plant build(Location location, String family, String description){
        return new Bush(location, description, family);
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
