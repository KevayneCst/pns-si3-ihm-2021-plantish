package edu.polytech.si3.ihm.plantish.plants;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.util.Log;

import java.io.IOException;
import java.util.Date;

import edu.polytech.si3.ihm.plantish.MainActivity;
import edu.polytech.si3.ihm.plantish.R;
import edu.polytech.si3.ihm.plantish.user.User;
import edu.polytech.si3.ihm.plantish.posts.Post;
import edu.polytech.si3.ihm.plantish.posts.PostFlower;


public class FlowerFactory extends PlantFactory {

    @Override
    public Plant build(Location location, String family, String description){
        return new Flower(location, description, family);
    }

    @Override
    public Post build(User user, Date date, Bitmap image, Plant plant){
        PostFlower postFlower = new PostFlower(user, date, image, (Flower) plant);
        super.savePost(postFlower);
        return postFlower;
    }

    @Override
    public Post build(User user, Date date, Plant plant){
        PostFlower postFlower = new PostFlower(user, date, (Flower) plant);
        super.savePost(postFlower);

        return postFlower;
    }
}
