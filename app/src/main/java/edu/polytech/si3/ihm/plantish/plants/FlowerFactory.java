package edu.polytech.si3.ihm.plantish.plants;

import android.graphics.Bitmap;

import org.osmdroid.util.GeoPoint;

import java.util.Date;

import edu.polytech.si3.ihm.plantish.user.User;
import edu.polytech.si3.ihm.plantish.posts.Post;
import edu.polytech.si3.ihm.plantish.posts.PostFlower;


public class FlowerFactory extends PlantFactory {

    @Override
    public Plant build(GeoPoint position, String family, String description){
        return new Flower(position, description, family);
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
